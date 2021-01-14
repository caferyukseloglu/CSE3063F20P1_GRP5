#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

from .Question import Question


class Poll:

    def __init__(self, zpv, poll_name, poll_type="QUIZ"):

        self.zpv = zpv
        self._name = poll_name
        self._type = poll_type
        self._questions = []

        self._number_of_questions = None
        self._session_grades = {}
        self._number_of_students = {}

    def get_name(self):
        return self._name

    def get_type(self):
        return self._type

    def get_questions(self):
        return self._questions

    def get_question(self, question_text):
        for question in self._questions:
            if question.get_text() == question_text:
                return question
        return False

    def get_number_of_questions(self):
        if self._number_of_questions == None:
            self._number_of_questions = len(self._questions)
        return self._number_of_questions

    def get_number_of_students(self):
        # @todo not ready yet
        total = 0
        print(self._number_of_students)
        for session in self._number_of_students:
            total += self._number_of_students[session]
        return total

    def add_question(self, question_text):
        question = self.get_question(question_text)
        if question:
            return question
        else:
            question = Question(self, question_text)
            self._questions.append(question)
            return question

    def set_session_grades(self, session, grades):
        self._session_grades[session] = grades

    def get_grades_of_seesion(self, session):
        return self._session_grades[session]

    def set_session_number_of_students(self, session, number_of_students):
        self._number_of_students[session] = number_of_students

    def calculate_session_average_grade(self):
        for i in self._session_grades:
            grades = self._session_grades[i]
            break

        if len(grades) > 0:
            return sum(grades) / len(grades)
        else:
            return 0
