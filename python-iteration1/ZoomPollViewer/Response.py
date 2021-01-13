#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
#from .ZoomPollViewer import ZoomPollViewer


class Response:

    def __init__(self, student, session, poll):
        self._student = student
        self._session = session
        self._poll = poll

        # @todo need to check if poll does not belongs to session

        self._answers = {}
        # _answers = { <question_A> :  [<choice_1>, <choice_2>], <question_B> :  [<choice_2>]}

    def add_answer(self, question_text, answer_texts):
        question = self._poll.get_question(question_text)
        choices = []
        if isinstance(answer_texts, list):
            for answer_text in answer_texts:
                choice = question.get_choice(answer_text)
                choices.append(choice)
                #
        else:
            choice = question.get_choice(answer_texts)
            choices.append(choice)

        if question not in self._answers:
            self._answers[question] = []

        for choice in choices:
            self._answers[question].append(choice)