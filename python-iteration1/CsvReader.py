import csv
import xlrd
from Student import Student
import Questions
class CsvReader:

    def readpoll(self):

        users={}
        students=[]
        with open('CSE3063_20201124_Tue_zoom_PollReport.csv', mode="r",encoding="utf8") as csv_file:
            csv_reader = csv.reader(csv_file, delimiter =",")
            list=[]
            for row in csv_reader:
                list.append(row)




            for i in range(1,len(list)):
                split_row = str(list[i][1]).split(" ")
                for a in range(4,(len(list[i])-1),2):
                    questions = {}
                    questions[list[i][a]]= list[i][a+1]
                    users[(list[i][1])] = questions

                #print(users[(list[i][1])])

                student =Student(split_row[0].upper(),split_row[-1].upper(),list[i][2])
                #student.func()

            #print(questions)
            for key in users.keys():
                print(users[key])

csvread= CsvReader()
csvread.readpoll()