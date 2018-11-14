import intra.features_acquisition as fa
import numpy as np
from io import StringIO

idxLabel_time = 0
idxAccX = 0
idxAccY = 1
idxAccZ = 2
idxRotX = 3
idxRotY = 4
idxRotZ = 5

def csv_processing(lines):
    x = StringIO(lines[idxLabel_time])
    label_time = np.genfromtxt(x, delimiter=',', dtype=str)
    del lines[idxLabel_time]

    series = np.genfromtxt(lines, delimiter=',', dtype=float)
    label = label_time[0]
    time = float(label_time[1])

    data = __processing__(time, series)
    data.append(label)

    return data

def data_processing(values):
    if values is None or len(values) <= 0:
        return

    time = float(values['time'])
    series = np.array(values['series'])

    accX = []
    accY = []
    accZ = []

    rotX = []
    rotY = []
    rotZ = []

    for serie in series:
        accX.append(serie['accX'])
        accY.append(serie['accY'])
        accZ.append(serie['accZ'])

        rotX.append(serie['rotX'])
        rotY.append(serie['rotY'])
        rotZ.append(serie['rotZ'])

    data = np.array([accX, accY, accZ, rotX, rotY, rotZ])
    return  np.matrix(__processing__(abs(time), data))

def __processing__(time, series):
    
    if time <= 0:
        return

    time_step = (time / 100)

    # Calculate frequency of time serie
    frequency = fa.calculateFrequency((series[0] if len(series) > 1 else series), time_step)

    # Calculate the mean from all axes of accelerometer and gyroscope
    mnAccX = fa.calculateMean(series[idxAccX])
    mnAccY = fa.calculateMean(series[idxAccY])
    mnAccZ = fa.calculateMean(series[idxAccZ])

    mnRotX = fa.calculateMean(series[idxRotX])
    mnRotY = fa.calculateMean(series[idxRotY])
    mnRotZ = fa.calculateMean(series[idxRotZ])

    # Calculate the variance from all axes of accelerometer and gyroscope
    vrAccX = fa.caculateVar(series[idxAccX])
    vrAccY = fa.caculateVar(series[idxAccY])
    vrAccZ = fa.caculateVar(series[idxAccZ])

    vrRotX = fa.caculateVar(series[idxRotX])
    vrRotY = fa.caculateVar(series[idxRotY])
    vrRotZ = fa.caculateVar(series[idxRotZ])

    # Calculate the magnitude of acceleration
    mvAccX = fa.calculateMagnitudeOfVector(series[idxAccX])
    mvAccY = fa.calculateMagnitudeOfVector(series[idxAccY])
    mvAccZ = fa.calculateMagnitudeOfVector(series[idxAccZ])

    # Calculate the magnitude of fast transform fourier of acceleration
    mgtAccX = fa.calculateMagnitudeOfFastTransformFourier(series[idxAccX])
    mgtAccY = fa.calculateMagnitudeOfFastTransformFourier(series[idxAccY])
    mgtAccZ = fa.calculateMagnitudeOfFastTransformFourier(series[idxAccZ])

    return [
        time_step,
        frequency,
        mnAccX, 
        mnAccY, 
        mnAccZ, 
        mnRotX, 
        mnRotY, 
        mnRotZ,
        vrAccX, 
        vrAccY, 
        vrAccZ, 
        vrRotX, 
        vrRotY, 
        vrRotZ,
        mvAccX,
        mvAccY,
        mvAccZ,
        mgtAccX,
        mgtAccY,
        mgtAccZ]