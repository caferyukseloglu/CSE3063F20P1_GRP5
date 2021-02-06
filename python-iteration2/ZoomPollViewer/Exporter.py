#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1
15 Function
2 Object
256 Lines
"""
import xlsxwriter
from .Logger import Logger
import datetime as date
import os
import re

class Exporter():
    def __init__(self,zpv):
        self.zpv = zpv
        self.path = str(os.getcwd()) + '/python-iteration2/Outputs/'
        if not os.path.isdir(self.path): 
            os.mkdir(self.path)
            os.mkdir(self.path+'Student Reports/')
        elif not os.path.isdir(self.path + 'Student Reports/'):
            os.mkdir(self.path+'Student Reports/')

    # To Create xlsx File
    def create_xlsx(self,name:str):
        self.xlsx_file = xlsxwriter.Workbook(self.path+name, {'constant_memory': True})

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
        now = date.date.today()
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

    # To Export Poll Report
    def export_poll(self):
        for poll in self.zpv._polls:
            Logger(Exporter.export_poll.__name__ + ":"+str(poll.get_name()).strip(),str(poll.get_name()).strip()+" exported")   
            r = 0
            c = 3
            self.create_xlsx(str(poll.get_name()).strip()+'.xlsx')
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
                    self.write_xlsx_page_correct_data(r, c, selected_by)
                    altdata.insert(c-1, selected_by)
                else:
                    self.write_xlsx_page_data(r, c, selected_by)
                    altdata.insert(c-1, selected_by)
                c+=1
            data.insert(r, altdata)
            r += 1
        for i in range(maximum_choice):
            if i == 0:
                color = 'green'
            else:
                color = 'gray'
            self.chart.add_series({           
                'name':['Poll Chart', 0, i+1],
                'values':['Poll Chart', 1, i+1, r-1, i+1],
                'fill': {'color': color},          
            })
        self.xlsxpage.insert_chart(r+2,0, self.chart)

    def export_quiz_report(self):
        for session in self.zpv.get_sessions():
            for poll in session.get_polls("QUIZ"):
                Logger(Exporter.export_poll.__name__ + ":"+str(poll.get_name()).strip(),str(poll.get_name()).strip()+" exported")
                self.create_xlsx(str(self.format_name(poll, session) + '.xlsx'))
                self.create_xlsx_page('Quiz Report')

                self.write_xlsx_page_data_title(0, 0, 'Quiz Report')
                self.write_xlsx_page_data_title(1, 0, 'Report Generated:')
                now = date.datetime.now()
                date_time = now.strftime("%Y-%m-%d %H:%M:%S")
                self.write_xlsx_page_data_title(1, 1, str(date_time)) #TODO: DateTime şuan eklenecek

                self.write_xlsx_page_data_title(2, 0, 'Poll Name')
                self.write_xlsx_page_data_title(2, 1, poll.get_name())

                self.write_xlsx_page_data_title(3, 0, '#')
                self.write_xlsx_page_data_title(3, 1, 'Student ID')
                self.write_xlsx_page_data_title(3, 2, 'First Name')
                self.write_xlsx_page_data_title(3, 3, 'Last Name')

                self.write_xlsx_page_data_title(3, 4, 'Number of Questions')
                self.write_xlsx_page_data_title(3, 5, 'Number of Correctly Answered Questions')
                self.write_xlsx_page_data_title(3, 6, 'Number of Wrongly Answered Questions')
                self.write_xlsx_page_data_title(3, 7, 'Number of Empty Questions')
                self.write_xlsx_page_data_title(3, 8, 'Rate of Correctly Answered Questions')  # 0.4
                self.write_xlsx_page_data_title(3, 9, 'Accuracy Percentage')

                counter = 1
                number_of_questions = poll.get_number_of_questions()

                for student in self.zpv.get_students():
                    response = student.get_response_by_session_and_poll(session, poll)
                    if response:
                        correct_answer_count = response.get_correct_answer_count()
                        wrong_answer_count = response.get_wrong_answer_count()
                        empty_answer_count = response.get_empty_answer_count()
                        correct_rate = correct_answer_count/number_of_questions
                        accuracy_percentage = round(correct_rate * 100, 2)
                    else:
                        correct_answer_count = ''
                        wrong_answer_count = ''
                        empty_answer_count = ''
                        correct_rate = ''
                        accuracy_percentage = ''

                    self.write_xlsx_page_data(3 + counter, 0, str(counter))
                    self.write_xlsx_page_data(3 + counter, 1, str(student.get_student_id()))
                    self.write_xlsx_page_data(3 + counter, 2, str(student.get_first_name()))
                    self.write_xlsx_page_data(3 + counter, 3, str(student.get_last_name()))

                    self.write_xlsx_page_data(3 + counter, 4, str(number_of_questions))
                    self.write_xlsx_page_data(3 + counter, 5, str(correct_answer_count))
                    self.write_xlsx_page_data(3 + counter, 6, str(wrong_answer_count))
                    self.write_xlsx_page_data(3 + counter, 7, str(empty_answer_count))
                    self.write_xlsx_page_data(3 + counter, 8, str(correct_rate))
                    self.write_xlsx_page_data(3 + counter, 9, str(accuracy_percentage))
                    counter += 1
                self.create_histogram_chart(poll)
                self.close_xlsx()

    def export_student_reports(self):
        for session in self.zpv.get_sessions():
            for poll in session.get_polls("QUIZ"):
                for student in self.zpv.get_students():
                    response = student.get_response_by_session_and_poll(session, poll)
                    if response:

                        self.create_xlsx(str('Student Reports/' + self.format_name(poll, session, student) + '.xlsx'))
                        self.create_xlsx_page('Quiz Report')
                        now = date.datetime.now()
                        date_time = now.strftime("%Y-%m-%d %H:%M:%S")

                        self.write_xlsx_page_data_title(0, 0, 'Student Report')
                        self.write_xlsx_page_data_title(1, 0, 'Report Generated:')
                        self.write_xlsx_page_data_title(1, 1, str(date_time))

                        self.write_xlsx_page_data_title(2, 0, 'Student ID')
                        self.write_xlsx_page_data_title(3, 0, student.get_student_id())
                        self.write_xlsx_page_data_title(2, 1, 'First Name')
                        self.write_xlsx_page_data_title(3, 1, student.get_first_name())
                        self.write_xlsx_page_data_title(2, 2, 'Last Name')
                        self.write_xlsx_page_data_title(3, 2, student.get_last_name())

                        self.write_xlsx_page_data_title(2, 3, 'Poll Name')
                        self.write_xlsx_page_data_title(3, 3, poll.get_name())
                        self.write_xlsx_page_data_title(2, 4, 'Session Date/Time')
                        self.write_xlsx_page_data_title(3, 4, session.get_date_text())

                        self.write_xlsx_page_data_title(4, 0, '#')
                        self.write_xlsx_page_data_title(4, 1, 'Question')
                        self.write_xlsx_page_data_title(4, 2, 'Given Answer')
                        self.write_xlsx_page_data_title(4, 3, 'Correct Answer')
                        self.write_xlsx_page_data_title(4, 4, 'Correct')

                        counter = 1
                        for question in poll.get_questions():
                            self.write_xlsx_page_data(4 + counter, 0, str(counter))
                            self.write_xlsx_page_data(4 + counter, 1, str(question.get_text()))
                            given_answers = response.get_given_answer(question)
                            if given_answers:
                                given_answers_text = "; ".join([choice.get_text() for choice in given_answers])
                            self.write_xlsx_page_data(4 + counter, 2, given_answers_text)
                            correct_choices = question.get_correct_choices()
                            if correct_choices:
                                correct_choices_text = "; ".join([choice.get_text() for choice in correct_choices])
                            self.write_xlsx_page_data(4 + counter, 3, correct_choices_text)
                            if str(response.get_correctness_of_answer(question)) == "True":
                                response_correctness = 1
                            else:
                                response_correctness = 0 

                            self.write_xlsx_page_data(4 + counter, 4, str(response_correctness))
                            counter += 1
                        self.close_xlsx()


    def export_global_analytics(self):
        self.create_xlsx(str('Global_Analytics.xlsx'))
        self.create_xlsx_page('Analytics')
        now = date.datetime.now()
        date_time = now.strftime("%Y-%m-%d %H:%M:%S")
        self.write_xlsx_page_data_title(0, 0, 'Global Analytics Report')
        self.write_xlsx_page_data_title(1, 0, 'Report Generated:')
        self.write_xlsx_page_data_title(1, 1, str(date_time))

        self.write_xlsx_page_data_title(2, 0, '#')
        self.write_xlsx_page_data_title(2, 1, 'Student ID')
        self.write_xlsx_page_data_title(2, 2, 'Student Name')

        column_counter = 3
        total_number_of_questions = 0
        for session in self.zpv.get_sessions():
            for poll in session.get_polls("QUIZ"):
                self.write_xlsx_page_data_title(2, column_counter, self.format_name(poll, session))
                total_number_of_questions += poll.get_number_of_questions()
                column_counter += 1
        self.write_xlsx_page_data_title(2, column_counter, "Total")

        row_counter = 3
        for student in self.zpv.get_students():
            self.write_xlsx_page_data(row_counter, 0, str(row_counter-2))
            self.write_xlsx_page_data(row_counter, 1, str(student.get_student_id()))
            self.write_xlsx_page_data(row_counter, 2, str(student.get_name()))
            column_counter = 3
            student_total_correct_answer_count = 0
            for session in self.zpv.get_sessions():
                for poll in session.get_polls("QUIZ"):
                    response = student.get_response_by_session_and_poll(session, poll)
                    if response:
                        self.write_xlsx_page_data_title(row_counter, column_counter, response.get_correct_answer_count())
                        student_total_correct_answer_count += response.get_correct_answer_count()
                    column_counter += 1
            self.write_xlsx_page_data_title(row_counter, column_counter, student_total_correct_answer_count/total_number_of_questions)
            row_counter += 1
        self.close_xlsx()

    def format_name(self, poll, session, student = ""):
        result = ""
        result += "_".join(poll.get_name().split())
        result += "_" + session.get_date_time().strftime("%Y_%m_%d_%H_%M_%S")
        if student != "":
            result += "_" + "_".join(student.get_name().split())
            result += "_" + student.get_student_id()
        result = re.sub('[!@#$.:,]','', result)
        return result