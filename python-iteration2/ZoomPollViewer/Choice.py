#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v1.0
CHOICE CLASS
Lines 11
1
"""


#
class Choice:

    def __init__(self, question, text, correctness=0):

        self.question = question
        self._text = text
        self._correctness = correctness

    def get_text(self):
        # Returns text of choice
        return self._text

    def get_correctness(self):
        # Returns correctness of choice
        return self._correctness

    def set_correct(self):
        # Sets correctness of choice
        self._correctness = 1
