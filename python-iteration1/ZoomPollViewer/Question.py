#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
from .Choice import Choice


class Question:

    def __init__(self, poll, question_text):

        self.poll = poll
        self._text = question_text
        self._choices = []

    def get_text(self):
        return self._text

    def get_choices(self):
        return self._choices

    def get_choice(self, choice_text, correctness=0):
        for choice in self._choices:
            if choice.get_text() == choice_text:
                return choice
        return False

    def add_choice(self, choice_text, correctness=0):
        choice = self.get_choice(choice_text)
        if choice:
            return choice
        else:
            choice = Choice(choice_text, correctness)
            self._choices.append(choice)
            return choice
