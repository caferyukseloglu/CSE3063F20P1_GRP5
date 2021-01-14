#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import csv, xlrd, os, re
from pprint import pprint


class Importer():

    def __init__(self, zpv):

        self.zpv = zpv

    def oldimport_answer_key(self):
        poll = self.ZPV.add_poll("value")
        for i in range(10):
            question = poll.add_question("q text")
            question.add_choice("answer", 1)

    def oldimport_poll_report(self):
        session = self.ZPV.add_session(20201123) # datetime object ???
        for i in range(100): # butun cevaplar
            while True:
                poll = self.ZPV.search_poll("question")
                if poll != None:
                    break
                else:
                    pass

            student = self.ZPV.get_student("full name", "email")
            response = student.add_response(session, poll)
            ### !!! answerlar array olacak her sekilde
            response.add_answer("question", "answers")

    def oldimport_bys(self):
        #self.ZPV.GUI.insert_student()
        self.ZPV.GUI.insert_student({"id":1453, "firstname":"Emin Safa", "lastname":"Tok", "email":"esafa.tok@gmail.com"})

    def oldimport_poll_report(self):
        self.ZPV.GUI.insert_student(
            {"id": 1453, "firstname": "Emin Safa", "lastname": "Tok", "email": "esafa.tok@gmail.com"})

    def import_bys(self, file_path):
        try:
            read_xls=xlrd.open_workbook(file_path)
            xl_sheet = read_xls.sheet_by_index(0)
            first_names = xl_sheet.col_values(4)
            last_names = xl_sheet.col_values(7)
            students_ids = xl_sheet.col_values(2)
            for i in range(min(len(first_names),len(last_names))):
                if first_names[i]=="" or first_names[i]=="Adı" or last_names[i]=="" or last_names[i]=="Soyadı":
                    pass
                else:
                    getname = first_names[i].split(" ",1)
                    if (len(getname) > 1):
                        first_names[i] = getname[0]
                        middle_name = getname[1]
                    else:
                        middle_name = ''
                    self.zpv.add_student(first_names[i], middle_name, last_names[i], students_ids[i])
        except Exception as err:
            print("Error Occured", err)

    def import_answer_key(self, file_path):
        # @todo CSV version not working well with semicolon delimeter
        if os.path.basename(file_path).endswith(".xls"):
            read_xls = xlrd.open_workbook(file_path)
            xl_sheet = read_xls.sheet_by_index(0)
            questions = xl_sheet.col_values(0)
            answers = xl_sheet.col_values(1)
            poll = self.zpv.add_poll(xl_sheet.cell(0,0).value)
            for i in range(2, min(len(questions), len(answers))):
                question = poll.add_question(questions[i])
                question.add_choice(answers[i])

        elif os.path.basename(file_path).endswith(".csv"):
            with open(file_path, newline='', encoding="utf8") as csv_file:
                csv_array = csv.reader(csv_file)
                i = 0
                for row in csv_array:
                    if i == 0:
                        pass
                    else:
                        question = row[0]
                        print(question)
                    i += 1

    def import_poll_report(self, file_path):
        with open(file_path, newline='', encoding="utf8") as csv_file:
            csv_array = csv.reader(csv_file)
            poll_cache = {}
            i = 0
            for row in csv_array:
                if i == 0:
                    pass
                else:
                    if row[4] in poll_cache.keys():
                        poll = poll_cache[row[4]]
                    else:
                        poll = self.zpv.get_poll_by_question(row[4])
                    session = self.zpv.add_session(row[3])
                    full_name = row[1]
                    email = row[2]
                    self.control_student(full_name,email,session,poll,row)
                                          
                i = i + 1
    def control_student(self,full_name,email,session,poll,row):
         # This part is is to control every step of import
        student = self.zpv.get_student(full_name, email)
        if student:
            print("Student found")
            response = student.add_response(session, poll)
            for j in range(4, len(row) - 1, 2):
                response.add_answer(row[j], row[j + 1])
        else:            
            #If fullName has digit inside it will be cleared
            if(any(a.isnumeric() for a in full_name)):
                full_name = ''.join(b for b in full_name if b.isalpha() or b.isspace())
                print("Student name had integer in, ", full_name)      
            #If fullName has not space inside it and starts with uppercase resumed lowercase we will add space before A-Z.a-z
            if not(any(c.isspace() for c in full_name)):
                full_name =  re.sub(r"(\w)([A-Z][a-z])", r"\1 \2", full_name)
                print("Student name had no space in, ", full_name)
            else:
                #If fullName has space inside it and starts with uppercase resumed lowercase we will break it to array and add space before A-Z.a-z
                newname = full_name.split()
                result = []
                for m in newname:
                     m =  re.sub(r"(\w)([A-Z][a-z])", r"\1 \2", m)
                     result.append(m)
                full_name = ' '.join(result)
                print("Student name had space in, ", full_name)
                
            # This part is is to control every step of import
            student = self.zpv.get_student(full_name, email)
            if student:
                print("Student found")
                response = student.add_response(session, poll)
                for j in range(4, len(row) - 1, 2):
                    response.add_answer(row[j], row[j + 1])
            else:
                print("Student not found")
        print("----------",full_name)
        
        return 0  

