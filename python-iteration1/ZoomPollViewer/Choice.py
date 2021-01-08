#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Choice:

    def __init__(self, text, correctness=0):
        self._text = text
        self._correctness = correctness
        # Correctness can be True/False or 1/0 else dont assign. Please check === operator

    def set_correct(self):
        self._correctness = 1
