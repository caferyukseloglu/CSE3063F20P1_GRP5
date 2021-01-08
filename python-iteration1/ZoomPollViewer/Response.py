#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

class Response:

    def __init__(self, student, session, poll):
        self._student = student
        self._session = session
        self._poll = poll
        # @todo need to check if poll does not belongs to session
        self._answers = {}
        # _answers = { <question_A> :  [<choice_1>, <choice_2>], <question_B> :  [<choice_2>]}

    def add_answer(self, question, choice):
        pass
