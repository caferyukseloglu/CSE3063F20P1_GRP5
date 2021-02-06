#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v1.0
LOGGING CLASS

"""
import logging


class Logger:

    def __init__(self, text1, text2):
        self.logger = logging.getLogger(text1)
        self.logger.setLevel(logging.INFO)

        self.fh = logging.FileHandler("ZoomPollViewer.log")
        self.ch = logging.StreamHandler()
        self.formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
        self.fh.setFormatter(self.formatter)
        self.ch.setFormatter(self.formatter)
        self.logger.addHandler(self.fh)
        self.logger.addHandler(self.ch)
        self.logger.info(text2)
