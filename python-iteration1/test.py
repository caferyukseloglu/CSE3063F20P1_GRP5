import tkinter as tk
from tkinter import ttk

root = tk.Tk()

# def clickA():

# def clickB():

frame = tk.LabelFrame(root, text='B is here', padx=20, pady=20).pack(padx=10, pady=10)

importBysFile = tk.Button(frame, text='Import BYS File')
importZoomReport = tk.Button(frame, text='Import Zoom Report')
importAnswerKey = tk.Button(frame, text='Import Answer Key')
process = tk.Button(frame, text='Process')
exportReport = tk.Button(frame, text='Export to Report')


tabControl = ttk.Notebook(root)

tab1 = ttk.Frame(tabControl)
tab2 = ttk.Frame(tabControl)
tab3 = ttk.Frame(tabControl)

tabControl.add(tab1, text='Students')
tabControl.add(tab2, text='Sessions')
tabControl.add(tab3, text='Polls')
tabControl.pack(expand=1, fill="both")

ttk.Label(tab1,text="for Students")
ttk.Label(tab2, text="for Sessions")
ttk.Label(tab3, text="for Polls")


root.mainloop()