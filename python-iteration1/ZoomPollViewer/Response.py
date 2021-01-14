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

        self._grade = None

    def get_student(self):
        return self._student

    def get_session(self):
        return self._session

    def get_poll(self):
        return self._poll

    def add_answer(self, question_text, answer_texts):
        question = self._poll.get_question(question_text)
        choices = []
        if isinstance(answer_texts, list):
            for answer_text in answer_texts:
                choice = question.add_choice(answer_text, 0)
                choices.append(choice)
        else:
            choice = question.add_choice(answer_texts, 0)
            choices.append(choice)

        if question not in self._answers.keys():
            self._answers[question] = []

        for choice in choices:
            self._answers[question].append(choice)

    def get_grade(self):
        if self._grade is None:
            self.calculate_grade()
        return self._grade

    def calculate_grade(self):
        true_answers = 0
        false_answers = 0
        for question in self._poll.get_questions():
            correct_choices = question.get_correct_choices()
            if question in self._answers.keys():
                if len(correct_choices) == len(self._answers[question]) and all(i in correct_choices for i in self._answers[question]):
                    true_answers += 1
                else:
                    false_answers += 1
        self._grade = round(true_answers / (self._poll.get_number_of_questions()) * 100, 2)
