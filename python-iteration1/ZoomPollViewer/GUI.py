#!/usr/bin/env python3
"""

ZOOM POLL VIEWER v0.1

"""
import tkinter as tk
import tkinter.ttk as ttk

class GUI:

    def __init__(self, ZPV):

        self.ZPV = ZPV

        self.root = tk.Tk()
        self.root.resizable(False, False)
        self.root.title("Zoom Poll Viewer")

        #p1 = tk.PhotoImage(file='ZoomPollViewer/poll.png')
        #self.root.iconphoto(False, p1)

        self.left_frame = tk.Frame(self.root, bg="red")
        self.left_frame.grid(row=0, column=0, padx=0, pady=0, rowspan=2)

        self.right_frame_top = tk.Frame(self.root, width=400, height=400)
        self.right_frame_top.grid(row=0, column=1, padx=0, pady=0)

        self.right_frame_bottom = tk.Frame(self.root, width=400, height=400, bg="blue")
        self.right_frame_bottom.grid(row=1, column=1, padx=0, pady=0)

        self.insert_tab_controller()
        self.insert_buttons()



    ##########          TABS          ##########

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

        self.treeview_student = ttk.Treeview(self.tab_students)

        self.treeview_student["columns"] = ("1", "2", "3")
        self.treeview_student.column("#0", width=20, minwidth=20, stretch=tk.NO)
        self.treeview_student.column("1", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_student.column("2", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_student.column("3", width=450, minwidth=50, stretch=tk.NO)

        self.treeview_student.heading("#0", text="ID", anchor=tk.W)
        self.treeview_student.heading("1", text="Name", anchor=tk.W)
        self.treeview_student.heading("2", text="Surname", anchor=tk.W)
        self.treeview_student.heading("3", text="E-Mail", anchor=tk.W)

        self.treeview_student.grid(column=0, row=0)

    def insert_student(self, data):
        self.treeview_student.insert('', 'end', text=data["id"], values=(data["firstname"], data["lastname"], data["email"]))

    def insert_session_list(self):

        self.treeview_session = ttk.Treeview(self.tab_sessions)

        self.treeview_session["columns"] = ("1", "2", "3")
        self.treeview_session.column("#0", width=20, minwidth=20, stretch=tk.NO)
        self.treeview_session.column("1", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_session.column("2", width=150, minwidth=50, stretch=tk.NO)
        self.treeview_session.column("3", width=450, minwidth=50, stretch=tk.NO)

        self.treeview_session.heading("#0", text="ID", anchor=tk.W)
        self.treeview_session.heading("1", text="Date Time", anchor=tk.W)
        self.treeview_session.heading("2", text="Students", anchor=tk.W)
        self.treeview_session.heading("3", text="Attendance %", anchor=tk.W)

        self.treeview_session.grid(column=0, row=0)

    def insert_session(self, data):
        self.treeview_session.insert('', 'end', text=data["id"], values=(data["datetime"], data["students"], data["percentage"]))

    def insert_poll_list(self):

        self.treeview_poll = ttk.Treeview(self.tab_polls)

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

    ##########         BUTTONS         ##########

    def insert_buttons(self):

        self.frame_title = tk.Label(self.right_frame_top, text="Zoom Poll Viewer", fg="#3366ff", font=("Helvetica", 32)).grid(row=0, column=0, pady=10)
        self.button_import_bys = tk.Button(self.right_frame_top, text='Import BYS File', command=self.import_bys).grid(row=2, column=0)
        self.button_import_poll_report = tk.Button(self.right_frame_top, text='Import Zoom Report').grid(row=3, column=0)
        self.button_import_answer_key = tk.Button(self.right_frame_top, text='Import Answer Key').grid(row=4, column=0)
        self.button_run = tk.Button(self.right_frame_top, text='Process').grid(row=5, column=0)
        self.button_export = tk.Button(self.right_frame_top, text='Export to Report').grid(row=6, column=0)

    def import_bys(self):
        self.ZPV.importer.import_bys()
        print("Added")
