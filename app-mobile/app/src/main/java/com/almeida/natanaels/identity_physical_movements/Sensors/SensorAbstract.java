package com.almeida.natanaels.identity_physical_movements.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.almeida.natanaels.identity_physical_movements.Entities.Movement;

public abstract class SensorAbstract {
    private final SensorManager mSensorManager;
    private Sensor mSensor;
    private final int mSensorType;

    private SensorEvent mSensorEvent;
    private SensorEventListener mEventListener;

    SensorAbstract(Context context, int sensorType) {
        if(!(context instanceof SensorEventListener)){
            throw new RuntimeException("");
        }

        mEventListener = (SensorEventListener)context;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        mSensorType = sensorType;

        init();
    }

    private void init(){
        mSensor = mSensorManager.getDefaultSensor(mSensorType);
        register();
    }

    private void register(){
        mSensorManager.registerListener(mEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public boolean add(Movement movement, SensorEvent event){
        mSensorEvent = event;
        return true;
    }

    float getAxisX(){
        return mSensorEvent.values[0];
    }

    float getAxisY(){
        return mSensorEvent.values[1];
    }

    float getAxisZ(){
        return mSensorEvent.values[2];
    }
}
