#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

class Student:

    def __init__(self, name, surname, email=""):
        self._name = name
        self._surname = surname
        self._email = ""
        # @todo While creating object of this Student class, we need to check all students to ensure email is unique.
        # @todo Main class is going to has check_student_email method.
        self._responses = [] # this is the list of objects of responses


    def set_email(self, email):
        # @todo While creating object of this Student class, we need to check all students to ensure email is unique.
        # @todo Main class is going to has check_student_email method.
        self._email = email

    def add_response(self, response):
        pass