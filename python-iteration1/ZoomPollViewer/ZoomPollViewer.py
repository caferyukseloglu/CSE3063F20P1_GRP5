#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import logging
from .Student import Student
from .Poll import Poll
from .Session import Session
from .Logger import Logger
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
        # Logging example
        #
        #
        log=Logger(ZoomPollViewer.add_session.__name__,"deneme")

        return session

    def add_student(self, name, surname, student_id):
        student = Student(name, surname, student_id)
        self._students.append(student)
        # maybe check if exist
        return student

    # get_student # cafer yapacak

    def get_poll(self, question_text):
        # get poll which has question with given text.
        # else return none or -1 or 0
        pass


    def add_poll(self, poll_name):
        poll = Poll(poll_name)
        self._polls.append(poll)
        # maybe check if exist
        return poll

    def get_student(self, fullname, email):
        found = 0
        for student in self._students:
            if student._email == email:
                found = student
        if found == 0:
            fullname = str(fullname).split(" ")
            firstname = fullname[0]
            surname = fullname[-1]
            for student in self._students:
                if str(student._firstname).lower() == firstname.lower() and str(student._surname).lower() == surname.lower():
                    student._email = email
                    found = student
        return found

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
    # AD, SOYAD, OGRENCI-NO
    # main.add_student -> AD, SOYAD, OGRENCI-NO (3 input)

# 2. Poll Answer Key (question, answer)
    # main.add_poll -> verilen isimde poll oluştur ve oluşturulan poll objesini döndür.
    # poll.add_question -> input olarak question text alacak. question objesini döndürecek.
    # question.add_choice -> input olarak choice text ve correctness alacak. correctness = 1/0

# 3. Poll report (students)
    # student = main.get_student(fullname, email) @todo cafer bakacak.
    # @todo student class'ında email array olmalı. student classında add_email diye method olmalı.
    # main.add_session(date_time_text) // session üretecek ancak date time modülünü kullanacak. session return
    # poll = main.get_poll(question_text) // verilen soru ile _poll arrayinde arama yapılacak ve ilgili poll return edilecek.
    # student.add_response(question_text, answer_texts, poll) -> ogrenciye response objesi olusturacak ve response içerisinde verilen bilgileri tutacak
    #      => poll loop question -> verilen question_text ile question objesi return edilecek.
    # her answer_text için => loop ile choice aranacak. verilen choice_text ile choice objesi döndürülecek.
    #               -> eğer choice question içerisinde yoksa oluşturulacak
    #      => response.add_answer(<question>, [<answer>]) response classı içerisinde answer dicte eklenecek.


# Session -> * poll
# 1 poll -> * question
# 1 question -> * choice
# 1 student -> * response
a=ZoomPollViewer().add_session(2222)
