import csv
import xlrd
import os,sys
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
    def answer_key_reader(self):
        if os.path.basename(self.file).endswith(".xls"):
                read_xls = xlrd.open_workbook(self.file)
                xl_sheet = read_xls.sheet_by_index(0)  # In order to read specific sheet such as rptSinifListesi we'll use this method.
                print(f'Sheet name: {xl_sheet.name}')
                col_of_question = xl_sheet.col_values(0)
                col_of_answer = xl_sheet.col_values(1)
                i=0
                for row in xl_sheet:
                    if i==0:
                        pass
                        for i in range(min(len(col_of_question), len(col_of_answer))):
                                print(col_of_question[i], col_of_answer[i])
        elif os.path.basename(self.file).endswith(".csv"):
            with open(self.file, newline='',encoding="utf8") as csvfile:
                csv_array = csv.reader(csvfile)
                i = 0
                for row in csv_array:
                    if i == 0:
                        pass
                    else:
                        question=row[0]
                        print(question)
                    i+=1

data=CsvReader("Answers_Attandance_Poll.csv")
data.answer_key_reader()