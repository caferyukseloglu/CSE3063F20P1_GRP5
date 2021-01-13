#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
from .Response import Response


class Student:

    def __init__(self, zpv, first_name, last_name, student_id):

        self.zpv = zpv

        self._firs_name = first_name
        self._last_name = last_name
        self._email = None
        self._student_id = student_id


        # @todo While creating object of this Student class, we need to check all students to ensure email is unique.
        # @todo Main class is going to has check_student_email method.

        self._responses = [] # this is the list of objects of responses

    def get_first_name(self):
        return self._first_name

    def get_last_name(self):
        return self._last_name

    def get_email(self):
        return self._email

    def get_student_id(self):
        return self._student_id

    def get_responses(self):
        return self._responses

    def set_email(self, email):
        if self.zpv.check_student_email(email):
            self._email = email
        else:
            return False

    def add_response(self, session, poll):
        response = Response(self, session, poll)
        self._responses.append(response)
        return response