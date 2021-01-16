#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import xlsxwriter
from datetime import date

from vincent.colors import brews

question_section={1:'A',2:'B',3:'C',4:'D',5:'E',6:'F',7:'G',8:'H'}
# Some sample data to plot.
cat_4 = [ question_section[x] for x in range(1, 8)]


class Exporter():
    def __init__(self,zpv):
        self.zpv = zpv
        self.path = "./"
    # To Create xlsx File
    def create_xlsx(self,name:str):
        self.xlsx_file = xlsxwriter.Workbook(self.path+name)
    # To Create a Page in xlsx File
    def create_xlsx_page(self,name:str):
        self.xlsxpage = self.xlsx_file.add_worksheet(name) 
    # Writing Data
    def write_xlsx_page_data(self,col:int,row:int,data:str):
        self.xlsxpage.write(col,row,data)
    def write_xlsx_page_data_title(self,col:int,row:int,data:str):
        self.xls_cell_format = self.xlsx_file.add_format({'bold':True})
        self.xlsxpage.write(col,row,data,self.xls_cell_format)
    def write_xlsx_page_chart(self,col,row,data):
        self.chart = self.xlsx_file.add_chart()
    def close_xlsx(self):
        self.xlsx_file.close()        
    def export_global(self):
        now = date.today()
        r = 1        
        self.create_xlsx('Global.xlsx')
        self.create_xlsx_page(str(now))
        self.write_xlsx_page_data_title(0,0,'BYS')        
        self.write_xlsx_page_data_title(1,0,'ID')
        self.write_xlsx_page_data_title(1,1,'NAME')
        self.write_xlsx_page_data_title(1,2,'SURNAME')    
        c=3   
        for poll in self.zpv._polls:
            self.xlsxpage.merge_range(0,c,0,c+2,str(poll._name))
            self.write_xlsx_page_data_title(1,c,'DATE')
            c+=1
            self.write_xlsx_page_data_title(1,c,'NOQ')
            c+=1
            self.write_xlsx_page_data_title(1,c,'SP')
            c+=1
        for student in self.zpv._students:            
            if student.get_email() != None:
                c = 0
                r += 1
                self.write_xlsx_page_data(r,c,str(student.get_student_id())) 
                c += 1
                self.write_xlsx_page_data(r,c,str(student.get_name())) 
                c += 1
                self.write_xlsx_page_data(r,c,student.get_last_name())
                for poll in self.zpv._polls:                      
                    response = student.get_response_by_poll(poll)
                    if response:
                        c += 1
                        self.write_xlsx_page_data(r,c,response._session._date_text) 
                        c += 1
                        self.write_xlsx_page_data(r,c,response._poll._number_of_questions) 
                        c += 1
                        self.write_xlsx_page_data(r,c,response.get_grade())    
                    else:
                        c += 3
        self.close_xlsx()                
    def draw_histogram(self,type,column):
        self.create_xlsx('Global.xlsx')
        self.create_xlsx_page('Histogram')
        chart=self.xlsx_file.add_chart({type:column})
        for col_num in range(1, len(cat_4) + 1):
            chart.add_series({
                'name': ['Sheet1', 0, col_num],
                'categories': ['Sheet1', 1, 0, len(cat_4) + 1, 0],
                'values': ['Sheet1', 1, col_num, len(cat_4) + 1, col_num],
                'fill': {'color': brews['Spectral'][col_num - 1]},
                'gap': 300,
            })

        chart.set_y_axis({'major_gridlines':{'visible':False}})
        self.xlsxpage.insert_chart('K2',chart)


m = Exporter()
m.create_xlsx('Poll.xlsx')
m.create_xlsx_page('Pager-1')
m.write_xlsx_page(0,0,'Selam')
m.create_xlsx_page('Pager-2')
m.write_xlsx_page(0,0,'Selam2')
m.close_xlsx()