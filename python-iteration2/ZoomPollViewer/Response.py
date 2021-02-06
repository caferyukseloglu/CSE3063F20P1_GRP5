#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v1.0
RESPONSE CLASS

6 Function
2 Object
"""


class Response:

    def __init__(self, student, session, poll):
        self._student = student
        self._session = session
        self._poll = poll

        self._answers = {}
        # Structure of _answers = { <question_A> :  [<choice_1>, <choice_2>], <question_B> :  [<choice_2>]}

        self._grade = None
        self._correct_answer_count = 0
        self._wrong_answer_count = 0
        self._empty_answer_count = 0

    def get_student(self):
        # Returns student object
        return self._student

    def get_session(self):
        # Returns session object
        return self._session

    def get_poll(self):
        # Returns poll object
        return self._poll

    def get_correct_answer_count(self):
        return self._correct_answer_count

    def get_wrong_answer_count(self):
        return self._wrong_answer_count

    def get_empty_answer_count(self):
        return self._empty_answer_count

    def get_given_answer(self, question):
        if question in self._answers:
            return self._answers[question]
        else:
            return False

    def get_correctness_of_answer(self, question):
        correct_choices = question.get_correct_choices()
        if question in self._answers:
            if len(correct_choices) == len(self._answers[question]) and all(
                    i in correct_choices for i in self._answers[question]):
                return True
            else:
                return False
        else:
            return None

    def add_answer(self, question_text, answer_texts):
        # Adds question and returns its object
        question = self._poll.get_question(question_text)
        if question:
            choices = []
            if isinstance(answer_texts, list):
                for answer_text in answer_texts:
                    choice = question.add_choice(answer_text, 0)
                    choices.append(choice)
            else:
                choice = question.add_choice(answer_texts, 0)
                choices.append(choice)

            if question not in self._answers.keys():
                self._answers[question] = []

            for choice in choices:
                self._answers[question].append(choice)
        else:
            #pass
            print(question_text)
    def get_grade(self):
        # Returns grade of reponse
        if self._grade is None:
            self.calculate_grade()
        return self._grade

    def calculate_grade(self):
        # Calculates and sets grade
        for question in self._poll.get_questions():
            correct_choices = question.get_correct_choices()
            if question in self._answers.keys():
                if len(correct_choices) == len(self._answers[question]) and all(i in correct_choices for i in self._answers[question]):
                    self._correct_answer_count += 1
                else:
                    self._wrong_answer_count += 1
        self._grade = round(self._correct_answer_count / (self._poll.get_number_of_questions()) * 100, 2)
        self._empty_answer_count = self._poll.get_number_of_questions() - self._correct_answer_count - self._wrong_answer_count
