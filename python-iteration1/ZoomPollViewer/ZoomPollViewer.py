#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
from ZoomPollViewer import *


class ZoomPollViewer:

    def __init__(self):

        self._students = []
        self._sessions = []
        self._polls = []

        self.GUI = 0        # not implemented yet
        self.loggerActive = True
        self.importerType = "TABLE" #API OR TABLE

    def add_session(self, session):
        self._sessions.append(session)

    def add_student(self, student):
        self._students.append(student)

    def add_poll(self, poll):
        self._polls.append(poll)



tester = ZoomPollViewer()