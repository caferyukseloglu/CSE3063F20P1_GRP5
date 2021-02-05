file_name = "CSE3063 OOSD Weekly Session 2 - Tuesday Quizzes ANSWER KEY.txt"
f = open(file_name)
lines = f.readlines()

last_poll = ""
question_id = 1
answer_id = 1
last_question = ""
is_multiple = False

polls = {}
counter = 0

for line in lines:
    if counter < 2:
        pass
    else:
        if line[1:5] == "Poll":
            poll_name = line[8:-15].split("\t")[0]
            polls[poll_name] = {}
            last_poll = poll_name
            question_id = 1
            print (line[:10])
        elif line[0] == str(question_id):
            if " ( Multiple Choice)\n" in line:
                question_name = line[:-20]
            elif " ( Single Choice)\n" in line:
                question_name = line[:-18]
            question_name = ". ".join(question_name.split(". ")[1:])
            polls[last_poll][question_name] = []
            last_question = question_name
            question_id += 1
        elif line[:6] == "Answer":
            answer_text = line[10:-1]

            polls[last_poll][last_question].append(answer_text)
    counter += 1

print(polls)
print(polls.keys())

