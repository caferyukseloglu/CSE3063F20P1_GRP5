#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

from .Question import Question


class Poll:

    def __init__(self, ZPV, poll_name, poll_type="QUIZ"):

        self.ZPV = ZPV
        self._name = poll_name
        self._type = poll_type
        self._questions = []

    def get_name(self):
        return self._name

    def get_question(self, question_text):
        for question in self._questions:
            if question.get_text() == question_text:
                return question
        return False

    def add_question(self, question_text):
        question = self.get_question(question_text)
        if question:
            return question
        else:
            question = Question(self, question_text)
            self._questions.append(question)
            return question
