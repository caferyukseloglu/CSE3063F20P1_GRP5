import csv
import xlrd
import os,sys
from .ZoomPollViewer import ZoomPollViewer as ZPV


class CsvReader:
    def __init__(self,file):
        self.file=file
    def bys_reader(self):
        try:
            read_xls=xlrd.open_workbook(self.file)
            xl_sheet = read_xls.sheet_by_index(0) #In order to read specific sheet such as rptSinifListesi we'll use this method.
            print(f'Sheet name: {xl_sheet.name}')
            col_of_name = xl_sheet.col_values(4)
            col_of_surname=xl_sheet.col_values(7)
            for i in range(min(len(col_of_name),len(col_of_surname))):
                if col_of_name[i]=="" or col_of_name[i]=="Adı" or col_of_surname[i]=="" or col_of_surname[i]=="Soyadı":
                    pass
                else:
                    #ZPV.ZoomPollViewer.add_student(col_of_name[i], col_of_surname[i], 1111111)
                    print(col_of_name[i], col_of_surname[i])
        except Exception as err:
            print("Error Occured",err)
    def poll_report_reader(self):
        if os.path.basename(self.file).upper().endswith(".csv"):
            print("Poll Name: {}".format(os.path.basename(self.file)))
            with open(self.file, newline='',encoding="utf8") as csvfile:
                csv_array = csv.reader(csvfile)
                i = 0
                for row in csv_array:
                    if i == 0:
                        pass
                    else:
                        name = row[1]
                        email = row[2]
                            # add_student( fullname, email )
                        for j in range(4, len(row) - 1, 2):
                            question = row[j]
                            answer = row[j + 1]
                                # add_question ( question, email )
                            print(question,answer)
                    i = i + 1
        elif os.path.basename(self.file).upper().endswith(".xls"):
            with open(self.file, newline='',encoding="utf8") as csvfile:
                read_xls=xlrd.open_workbook(self.file)
                i = 0
                for row in read_xls:
                    if i == 0:
                        pass
                    else:
                        name = row[1]
                        email = row[2]
                            # add_student( fullname, email )
                        for j in range(4, len(row) - 1, 2):
                            question = row[j]
                            answer = row[j + 1]
                                # add_question ( question, email )
                            print(question,answer)
                    i = i + 1
data=CsvReader("CSE3063_20201124_Tue_zoom_PollReport.csv")
data.poll_report_reader()
