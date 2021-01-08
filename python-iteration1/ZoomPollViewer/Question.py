#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Question:

    def __init__(self, text, type="QUIZ"):
        self._text = text
        self._type = type
        self._choices = []
        #type can be quiz or attandance

    def add_choice(self, choice):
        self._choices.append(choice)