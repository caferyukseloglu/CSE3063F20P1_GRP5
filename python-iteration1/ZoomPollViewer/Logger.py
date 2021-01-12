#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import logging

class Logger:

    def __init__(self):
        self.logger = logging.getLogger("ZoomPollViewer")
        self.logger.setLevel(logging.INFO)

        self.fh = logging.FileHandler("ZoomPollViewer.log")
        self.formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
        self.fh.setFormatter(self.formatter)
        self.logger.addHandler(self.fh)
        self.logger.info("run")