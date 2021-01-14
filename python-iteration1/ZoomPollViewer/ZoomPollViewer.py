#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
from datetime import datetime
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

        self.date_time_format = "%b %d, %Y %H:%M:%S"
        self.file_name_date_format = "%Y%m%d"
        self._is_logger_active = True

    def get_student(self, full_name, email):
        found = False
        for student in self._students:
            if student._email == email:
                found = student
        if not found:
            full_name = str(full_name).split(" ")
            first_name = full_name[0]
            last_name = full_name[-1]
            for student in self._students:
                # For student name has Turkish lower
                lower_map = {
                    ord(u'C'): u'c',
                    ord(u'Ç'): u'ç',                    
                    ord(u'G'): u'g',
                    ord(u'Ğ'): u'ğ',
                    ord(u'I'): u'ı',
                    ord(u'İ'): u'i',                    
                    ord(u'O'): u'o',
                    ord(u'Ö'): u'ö',
                    ord(u'U'): u'u',
                    ord(u'Ü'): u'ü',
                    ord(u'S'): u's',
                    ord(u'Ş'): u'ş',
                }
                # Strip used to get away from spaces
                if (str(student._firs_name).strip().translate(lower_map).lower() == first_name.strip().translate(lower_map).lower() and str(student._last_name).strip().translate(lower_map).lower() == last_name.strip().translate(lower_map).lower()):
                    student._email = email
                    return student
        return found

    def get_poll_by_question(self, question_text):
        for poll in self._polls:
            for question in poll._questions:
                if question_text == question._text:
                    return poll
        # @todo It may throw error.
        return 0

    def get_poll_by_name(self, poll_name, poll_type):
        for poll in self._polls:
            if poll.get_name() == poll_name:
                return poll
        return False

    def get_session(self, date_time_text):
        date_time_object = datetime.strptime(date_time_text, self.date_time_format)
        date_text = date_time_object.strftime(self.file_name_date_format)
        for session in self._sessions:
            if session.get_date_text() == date_text:
                return session
        return False
        #Logger(ZoomPollViewer.add_session.__name__,"deneme")

    def add_student(self, firstname, middlename, surname, student_id):
        student = Student(self, firstname, middlename, surname, student_id)
        self._students.append(student)
        # @todo It may check if user exist
        return student

    def add_session(self, date_time_text):
        session = self.get_session(date_time_text)
        if session:
            return session
        else:
            date_time_object = datetime.strptime(date_time_text, self.date_time_format)
            session = Session(self, date_time_object)
            self._sessions.append(session)
            return session

    def add_poll(self, poll_name, poll_type="QUIZ"):
        for poll in self._polls:
            if poll.get_name() == poll_name:
                return poll
        poll = Poll(self, poll_name, poll_type)
        self._polls.append(poll)
        return poll

    def check_student_email(self, email):
        for student in self._students:
            if student.get_email() == email:
                return True
        return False

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
    # session = main.add_session(date_time_text) // session üretecek ancak date time modülünü kullanacak. session return
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
