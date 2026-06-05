#EVANGELOS BASDAVANOS
#4962

import sys
import csv


def composite_query(fileR, fileS):
	data = []
	with open(fileS, 'r', newline='') as fileS:
		with open(fileR, 'r', newline='') as fileR:
			csv_readerS = csv.reader(fileS)
			csv_readerR = csv.reader(fileR)
			rowS = next(csv_readerS)
			for rowR in csv_readerR:
				sum = 0
				temp = ["",""]
				while rowR[0] == rowS[1] and rowR[2] == str(7):
					sum += int(rowS[2])
					temp[0] = rowR[0]
					temp[1] = str(sum)
					try: 
						rowS = next(csv_readerS)
					except StopIteration:
						break
				if temp[0] != "" : 
					data.append(temp)
				while rowR[0] == rowS[1]:
					try: 
						rowS = next(csv_readerS)
					except StopIteration:
						break
	return data

def save_to_csv(data, csv_file):
    with open(csv_file, 'w', newline='') as file:
        csv_writer = csv.writer(file)
        for row in data:
        	csv_writer.writerow(row)



def main():
	save_to_csv(composite_query("R.csv","S.csv"),"O3.csv")
main()
