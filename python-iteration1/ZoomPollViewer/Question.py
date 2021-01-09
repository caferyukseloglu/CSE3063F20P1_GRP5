#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
from .Choice import Choice


class Question:

    def __init__(self, text, type="single"):
        self._text = text
        self._type = type
        self._choices = []
        #type can be quiz or attandance

    def add_choice(self, text, correctness=0):
        choice = Choice(text, correctness)
        self._choices.append(choice)
        return choice

    def get_choice(self, choice_text):
        # check all choices text to get given
        # if not exist create one
        # then return choice object
        pass