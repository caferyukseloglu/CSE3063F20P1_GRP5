#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import xlsxwriter

class Exporter:
    def __init__(self):
        self.path = "./"
    # To Create xlsx File
    def create_xlsx(self,name:str):
        self.xlsx_file = xlsxwriter.Workbook(self.path+name)
    # To Create a Page in xlsx File
    def create_xlsx_page(self,name:str):
        self.xlsxpage = self.xlsx_file.add_worksheet(name) 
    # Writing Data
    def write_xlsx_page(self,col:int,row:int,data:str):
        self.xlsxpage.write(col,row,data)
    def close_xlsx(self):
        self.xlsx_file.close()

m = Exporter()

m.create_xlsx('Poll.xlsx')
m.create_xlsx_page('Pager-1')
m.write_xlsx_page(0,0,'Selam')
m.create_xlsx_page('Pager-2')
m.write_xlsx_page(0,0,'Selam2')
m.close_xlsx()