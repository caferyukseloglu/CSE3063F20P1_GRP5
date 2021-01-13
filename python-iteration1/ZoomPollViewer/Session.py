#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""


class Session:

    def __init__(self, zpv, date_time_object):

        self.zpv = zpv
        self._date_time = date_time_object
        self._date_text = date_time_object.strftime(self.zpv.file_name_date_format)
        self._polls = []

    def get_date_time(self):
        return self._date_time

    def get_date_text(self):
        return self._date_text

    def get_polls(self):
        return self._polls

    def add_poll(self, poll):
        self._polls.append(poll)