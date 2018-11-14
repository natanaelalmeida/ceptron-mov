import numpy as np
import scipy.signal as signal
from scipy import fftpack as fft

def calculateMagnitudeOfVector(series):
   return np.linalg.norm(series)

def calculateMagnitudeOfFastTransformFourier(series):
    n = len(series)
    fourier = fft.fft(np.exp(2j * np.pi * series / n))   
    return np.max((2.0 / n) * np.abs(fourier[0:int(n / 2)]))

def calculateFrequency(series, time_step):
    
    frequency = fft.fftfreq(series.size, d=time_step)
    peaks = np.where(frequency > 0)
    frequencys = frequency[peaks]    

    return np.max(frequencys[-1:len(frequencys)])

def calculateMean(series):
    return np.mean(series)

def caculateVar(series):
    return np.var(series)