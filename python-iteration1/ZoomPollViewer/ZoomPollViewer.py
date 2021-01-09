#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

from .Student import Student
from .Poll import Poll
from .Session import Session

class ZoomPollViewer:

    def __init__(self):

        self._students = []
        self._sessions = []
        self._polls = []

        self.GUI = 0        # not implemented yet
        self.loggerActive = True
        self.importerType = "TABLE" #API OR TABLE (API for v2)

    def add_session(self, date):
        session = Session(221023)
        self._sessions.append(session)
        return session

    def add_student(self, name, surname, email, id):
        student = Student(name, surname, email, id)
        self._students.append(student)
        # maybe check if exist
        return student

    # get_student # cafer yapacak

    def get_poll(self, question_text):
        # get poll which has question with given text.
        # else return none or -1 or 0
        pass

    def add_poll(self, text):
        poll = Poll(text)
        self._polls.append(poll)
        # maybe check if exist
        return poll

    def test(self):

        #### 1 - OGRENCILERIN EKLENLESI
        EST = self.add_student("Emin Safa Tok")
        ZOK = self.add_student("Orhun Zülküf Özkaya")



        #### 2 - ANSWER KEY YUKLENMESI
        POLL1 = Poll("Örnek Poll")
        for i in range(10):
            question = POLL1.add_question("Bu örnek bir soru - "+str(i))
            question.add_choice("Örnek seçenek ABC", 1)



        #### 3 - ÖĞRENCİ CEVAPLARININ YÜKLENMESİ

        SESS1 = Session(20201102) # öğrenci cevapları yüklenirken session da tespit ediliyor.
        SESS1.add_poll(POLL1)





# 1. BYS (ad soyad email)
# 2. Poll Answer Key (question, answer)
# 3. Poll answers (students)
