#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

from .Question import Question

class Poll:

    def __init__(self, poll_name):
        self._name = poll_name
        self._questions = []

    def add_question(self, text):
        question  = Question(text)
        self._questions.append(question)
        return question
        #check if exist. IF does not exist add, else dont.
