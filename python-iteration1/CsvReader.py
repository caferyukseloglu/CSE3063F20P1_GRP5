import csv
import xlrd
from Student import Student
import Questions
class CsvReader:

    def readpoll(self):
        questions = {}
        users={}
        students=[]
        with open('CSE3063_20201124_Tue_zoom_PollReport.csv', mode="r",encoding="utf8") as csv_file:
            csv_reader = csv.reader(csv_file, delimiter =",")
            list=[]
            for row in csv_reader:
                list.append(row)
                split_row= str(row[1]).split(" ")

            print(questions)
            for i in range(1,len(list)):
                questions[list[i][4]] = list[i][5]
                split_row = str(list[i][1]).split(" ")
                print(split_row)
                users[(list[i][1])]=questions
                print(list[i][5])
                student =Student(split_row[0],split_row[1],list[i][2])
                student.func()
            print(questions)
            print(users)
csvread= CsvReader()
csvread.readpoll()