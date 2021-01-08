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

    def add_session(self, session):
        self._sessions.append(session)

    def add_student(self, name):
        student = Student(name)
        self._students.append(student)
        return student

    def add_poll(self, poll):
        self._polls.append(poll)

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



        SESS1 = Session(20201102)

        SESS1.add_poll(POLL1)

