#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Session:

    def __init__(self, zpv, date_time_object):

        self.zpv = zpv
        self._date_time = date_time_object
        self._date_text = date_time_object.strftime(self.zpv.file_name_date_format)
        self._polls = []

        self._attendance = 0

    def get_date_time(self):
        return self._date_time

    def get_date_text(self):
        return self._date_text

    def get_polls(self):
        return self._polls

    def get_attendance(self):
        return self._attendance

    def get_date_formatted(self):
        return self._date_time.strftime("%Y - %m - %d")

    def add_poll(self, poll):
        if poll not in self._polls:
            self._polls.append(poll)

    def get_absent(self):
        return self.zpv.get_number_of_students() - self._attendance

    def get_number_of_polls(self):
        return len(self._polls)

    def set_attendance(self, attendance):
        self._attendance = attendance

    def get_attendance_percentage(self):
        total = self.zpv.get_number_of_students()
        return round(self._attendance / total * 100, 2)

    def calculate_average_grade(self):
        total_grades = []
        for poll in self._polls:
            if poll.get_type() == "QUIZ":
                total_grades.extend(poll.get_grades_of_seesion(self))
        if len(total_grades) > 0:
            return sum(total_grades) / len(total_grades)
        return 0

