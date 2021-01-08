#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""

from .Session import Session


class ZoomPollViewer:

    def __init__(self):

        self._students = []
        self._sessions = []
        self._polls = []

        self.__apples = 123
        self.GUI = 0        # not implemented yet
        self.loggerActive = True
        self.importerType = "TABLE" #API OR TABLE (API for v2)

    def add_session(self, session):
        self._sessions.append(session)

    def add_student(self, student):
        self._students.append(student)

    def add_poll(self, poll):
        self._polls.append(poll)

    def test(self):
        a = Session(0000)





