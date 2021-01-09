#!/usr/bin/python
# -*- coding: utf-8 -*-
import xlrd
from os.path import join, dirname, abspath

import csv
with open('CSE3063_20201123_Mon_zoom_PollReport.csv', newline='') as csvfile:
    csv_array = csv.reader(csvfile)
    i = 0
    for row in csv_array:
        if i == 0:
            pass
        else:
            name = row[1]
            email = row[2]
            # add_student( fullname, email )
            for j in range(4, len(row)-1, 2):
                question = row[j]
                answer = row[j+1]
                # add_question ( question, email )
        i = i + 1
        if i == 3:
            break









import xlrd

book = xlrd.open_workbook("tablo4.xls", formatting_info=True)

sheet = book.sheet_by_index(0)

rows = sheet.nrows

for row in range(1,rows):
    question = sheet.cell(row, 1).value
    answer = sheet.cell(row, 2).value
