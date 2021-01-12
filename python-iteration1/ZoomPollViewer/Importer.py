#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Importer():

    def __init__(self, ZPV):

        self.ZPV = ZPV
        self.path = ""


    def import_answer_key(self):
        poll = self.ZPV.add_poll("value")
        for i in range(10):
            question = poll.add_question("q text")
            question.add_choice("answer", 1)

    def import_poll_report(self):
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

    def import_bys(self):
        #self.ZPV.GUI.insert_student()
        self.ZPV.GUI.insert_student({"id":1453, "firstname":"Emin Safa", "lastname":"Tok", "email":"esafa.tok@gmail.com"})

    def import_poll_report(self):
        self.ZPV.GUI.insert_student(
            {"id": 1453, "firstname": "Emin Safa", "lastname": "Tok", "email": "esafa.tok@gmail.com"})