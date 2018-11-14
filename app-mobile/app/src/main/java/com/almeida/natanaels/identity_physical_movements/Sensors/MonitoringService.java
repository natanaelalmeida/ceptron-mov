package com.almeida.natanaels.identity_physical_movements.Sensors;

import android.content.Context;
import android.hardware.SensorEvent;
import android.util.Log;

import com.almeida.natanaels.identity_physical_movements.Firebase.Storage;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Classifier;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Monitoring;
import com.almeida.natanaels.identity_physical_movements.Service.ClassifierService;
import com.almeida.natanaels.identity_physical_movements.Service.SensorService;
import com.almeida.natanaels.identity_physical_movements.Util.Chronometer;

import java.util.Map;

public class MonitoringService {

    private final Chronometer mChronometer = new Chronometer();

    private Storage mStorage = new Storage();
    private SensorService mSensorService;
    private Monitoring mMonitoring;
    private Map<String, Object> mValues;

    private boolean isExec = false;

    public MonitoringService(Context context) {
        mSensorService = new SensorService(context);
        this.mMonitoring = (Monitoring) context;
    }

    public boolean execute() {
        if(!isExec){

            isExec = true;
            mSensorService.init(mMonitoring.getLabel());

            mMonitoring.start();
            mChronometer.start();

            if(mValues != null)
                mValues.clear();

        } else {

            isExec = false;
            mChronometer.stop();

            mValues = mSensorService.getData(mChronometer.getSeconds());
            mSensorService.dispose();

            mMonitoring.stop();
        }

        return isExec;
    }

    public void save(String label) {
        mStorage.save(label, mValues);
    }

    public void classifier(Classifier classifier) {
        new ClassifierService().classifier(mValues, classifier);
    }

    public void SensorChanged(SensorEvent event) {
        try{
            if(isExec)
                mSensorService.collect(event, event.sensor.getType());

        } catch (Exception e){
            Log.e("SensorChange", e.getMessage());
        }
    }

    public Map<String, Object> getValues(){
        return mValues;
    }
}
