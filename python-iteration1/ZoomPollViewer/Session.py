#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Session:

    def __init__(self, date):
        self._date = date # we need to decide date format
        self._polls = []

    def add_poll(self, poll):
        self._polls.append(poll)