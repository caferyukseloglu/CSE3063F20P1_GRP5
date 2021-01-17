#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Choice:

    def __init__(self, question, text, correctness=0):

        self.question = question
        self._text = text
        self._correctness = correctness
        # @todo Correctness can be True/False or 1/0 else dont assign. Please check === operator

    def get_text(self):
        return self._text

    def get_correctness(self):
        return self._correctness

    def set_correct(self):
        self._correctness = 1