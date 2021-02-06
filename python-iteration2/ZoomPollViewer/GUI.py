#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v1.0
GUI CLASS
Functions 25
"""
import tkinter as tk
from tkinter import filedialog
import tkinter.ttk as ttk
from .Logger import Logger
import json
class GUI:
    def __init__(self, zpv):

        self.zpv = zpv

        self.root = tk.Tk()
        self.root.resizable(False, False)
        self.root.title("Zoom Poll Viewer")

        self.left_frame = tk.Frame(self.root, bg="red")
        self.left_frame.grid(row=0, column=0, padx=0, pady=0, rowspan=2)

        self.right_frame_top = tk.Frame(self.root, width=400, height=400)
        self.right_frame_top.grid(row=0, column=1, padx=0, pady=0)

        self.right_frame_bottom = tk.Frame(self.root, width=400, height=400, bg="blue")
        self.right_frame_bottom.grid(row=1, column=1, padx=0, pady=0)

        self.insert_tab_controller()
        self.insert_buttons()

    #          TABS

    def insert_tab_controller(self):
        self.tab_controller = ttk.Notebook(self.left_frame, width=700, height=740)

        self.tab_students = ttk.Frame(self.tab_controller)
        self.tab_sessions = ttk.Frame(self.tab_controller)
        self.tab_polls = ttk.Frame(self.tab_controller)

        self.tab_controller.add(self.tab_students, text='Students')
        self.tab_controller.add(self.tab_sessions, text='Sessions')
        self.tab_controller.add(self.tab_polls, text='Polls')

        self.tab_controller.pack(expand=1, fill="both")

        self.insert_student_list()
        self.insert_session_list()
        self.insert_poll_list()

    def insert_student_list(self):

        self.treeview_student = ttk.Treeview(self.tab_students, height=40)

        self.treeview_student["columns"] = ("1", "2", "3", "4", "5", "6", "7")
        self.treeview_student.column("#0", width=100, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("1", width=100, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("2", width=100, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("3", width=100, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("4", width=100, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("5", width=50, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("6", width=50, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("7", width=100, minwidth=20, stretch=tk.NO)

        self.treeview_student.heading("#0", text="ID", anchor=tk.W)
        self.treeview_student.heading("1", text="Name", anchor=tk.W)
        self.treeview_student.heading("2", text="Surname", anchor=tk.W)
        self.treeview_student.heading("3", text="E-Mail", anchor=tk.W)
        self.treeview_student.heading("4", text="Av. Grade", anchor=tk.W)
        self.treeview_student.heading("5", text="Attended", anchor=tk.W)
        self.treeview_student.heading("6", text="Absent", anchor=tk.W)
        self.treeview_student.heading("7", text="Percentage", anchor=tk.W)

        self.treeview_student.grid(column=0, row=0)

    def insert_student(self, data):
        self.treeview_student.insert('', 'end', text=data["id"],
                                     values=(data["first_name"], data["last_name"], data["email"], data["grade"],
                                             data["attended"], data["absent"], data["percentage"]))

    def insert_session_list(self):

        self.treeview_session = ttk.Treeview(self.tab_sessions, height=40)

        self.treeview_session["columns"] = ("1", "2", "3", "4", "5", "6")
        self.treeview_session.column("#0", width=150, minwidth=40, stretch=tk.NO)
        self.treeview_session.column("1", width=100, minwidth=30, stretch=tk.NO)
        self.treeview_session.column("2", width=100, minwidth=30, stretch=tk.NO)
        self.treeview_session.column("3", width=100, minwidth=30, stretch=tk.NO)
        self.treeview_session.column("4", width=75, minwidth=30, stretch=tk.NO)
        self.treeview_session.column("5", width=75, minwidth=30, stretch=tk.NO)
        self.treeview_session.column("6", width=100, minwidth=30, stretch=tk.NO)

        self.treeview_session.heading("#0", text="Date Time", anchor=tk.W)
        self.treeview_session.heading("1", text="Students", anchor=tk.W)
        self.treeview_session.heading("2", text="Polls", anchor=tk.W)
        self.treeview_session.heading("3", text="Av. Grade", anchor=tk.W)
        self.treeview_session.heading("4", text="Attended", anchor=tk.W)
        self.treeview_session.heading("5", text="Absent", anchor=tk.W)
        self.treeview_session.heading("6", text="Percentage", anchor=tk.W)

        self.treeview_session.grid(column=0, row=0)

    def insert_session(self, data):
        self.treeview_session.insert('', 'end', text=data["datetime"],
                                     values=(data["students"], data["polls"], data["grade"], data["attended"],
                                             data["absent"], data["percentage"]))

    def insert_poll_list(self):

        self.treeview_poll = ttk.Treeview(self.tab_polls, height=40)

        self.treeview_poll["columns"] = ("1", "2", "3", "4")
        self.treeview_poll.column("#0", width=20, minwidth=20, stretch=tk.NO)
        self.treeview_poll.column("1", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_poll.column("2", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_poll.column("3", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_poll.column("4", width=250, minwidth=50, stretch=tk.NO)

        self.treeview_poll.heading("#0", text="ID", anchor=tk.W)
        self.treeview_poll.heading("1", text="Name", anchor=tk.W)
        self.treeview_poll.heading("2", text="Questions", anchor=tk.W)
        self.treeview_poll.heading("3", text="Students", anchor=tk.W)
        self.treeview_poll.heading("4", text="Average Grade", anchor=tk.W)

        self.treeview_poll.grid(column=0, row=0)

    def insert_poll(self, data):
        self.treeview_poll.insert('', 'end', text=data["id"], values=(data["name"], data["questions"], data["students"], data["average_grade"]))

    # BUTTONS        

    def insert_buttons(self):              

        # FRAME TITLE
        self.frame_title = tk.Label(self.right_frame_top, text="Zoom Poll Viewer", fg="#3366ff", font=("Helvetica", 32, 'bold'))
        self.frame_title.grid(row=0, column=0, pady=10, columnspan=2)

        # BYS
        self.bys_label = tk.Label(self.right_frame_top, text="1. Import BYS File", fg="#444444", font=("Helvetica", 18, 'bold'))
        self.bys_label.grid(row=1, column=0, pady=6, columnspan=2)
        self.button_bys = tk.Button(self.right_frame_top, text='Import BYS File', command=self.import_bys)
        self.button_bys.grid(row=2, column=0, columnspan=2)

        # ANSWER KEY
        self.answer_key_label = tk.Label(self.right_frame_top, text="2. Import Answer Key", fg="#333333",
                                         font=("Helvetica", 18, 'bold'))
        self.answer_key_label.grid(row=3, column=0, pady=6, columnspan=2)
        self.button_answer_key = tk.Button(self.right_frame_top, text='Import Answer Key',
                                                   command=self.import_answer_key, state=tk.DISABLED)
        self.button_answer_key.grid(row=4, column=0)
        self.button_answer_keys = tk.Button(self.right_frame_top, text='Import Answer Key Directory',
                                                  command=self.import_answer_keys, state=tk.DISABLED)
        self.button_answer_keys.grid(row=4, column=1)

        # POLL REPORT
        self.poll_report_label = tk.Label(self.right_frame_top, text="3. Import Poll Report", fg="#222222",
                                          font=("Helvetica", 18, 'bold'))
        self.poll_report_label.grid(row=5, column=0, pady=6, columnspan=2)
        self.button_poll_report = tk.Button(self.right_frame_top, text='Import Zoom Report',
                                                   command=self.import_poll_report, state=tk.DISABLED)
        self.button_poll_report.grid(row=6, column=0)
        self.button_poll_reports = tk.Button(self.right_frame_top, text='Import Zoom Report Directory',
                                             command=self.import_poll_reports, state=tk.DISABLED)
        self.button_poll_reports.grid(row=6, column=1)

        # PROCESS
        self.process_label = tk.Label(self.right_frame_top, text="4. Process", fg="#222222",
                                          font=("Helvetica", 18, 'bold'))
        self.process_label.grid(row=7, column=0, pady=6, columnspan=2)
        self.button_run = tk.Button(self.right_frame_top, text='Process', command=self.run_metrics_calculator)  #, state=tk.DISABLED
        self.button_run.grid(row=8, column=0, columnspan=2)

        # EXPORT
        self.export_label = tk.Label(self.right_frame_top, text="5. Export", fg="#222222",
                                                 font=("Helvetica", 18, 'bold')).grid(row=9, column=0, pady=6, columnspan=2)
        self.button_export = tk.Button(self.right_frame_top, text='Export to Report', command=self.run_exporter, state=tk.DISABLED)
        self.button_export.grid(row=10, column=0, columnspan=2)

    def import_bys(self):
        file_path = filedialog.askopenfilename()
        print(file_path)
        self.zpv.importer.import_bys(file_path)
        print("BYS File Imported")
        #self.update_lists()
        self.bys_label.config(fg="green")
        self.button_answer_key.config(state='normal')
        self.button_answer_keys.config(state='normal')
        
    def import_answer_key(self):
        file_path = filedialog.askopenfilename()
        print(file_path)
        self.zpv.importer.import_answer_key(file_path)
        print("Answer Keys Imported")
        #self.update_poll_list()
        self.answer_key_label.config(fg="green")
        self.button_poll_report.config(state='normal')
        self.button_poll_reports.config(state='normal')

    def import_answer_keys(self):
        file_path = filedialog.askdirectory()
        print(file_path)
        self.zpv.importer.import_answer_key(file_path)
        print("Answer Keys Imported")
        #self.update_poll_list()
        self.answer_key_label.config(fg="green")
        self.button_poll_report.config(state='normal')
        self.button_poll_reports.config(state='normal')

    def import_poll_report(self):
        file_path = filedialog.askopenfilename()
        print(file_path)
        self.zpv.importer.import_poll_report(file_path)
        print("Poll Reports Imported")
        self.poll_report_label.config(fg="green")
        self.button_run.config(state='normal')

    def import_poll_reports(self):
        file_path = filedialog.askdirectory()
        print(file_path)
        self.zpv.importer.import_poll_report(file_path)
        print("Poll Report Imported")
        self.poll_report_label.config(fg="green")
        self.button_run.config(state='normal')


    def run_metrics_calculator(self):

        #self.zpv.importer.import_bys("/Users/eminsafatok/Documents/Marmara/CSE3063/CSE3063F20P1_GRP5/python-iteration2/Documents/CES3063_Fall2020_rptSinifListesi.XLS")
        #self.zpv.importer.import_answer_key("/Users/eminsafatok/Documents/Marmara/CSE3063/CSE3063F20P1_GRP5/python-iteration2/Documents/keys_test")
        #self.zpv.importer.import_poll_report("/Users/eminsafatok/Documents/Marmara/CSE3063/CSE3063F20P1_GRP5/python-iteration2/Documents/test")
        #for poll in self.zpv._polls:
        #    for question in poll._questions:
        #        print(question.get_text())
        if self.zpv.check_unmatched_student_exist():
            try:
                with open('match.json', 'r') as json_file:
                    data = json.load(json_file)
                    for s in data['student']:
                        self.zpv.match_students(s['bys_id'],s['email'])
            except:
                with open('match.json', 'w') as json_file:
                    data = {}
                    data["student"] = []
                    json.dump(data, json_file)

            self.student_matcher()
            self.insert_all_unmatched_student()
        else:
            self.run_metrics_calculator_after_match()

    def run_metrics_calculator_after_match(self):
        self.zpv.metrics_calculator()
        self.update_lists()
        self.button_run.config(fg="green")
        self.button_export.config(state='normal')
        print(self.zpv._sessions)

    def update_lists(self):
        self.update_student_list()
        self.update_poll_list()
        self.update_session_list()

    def update_student_list(self):
        for student in self.zpv._students:
            self.insert_student({
                'id': student.get_student_id(),
                'first_name': student.get_first_name(),
                'last_name': student.get_last_name(),
                'email': student.get_email(),
                'grade': student.get_average_grade(),
                'attended': student.get_attendance(),
                'absent': student.get_absent(),
                'percentage': student.get_attendance_percentage()
            })

    def update_poll_list(self):
        counter = 1
        for poll in self.zpv._polls:
            self.insert_poll({'id':str(counter),
                 'name': poll.get_name(),
                 'questions': poll.get_number_of_questions(),
                 'students': poll.get_number_of_students(),
                 'average_grade': poll.calculate_session_average_grade()})
            counter += 1

    def update_session_list(self):
        for session in self.zpv.get_sessions():
            self.insert_session({
                'datetime': session.get_date_formatted(),
                'students': session.get_attendance(),
                'polls': session.get_number_of_polls(),
                'grade': session.calculate_average_grade(),
                'attended': session.get_attendance(),
                'absent': session.get_absent(),
                'percentage': session.get_attendance_percentage()
            })

    def student_matcher(self):
        self.sm = tk.Tk()
        self.sm.geometry("1000x700")
        self.sm.resizable(False, False)
        self.sm.title("Student Matching")

        self.sm_bys_frame = tk.Frame(self.sm, width=300, height=500)
        self.sm_bys_frame.grid(column=0, row=0)
        self.sm_middle_frame = tk.Frame(self.sm, width=100, height=500)
        self.sm_middle_frame.grid(column=1, row=0, rowspan=2)
        self.sm_unmatched_frame = tk.Frame(self.sm, width=400, height=500)
        self.sm_unmatched_frame.grid(column=2, row=0)

        # MATCH BYS
        self.sm_bys_label = tk.Label(self.sm_bys_frame, text="BYS Student List", fg="#222222", font=("Helvetica", 18, 'bold'))
        self.sm_bys_label.grid(row=0, column=0, pady=6)

        self.sm_bys_list = ttk.Treeview(self.sm_bys_frame, selectmode="browse", height=25)
        self.sm_bys_list['columns'] = ("Name")

        self.sm_bys_list.column("#0", width=150, minwidth=25)
        self.sm_bys_list.column("Name", width=250, minwidth=25)

        self.sm_bys_list.heading("#0", text="ID", anchor="w")
        self.sm_bys_list.heading("Name", text="Name", anchor="w")

        self.sm_bys_list.grid(column=0, row=1, sticky="nesw")

        # MATCH UNMATCHED
        self.sm_unmatched_label = tk.Label(self.sm_unmatched_frame, text="Unmatched Student List", fg="#222222", font=("Helvetica", 18, 'bold'))
        self.sm_unmatched_label.grid(row=0, column=0, pady=6)

        self.sm_unmatched_list = ttk.Treeview(self.sm_unmatched_frame, selectmode="browse", height=25)
        self.sm_unmatched_list['columns'] = ("Name")

        self.sm_unmatched_list.column("#0", width=250, minwidth=25)
        self.sm_unmatched_list.column("Name", width=250, minwidth=25)

        self.sm_unmatched_list.heading("#0", text="Email", anchor="w")
        self.sm_unmatched_list.heading("Name", text="Name", anchor="w")

        self.sm_unmatched_list.grid(column=0, row=1, sticky="nesw")

        # MIDDLE BUTTONS
        self.sm_middle_button = tk.Button(self.sm_middle_frame, text="Match", command=self.match_selected_students)
        self.sm_middle_button.grid(row=0, column=0, pady=200, sticky="ew")
        self.sm_middle_exit_button = tk.Button(self.sm_middle_frame, text="Completed", command=self.match_completed)
        self.sm_middle_exit_button.grid(row=1, column=0, pady=200, sticky="ew")

    def match_completed(self):
        try:
            with open('not_matched.json', 'w') as json_file:
                data = {}
                data["Students in BYS list but don't exist in this poll report (Absence)"] = []
                data["Students in this poll report but don't exist in BYS Student List (Anomalies)"] = []
                json.dump(data, json_file, ensure_ascii=False)
        except:
            print("Exist")

        students = self.zpv.get_unmatched_bys_students()
        for student in students:
            with open('not_matched.json', 'r') as json_file:
                 data = json.load(json_file)
                 data["Students in BYS list but don't exist in this poll report (Absence)"].append({
                     "student no" : str(student.get_student_id()),
                     "student name" : str(student.get_name())
                 })
            with open('not_matched.json', 'w') as json_file:
                json.dump(data, json_file, ensure_ascii=False)
        temp_students = self.zpv.get_unmatched_temporary_students()
        for student in temp_students:
            with open('not_matched.json', 'r') as json_file:
                 data = json.load(json_file)
                 data["Students in this poll report but don't exist in BYS Student List (Anomalies)"].append({
                     "student email" : str(student.get_email()),
                     "student name" : str(student.get_name())
                 })
            with open('not_matched.json', 'w') as json_file:
                json.dump(data, json_file, ensure_ascii=False)

        self.sm.destroy()
        self.run_metrics_calculator_after_match()

    def insert_all_unmatched_student(self):
        students = self.zpv.get_unmatched_bys_students()
        for student in students:
            self.sm_bys_list.insert('', 'end', text=student.get_student_id(), values=(student.get_name(),))
        temp_students = self.zpv.get_unmatched_temporary_students()
        for student in temp_students:
            self.sm_unmatched_list.insert('', 'end', text=student.get_email(), values=(student.get_name(),))

    def match_selected_students(self):
        bys_student_item = self.sm_bys_list.selection()[0]
        unmatched_student_item = self.sm_unmatched_list.selection()[0]

        self.zpv.match_students(self.sm_bys_list.item(bys_student_item, 'text'), self.sm_unmatched_list.item(unmatched_student_item, 'text'))
        with open('match.json', 'r') as json_file:
            data = json.load(json_file)
            data["student"].append({
                "email" : str(self.sm_unmatched_list.item(unmatched_student_item, 'text')),
                "bys_id": str(self.sm_bys_list.item(bys_student_item, 'text'))
            })
        with open('match.json', 'w') as json_file:
            json.dump(data, json_file)

        self.sm_bys_list.delete(bys_student_item)
        self.sm_unmatched_list.delete(unmatched_student_item)

    def run_exporter(self):
        self.zpv.exporter.export_quiz_report()
        self.zpv.exporter.export_student_reports() # HAZIR
        self.zpv.exporter.export_global_analytics()
        #self.zpv.exporter.export_global()
        #self.zpv.exporter.export_poll()

    def export_quiz_reports(self):
        # Quiz = Poll
        pass

    def export_student_reports(self):
        pass

    def export_global_report(self):
        pass
