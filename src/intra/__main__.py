import csv
import uuid
from glob import glob
from dataset_process import createDataset
from acquisition import csv_processing

if __name__ == "__main__":

    print('Data collecting\n-----')
    createDataset("andando_test")
    createDataset("andando_passos_longos_test")
    createDataset("correndo_test")

    files = glob('data/movs/*.csv')

    id = uuid.uuid4().hex
    with open(u'data/datasets/movements_{}.csv'.format(id[0:10]), 'w', newline='') as f:
        fw = csv.writer(f, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        
        print('Data processing\n-----')
        for file in files:
            with open(file, 'r') as fs:
                lines = fs.readlines()
                row = csv_processing(lines)

                fw.writerow(row)

        print('Dataset created success!')
