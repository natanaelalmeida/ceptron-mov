from intra import firebase_storage as fs
import xlsxwriter
import csv
import uuid

path = 'data/movs'

def createDataset(source):
    docs = fs.getCollection(source)
    
    for doc in docs:

        header = []

        rowAx = []
        rowAy = []
        rowAz = []

        rowRx = []
        rowRy = []
        rowRz = []

        series = doc.get(u'series')

        header.append(doc.get(u'label'))
        header.append(doc.get(u'time'))

        for serie in series:
            rowAx.append(serie.get("accX"))
            rowAy.append(serie.get("accY"))
            rowAz.append(serie.get("accZ"))
            
            rowRx.append(serie.get("rotX"))
            rowRy.append(serie.get("rotY"))
            rowRz.append(serie.get("rotZ"))

        save(source, header, rowAx, rowAy, rowAz, rowRx, rowRy, rowRz)

def save(filename, header, rowAx, rowAy, rowAz, rowRx, rowRy, rowRz):
    guid = uuid.uuid4().hex
    with open(u'{}/{}.csv'.format(path, guid[0:10]), 'w', newline='') as csvfile:
        fw = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)

        fw.writerow(header)
        fw.writerow(rowAx)
        fw.writerow(rowAy)
        fw.writerow(rowAz)

        fw.writerow(rowRx)
        fw.writerow(rowRy)
        fw.writerow(rowRz) 