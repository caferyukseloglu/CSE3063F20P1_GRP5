import tkinter as tk
from tkinter import ttk
from tkinter import *

root = tk.Tk()

# def clickA():

# def clickB():

frame = LabelFrame(root, text='B is here', padx=20, pady=20).pack(padx=10, pady=10)

importBysFile = Button(frame, text='Import BYS File').grid(row=0, column=0)
importZoomReport = Button(frame, text='Import Zoom Report').grid(row=1, column=0)
importAnswerKey = Button(frame, text='Import Answer Key').grid(row=2, column=0)
process = Button(frame, text='Process').grid(row=3, column=0)
exportReport = Button(frame, text='Export to Report').grid(row=4, column=0)


tabControl = ttk.Notebook(root)

tab1 = ttk.Frame(tabControl)
tab2 = ttk.Frame(tabControl)
tab3 = ttk.Frame(tabControl)

tabControl.add(tab1, text='Students')
tabControl.add(tab2, text='Sessions')
tabControl.add(tab3, text='Polls')
tabControl.pack(expand=1, fill="both")

ttk.Label(tab1,text="for Students").grid(column=0, row=0, padx=30, pady=30)
ttk.Label(tab2, text="for Sessions").grid(column=0, row=0, padx=30, pady=30)
ttk.Label(tab3, text="for Polls").grid(column=0, row=0, padx=30, pady=30)


root.mainloop()