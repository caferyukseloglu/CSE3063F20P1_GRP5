#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1
POLL CLASS
11 Function
3 Object
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
        # Returns name
        return self._name

    def get_type(self):
        # Returns type
        return self._type

    def get_questions(self):
        # Returns question object
        return self._questions

    def get_question(self, question_text):
        # Returns question of given text
        for question in self._questions:
            if question.get_text() == question_text:
                return question
        question_text = self.question_formatter(question_text)
        for question in self._questions:
            if self.question_formatter(question.get_text()) == self.question_formatter(question_text):
                return question
        return False

    def question_formatter(self, question_text):
        result = question_text.replace(" ", "")
        result = result.replace("\t", "")
        result = result.replace("\n", "")
        return result


    def get_number_of_questions(self):
        # Returns number of questions
        if self._number_of_questions is None:
            self._number_of_questions = len(self._questions)
        return self._number_of_questions

    def get_number_of_students(self):
        # Returns number of student
        total = 0
        print(self._number_of_students)
        for session in self._number_of_students:
            if self._number_of_students[session] > total:
                total = self._number_of_students[session]
        return total

    def add_question(self, question_text):
        # Adds question object and returns it
        question = self.get_question(question_text)
        if question:
            return question
        else:
            question = Question(self, question_text)
            self._questions.append(question)
            return question

    def set_session_grades(self, session, grades):
        # Sets grade of given session
        self._session_grades[session] = grades

    def get_grades_of_seesion(self, session):
        # Returns grade of given session
        return self._session_grades[session]

    def set_session_number_of_students(self, session, number_of_students):
        # Sets session attendance
        self._number_of_students[session] = number_of_students

    def calculate_session_average_grade(self):
        # Calculates average grade
        if len(self._session_grades) > 0:
            for i in self._session_grades:
                grades = self._session_grades[i]
                break
            if len(grades) > 0:
                return sum(grades) / len(grades)
            else:
                return 0

    def get_number_of_max_choices(self):
        # Returns number of choices
        new_max = 0
        for question in self.get_questions():
            if len(question._choices) > new_max:
                new_max = len(question._choices)
        return new_max
