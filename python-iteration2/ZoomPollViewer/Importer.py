#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v1.0
IMPORTER CLASS
# coding=utf-8
7 Function
1 Object
"""
import csv, xlrd, os, re
from pprint import pprint
from os import listdir
from os.path import isfile, join
from .Logger import Logger
import logging

class Importer():

    def __init__(self, zpv):

        self.zpv = zpv

    def import_bys(self, file_path):
        # Imports BYS file
        try:
            read_xls=xlrd.open_workbook(file_path)
            xl_sheet = read_xls.sheet_by_index(0)
            first_names = xl_sheet.col_values(4)
            last_names = xl_sheet.col_values(7)
            students_ids = xl_sheet.col_values(2)
            Logger(Importer.import_bys.__name__, "BYS Student list imported")
            for i in range(min(len(first_names),len(last_names))):

                if first_names[i] == "" or first_names[i] == "Adı" or last_names[i] == "" or last_names[i] == "Soyadı":
                    pass
                else:
                    getname = first_names[i].split(" ",1)
                    if (len(getname) > 1):
                        first_names[i] = getname[0]
                        middle_name = getname[1]
                    else:
                        middle_name = ''
                    if (n.isspace() for n in str(last_names[i])):
                        lastname = last_names[i].split(" ")[-1]
                    self.zpv.add_student(first_names[i], middle_name, lastname, students_ids[i])
            Logger(self.zpv.add_student.__name__,"All Student added successfully.")
        except Exception as err:
            Logger(Importer.import_bys.__name__, "Import Student list failed")
            print("Error Occurred", err)

    def import_answer_key_old(self, file_path):
        # Imports Answer Key
        # Uses related library csv or xls
        # @todo CSV version not working well with semicolon delimeter
        # @todo This library will no longer read anything other than .xls

        print(file_path)
        paths = self.get_paths(file_path, ["xls", "csv"])
        print("PATHS: ", paths)
        for file_path in paths:
            if os.path.basename(file_path).endswith(".xls"):
                read_xls = xlrd.open_workbook(file_path)
                xl_sheet = read_xls.sheet_by_index(0)
                question_texts = xl_sheet.col_values(0)
                answer_texts = xl_sheet.col_values(1)
                poll_name = xl_sheet.cell(0,0).value
                if poll_name == "Attendance Poll":
                    poll = self.zpv.add_poll(poll_name, "ATTENDANCE")
                else:
                    poll = self.zpv.add_poll(poll_name)
                print(poll.get_name(), " added successfully.")
                for i in range(2, min(len(question_texts), len(answer_texts))):
                    question = poll.add_question(question_texts[i])
                    question.add_choice(answer_texts[i], 1)

            elif os.path.basename(file_path).endswith(".csv"):
                with open(file_path, newline='', encoding="utf8") as csv_file:
                    csv_array = csv.reader(csv_file)
                    i = 0
                    for row in csv_array:
                        if i == 0:
                            poll_name = row[0]
                            if poll_name == "Attendance Poll":
                                poll = self.zpv.add_poll(poll_name, "ATTENDANCE")
                                Logger("add_attendance_poll",poll.get_name()+" answer key added successfully.")
                            else:
                                poll = self.zpv.add_poll(poll_name)

                                Logger("add_qoiz_poll",poll.get_name()+" answer key added successfully.")

                        else:
                            if len(row[0]) > 0:
                                question = poll.add_question(row[0])
                                question.add_choice(row[1], 1)
                        i += 1
                    Logger(poll.add_question.__name__, "Questions added successfully.")

    def import_poll_report(self, file_path):
        # Imports Poll Report
        paths = self.get_paths(file_path, ["csv"])
        Logger(Importer.import_poll_report.__name__, "Poll reports added successfully.")
        for file_path in paths:
            with open(file_path, newline='', encoding="utf8") as csv_file:
                csv_array = csv.reader(csv_file)
                poll_cache = {}
                i = 0
                for row in csv_array:
                    if i < 10:
                        pass
                    else:
                        if row[4] in poll_cache.keys():
                            poll = poll_cache[row[4]]
                        else:
                            for q_index in range(4, len(row), 2):
                                poll = self.zpv.get_poll_by_question(row[q_index])
                                if poll:
                                    break
                        session = self.zpv.add_session(row[3])
                        session.add_poll(poll)
                        full_name = row[1]
                        email = row[2]
                        self.control_student(full_name, email, session, poll, row)
                    i = i + 1
            Logger(self.zpv.add_session.__name__, " Sessions added successfully.")

    def get_paths(self, file_path, file_types):
        # Returns paths of dirextory
        paths = []
        if os.path.isdir(file_path):
            temp_paths = [f for f in listdir(file_path) if isfile(join(file_path, f))]
            for temp_path in temp_paths:
                for file_type in file_types:
                    if os.path.basename(temp_path).endswith("."+file_type):
                        paths.append(file_path+"/"+temp_path)
        else:
            paths.append(file_path)
        return paths

    def control_student(self,full_name,email,session,poll,row):
         # This part is is to control every step of import
        student = self.zpv.get_student(full_name, email)
        if student:
            response = student.add_response(session, poll)
            #Logger(Importer.control_student.__name__+ str(self.zpv.get_student(full_name,email)), "Student answers added")
            for j in range(4, len(row) - 1, 2):
                response.add_answer(row[j], row[j + 1])
        else:            
            #If fullName has digit inside it will be cleared
            if(any(a.isnumeric() for a in full_name)):
                full_name = ''.join(b for b in full_name if b.isalpha() or b.isspace())
                #print("Student name had integer in, ", full_name)
                Logger("control_student_isnumeric", "Student name had integer in, "+ full_name)
            #If fullName has not space inside it and starts with uppercase resumed lowercase we will add space before A-Z.a-z
            if not(any(c.isspace() for c in full_name)):
                full_name =  re.sub(r"(\w)([A-Z][a-z])", r"\1 \2", full_name)
                Logger("control_student_isspace", "Student name had no space in, " +full_name)
                #print("Student name had no space in, ", full_name)
            else:
                #If fullName has space inside it and starts with uppercase resumed lowercase we will break it to array and add space before A-Z.a-z
                newname = full_name.split()
                result = []
                for m in newname:
                     m =  re.sub(r"(\w)([A-Z][a-z])", r"\1 \2", m)
                     result.append(m)
                full_name = ' '.join(result)
                #print("Student name had space in, ", full_name)
                
            # This part is is to control every step of import
            student = self.zpv.get_student(full_name, email)
            if student:
                #print("Student found")
                Logger("control_student_found", "Student found, " +full_name)
                response = student.add_response(session, poll)
                for j in range(4, len(row) - 1, 2):
                    response.add_answer(row[j], row[j + 1])
            else:
                Logger("control_student_not_found", "Student found, " +full_name)
                pass
                #print("Student not found")
        
        return 0

    def import_answer_key(self, file_path):
        print(file_path)
        paths = self.get_paths(file_path, ["txt"])
        print("PATHS: ", paths)
        poll = self.zpv.add_poll("Attendance", "ATTENDANCE")
        question = poll.add_question("Are you attending this lecture?")
        question.add_choice("Yes", True)

        for file_name in paths:
            f = open(file_name)
            lines = f.readlines()

            last_poll = ""
            question_id = 1
            last_question = ""
            polls = {}
            counter = 0

            for line in lines:
                if counter < 2:
                    pass
                else:
                    if line[1:5] == "Poll":
                        poll_name = line[8:-15].split("\t")[0]
                        poll = self.zpv.add_poll(poll_name)
                        last_poll = poll
                        question_id = 1
                    elif line.split(".")[0] == str(question_id):
                        if " ( Multiple Choice)\n" in line:
                            question_text = line[:-20]
                        elif " ( Single Choice)\n" in line:
                            question_text = line[:-18]
                        question_text = ". ".join(question_text.split(". ")[1:])
                        question = poll.add_question(question_text)
                        last_question = question
                        question_id += 1
                    elif line[:6] == "Answer":
                        answer_text = line[10:-1]
                        question.add_choice(answer_text, True)
                counter += 1