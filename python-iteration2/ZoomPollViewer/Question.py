#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v1.0
QUESTION CLASS
Function 4
0 Object
"""
from .Choice import Choice


class Question:

    def __init__(self, poll, question_text):

        self.poll = poll
        self._text = question_text
        self._choices = []

    def get_text(self):
        # Returns text of question
        return self._text

    def get_choices(self):
        # Returns choice object
        return self._choices

    def get_correct_choices(self):
        # Returns list of choices
        correct_choices = []
        for choice in self._choices:
            if choice.get_correctness() == 1:
                correct_choices.append(choice)
        return correct_choices

    def get_choice(self, choice_text):
        # Returns choices of given text
        for choice in self._choices:
            if choice.get_text() == choice_text:
                return choice
        return False

    def add_choice(self, choice_text, correctness=0):
        # Adds choice and returns its object
        choice = self.get_choice(choice_text)
        if choice:
            return choice
        else:
            choice = Choice(self, choice_text, correctness)
            self._choices.append(choice)
            return choice
