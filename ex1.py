#EVANGELOS BASDAVANOS
#4962

import csv
import sys

def group_by_with_aggregation(data_list, grouping_attribute, aggregation_attribute, aggregation_function):
    if len(data_list) <= 1:
        return data_list

    middle= len(data_list) // 2
    left_half =  data_list[:middle]
    print("left half =",left_half)
    right_half = data_list[middle:]
    print("right half =",right_half)
    left = group_by_with_aggregation(left_half,grouping_attribute,aggregation_attribute,aggregation_function)
    print("left =",left)
    right = group_by_with_aggregation(right_half,grouping_attribute,aggregation_attribute,aggregation_function)
    print("right =",right)
   
    merged = []
    i = 0
    j = 0

    while i < len(left) and j < len(right):
        x = int(left[i][0])
        y = int(right[j][0])
        if x < y:
            merged.append(left[i] )
            i += 1
        elif x > y:
            merged.append(right[j])
            j += 1
        elif x == y:
            if aggregation_function == 'sum':
                result = str(int(left[i][1]) + int(right[j][1]))
            elif aggregation_function == 'min':
                result = str(min(int(left[i][1]),int(right[j][1])))
            elif aggregation_function == 'max':
                result = str(max(int(left[i][1]), int(right[j][1])))

            merged.append([left[i][0], result])
            i += 1
            j += 1

    while i < len(left):
        merged.append(left[i])
        print("left left=",left[i])
        i += 1

    while j < len(right):
        merged.append(right[j])
        print("left right=",right[j])
        j += 1

    print("merged =",merged)
    return merged



def updated_list(data, grouping_attribute, aggregation_attribute,aggregation_function):
	new_data = []
	for row in data:
		new_row = []
		counter = 0
		for element in row:
			if counter == int(grouping_attribute) or counter == int(aggregation_attribute):
				new_row.append(element)
			counter += 1
		new_data.append(new_row)

	return new_data

def read_csv_file(file_path):
    data = []
    with open(file_path, 'r', newline='') as file:
        csv_reader = csv.reader(file)
        for row in csv_reader:
            data.append(row)

    return data

def save_to_csv(data, csv_file):
	with open(csv_file, 'w', newline='') as file:
		writer = csv.writer(file)
		for row in data:
			writer.writerow(row)

def main():
	if len(sys.argv) < 5:
		print("Input should be: file_name  grouping_attribute  aggregation_attribute  and  max/min/sum")
		sys.exit(1)

	file_name = sys.argv[1]
	grouping_attribute = sys.argv[2]
	aggregation_attribute = sys.argv[3]
	aggregation_function = sys.argv[4]
	csv_data = read_csv_file(file_name)
	sorted_data = group_by_with_aggregation(updated_list(csv_data,grouping_attribute,aggregation_attribute,aggregation_function), grouping_attribute, aggregation_attribute, aggregation_function)
	save_to_csv(sorted_data,"O1.csv")
main()