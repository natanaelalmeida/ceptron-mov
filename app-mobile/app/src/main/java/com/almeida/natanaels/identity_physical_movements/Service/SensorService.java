package com.almeida.natanaels.identity_physical_movements.Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.almeida.natanaels.identity_physical_movements.Entities.Movement;
import com.almeida.natanaels.identity_physical_movements.Sensors.AccelerometerSensor;
import com.almeida.natanaels.identity_physical_movements.Sensors.GyroscopeSensor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SensorService {

    private AccelerometerSensor mAccelerometerSensor;
    private GyroscopeSensor mGyroscopeSensor;
    private HashMap<Movement, Movement> mMovements;
    private Movement mMov;

    private String mLabel;
    private boolean isAccComplete;
    private boolean isGyrComplete;

    public SensorService(Context context) {
        this.mMovements = new HashMap<>();

        mAccelerometerSensor = new AccelerometerSensor(context);
        mGyroscopeSensor = new GyroscopeSensor(context);
    }

    public void init(String label){
        mMov = new Movement();
        mLabel = label;

        isAccComplete = false;
        isGyrComplete = false;
    }

    public void collect(SensorEvent event, int sensorType){
        if(event == null)
            return;

        if(sensorType == Sensor.TYPE_ACCELEROMETER)
            isAccComplete = mAccelerometerSensor.add(mMov, event);
        else if(sensorType == Sensor.TYPE_GYROSCOPE)
            isGyrComplete = mGyroscopeSensor.add(mMov, event);

        mMovements.put(mMov, mMov);
        if(isAccComplete && isGyrComplete)
            init(mLabel);
    }

    public void dispose(){
        this.mMovements.clear();
        mMov = null;
    }

    public Collection<Movement> getMovements() {
        return mMovements.values();
    }

    public Map<String, Object> getData(long time){
        if(mMovements.size() <= 0)
            return null;

        LinkedHashMap<String, Object> data;
        List<Map<String, Object>> series = new ArrayList<>();

        for(Map.Entry<Movement, Movement> movs : mMovements.entrySet()){
            Movement mov = movs.getValue();

            data = new LinkedHashMap<>();
            data.put("accX", mov.getAcceleration_X());
            data.put("accZ", mov.getAcceleration_Z());
            data.put("accY", mov.getAcceleration_Y());

            data.put("rotX", mov.getRotation_X());
            data.put("rotZ", mov.getRotation_Z());
            data.put("rotY", mov.getRotation_Y());

            series.add(data);
        }

        data = new LinkedHashMap<>();

        data.put("series", series);
        data.put("label", mLabel);
        data.put("time", time);
        data.put("dataHora", data());
        return data;
    }

    private String data(){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
