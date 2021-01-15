#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
from datetime import datetime
import logging
from .Student import Student
from .Poll import Poll
from .Session import Session
from .GUI import GUI
from .Importer import Importer
from .Exporter import Exporter
from .Logger import Logger

class ZoomPollViewer:
    def __init__(self):

        self._students = []
        self._sessions = []
        self._polls = []

        self.GUI = GUI(self)
        self.importer = Importer(self)
        self.exporter = Exporter(self)
        self.date_time_format = "%b %d, %Y %H:%M:%S"
        self.file_name_date_format = "%Y%m%d"
        self._is_logger_active = True
        self._import_completed = False
        self.GUI.root.mainloop()
        self.GUI.sm.mainloop()

    def get_student(self, full_name, email):
        found = False
        for student in self._students:
            if student._email == email:
                found = student
        if not found:
            full_name_splitted = str(full_name).split(" ")
            first_name = full_name_splitted[0]
            last_name = full_name_splitted[-1]
            for student in self._students:
                # For student name has Turkish lower
                # TODO: IF student is match before don't loop in for student obj for that student...
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
                if (str(student.get_first_name()).strip().translate(lower_map).lower() == first_name.strip().translate(lower_map).lower() and str(student.get_last_name()).strip().translate(lower_map).lower() == last_name.strip().translate(lower_map).lower()):
                    student._email = email
                    return student
            return self.add_temporary_student(full_name, email)
        return found

    def get_sessions(self):
        return self._sessions

    def get_student_by_id(self, bys_id):
        for student in self._students:
            if str(student.get_student_id()) == str(bys_id):
                return student
        return False

    def get_student_by_email(self, email):
        for student in self._students:
            if student.get_email() == email:
                return student
        return False

    def get_poll_by_question(self, question_text):
        for poll in self._polls:
            for question in poll.get_questions():
                if question_text == question.get_text():
                    return poll
        return False

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

    def get_number_of_students(self):
        return len(self._students)

    def get_number_of_sessions(self):
        return len(self._sessions)

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

    def metrics_calculator(self):
        for session in self._sessions:
            session_attendance = 0
            for poll in session.get_polls():
                poll_grades = []
                poll_attendance = 0
                for student in self._students:
                    response = student.get_response_by_session_and_poll(session, poll)
                    if response:
                        if poll.get_type() == "ATTENDANCE":
                            student.add_session_attendance(session)
                            session_attendance += 1
                            poll_attendance += 1
                        elif poll.get_type() == "QUIZ":
                            poll_grades.append(response.get_grade())
                            poll_attendance += 1
                poll.set_session_grades(session, poll_grades)
                poll.set_session_number_of_students(session, poll_attendance)
                print(poll, poll_attendance)
            session.set_attendance(session_attendance)
        self.student_metrics_calculator()

    def student_metrics_calculator(self):
        for student in self._students:
            student.calculate_grades()

    def add_temporary_student(self, full_name, email):
        student = Student(self, full_name, "", "", "", True)
        student.set_email(email)
        self._students.append(student)
        return student

    def get_unmatched_temporary_students(self):
        unmatched_students = []
        for student in self._students:
            if student.get_temporary_state():
                unmatched_students.append(student)
        return unmatched_students

    def get_unmatched_bys_students(self):
        unmatched_students = []
        for student in self._students:
            if student.get_email() is None:
                unmatched_students.append(student)
        return unmatched_students

    def match_students(self, bys_id, email):
        student = self.get_student_by_id(bys_id)
        temporary_student = self.get_student_by_email(email)
        student.set_email(email)
        self._students.remove(temporary_student)
        print("Student MATCHED!")

    def check_unmatched_student_exist(self):
        for student in self._students:
            if student.get_temporary_state():
                return True
        return False

