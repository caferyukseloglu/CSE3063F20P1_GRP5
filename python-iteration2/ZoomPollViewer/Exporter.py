#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import xlsxwriter
from .Logger import Logger
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

    # Writing title Data
    def write_xlsx_page_data_title(self,col:int,row:int,data:str):
        self.xls_cell_format = self.xlsx_file.add_format({'bold':True})
        self.xlsxpage.write(col,row,data,self.xls_cell_format)

    # Create data Chart
    def write_xlsx_page_data_chart(self,type,value='NONE'):
        if value != 'NONE':
            self.chart = self.xlsx_file.add_chart({'type': type, 'subtype': value})
        else:
            self.chart = self.xlsx_file.add_chart({'type': type})
        self.chart.set_title({'name': 'POLL QUESTION ANALYZE'})
        self.chart.set_x_axis({'name': 'Answears Per Question'})
        self.chart.set_y_axis({'name': 'Number Of Students'})

    # Add data with green color
    def write_xlsx_page_correct_data(self,col:int,row:int,data:str):
        cell_format = self.xlsx_file.add_format({'font_color': 'green'})
        self.xlsxpage.write(col,row,data,cell_format)
        
    def close_xlsx(self):
        self.xlsx_file.close()  

        #To Export Global Report      
    def export_global(self):
        Logger(Exporter.export_global.__name__,"Global report exported")
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
            Logger(Exporter.export_poll.__name__ + ":"+str(poll.get_name()).strip(),str(poll.get_name()).strip()+" exported")   
            r = 0
            c = 3
            self.create_xlsx(str('/'+poll.get_name()).strip()+'.xlsx')
            self.create_xlsx_page('POLL STUDENT')
            self.write_xlsx_page_data_title(0,0,'STUDENT ID')        
            self.write_xlsx_page_data_title(0,1,'E-MAIL')
            self.write_xlsx_page_data_title(0,2,'NAME')
            self.write_xlsx_page_data_title(0,3,'SURNAME')
            for question in poll.get_questions():
                c += 1
                self.write_xlsx_page_data_title(0,c,question.get_text())
            self.write_xlsx_page_data_title(0,c+1,'NUMBER OF QUESTIONS')
            self.write_xlsx_page_data_title(0,c+2,'SUCCESS RATE')
            self.write_xlsx_page_data_title(0,c+3,'SUCCESS PERCENTAGE')
            for student in self.zpv._students:
                c = 0
                response = student.get_response_by_poll(poll)
                if response:
                    r += 1                   
                    self.write_xlsx_page_data(r,c,str(student.get_student_id())) 
                    c += 1
                    self.write_xlsx_page_data(r,c,str(student.get_email())) 
                    c += 1
                    self.write_xlsx_page_data(r,c,str(student.get_name())) 
                    c += 1
                    self.write_xlsx_page_data(r,c,student.get_last_name()) 
                    c += 1
                    success = 0
                    for question in poll.get_questions():
                        correct_choices = question.get_correct_choices()
                        if question in response._answers.keys():
                            if len(correct_choices) == len(response._answers[question]) and all(i in correct_choices for i in response._answers[question]):
                                self.write_xlsx_page_data(r,c,'1') 
                                success += 1                    
                            else:
                                self.write_xlsx_page_data(r,c,'0') 
                            c+=1
                        else:
                            self.write_xlsx_page_data(r,c,'-')
                            c+=1

                    self.write_xlsx_page_data(r,c,str(poll.get_number_of_questions()))
                    self.write_xlsx_page_data(r,c+1,str(success)+' out of '+str(poll.get_number_of_questions()))
                    grade = response.get_grade()
                    self.write_xlsx_page_data(r,c+2,str(grade))
            self.create_histogram_chart(poll)
            self.close_xlsx()
    
    def create_histogram_chart(self,poll):
        self.create_xlsx_page('Poll Chart')    
        self.write_xlsx_page_data_chart('column')    
        data=[]
        altdata=[]
        maximum_choice = poll.get_number_of_max_choices()
        for i in range(maximum_choice):
             self.write_xlsx_page_data_title(0,i+1,'Choice '+str(i+1))
        r = 1
        for question in poll.get_questions():
            self.write_xlsx_page_data_title(r,0,str(question.get_text()))
            c = 1            
            for choice in question.get_choices():  
                selected_by = 0      
                for student in self.zpv._students:                    
                    response = student.get_response_by_poll(poll)
                    if response:
                        if question in response._answers.keys():
                            if choice in response._answers[question]:
                                selected_by += 1
                if choice.get_correctness() == 1:
                    self.write_xlsx_page_correct_data(r,c,selected_by)
                    altdata.insert(c-1,selected_by)
                else:
                    self.write_xlsx_page_data(r,c,selected_by)
                    altdata.insert(c-1,selected_by)
                c+=1
            data.insert(r,altdata)                    
            r += 1
        for i in range(maximum_choice):
            if i == 0:
                color = 'green'
            else:
                color = 'gray'
            self.chart.add_series({           
                'name':['Poll Chart',0,i+1],
                'values':['Poll Chart', 1,i+1,r-1,i+1], 
                'fill': {'color': color},          
            })
        self.xlsxpage.insert_chart(r+2,0, self.chart)
