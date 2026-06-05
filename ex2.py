#EVANGELOS BASDAVANOS
#4962

import sys
import csv 

def merge_join(fileR, fileS):
	joined =[]
	with open(fileS, 'r', newline='') as fileS:
			with open(fileR, 'r', newline='') as fileR:
				csv_readerS = csv.reader(fileS)
				csv_readerR = csv.reader(fileR)
				rowS = next(csv_readerS)
				for rowR in csv_readerR:
					while rowR[0] == rowS[1] :
						temp = ["","","","",""]
						temp[0] = rowR[0]
						temp[1] = rowR[1]
						temp[2] = rowR[2]
						temp[3] = rowS[0]
						temp[4] = rowS[2]
						joined.append(temp)
						try: 
							rowS = next(csv_readerS)
						except StopIteration:
							break
	return joined

def save_to_csv(data, csv_file):
    with open(csv_file, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in data:
            writer.writerow(row)

def main():
	save_to_csv(merge_join("R.csv","S.csv"),"O2.csv")

main()