#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import xlsxwriter
from datetime import date


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
        #To Export Global Report      
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
     #To Export Poll Report  
    def export_poll(self):       
        for poll in self.zpv._polls:
            r = 0
            c = 3
            self.create_xlsx(poll.get_name()+'.xlsx')
            self.create_xlsx_page('POLL STUDENT')
            self.write_xlsx_page_data_title(0,0,'STUDENT ID')        
            self.write_xlsx_page_data_title(0,1,'E-MAIL')
            self.write_xlsx_page_data_title(0,2,'NAME')
            self.write_xlsx_page_data_title(0,3,'SURNAME')
            for question in poll.get_questions():
                c += 1
                self.write_xlsx_page_data_title(0,c,question.get_text())
            self.write_xlsx_page_data_title(0,c+1,'SUCCESS RATE')
            self.write_xlsx_page_data_title(0,c+2,'SUCCESS PERCENTAGE')
            for student in self.zpv._students:
                c = 0
                response = student.get_response_by_poll(poll)
                if response:
                    for question in poll.get_questions():
                        my_choice = response._answers
                        r += 1
                        c += 1                    
                        self.write_xlsx_page_data(r,c,str(student.get_student_id())) 
                        c += 1
                        self.write_xlsx_page_data(r,c,str(student.get_email())) 
                        c += 1
                        self.write_xlsx_page_data(r,c,str(student.get_name())) 
                        c += 1
                        self.write_xlsx_page_data(r,c,student.get_last_name()) 
                        c += 1
                        for right_choice in poll.get_correct_choices():
                            if response == right_choice:
                                self.write_xlsx_page_data(r,c,'1') 
                                break
                            else:
                                self.write_xlsx_page_data(r,c,'0') 
                        c+=1
        self.close_xlsx()
