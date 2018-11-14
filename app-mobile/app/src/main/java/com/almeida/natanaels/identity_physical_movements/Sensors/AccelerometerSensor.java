package com.almeida.natanaels.identity_physical_movements.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.almeida.natanaels.identity_physical_movements.Entities.Movement;

public class AccelerometerSensor extends SensorAbstract {
    public AccelerometerSensor(Context context) {
        super(context, Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public boolean add(Movement movement, SensorEvent event){
        super.add(movement, event);

        movement.setAcceleration_X(getAxisX());
        movement.setAcceleration_Y(getAxisY());
        movement.setAcceleration_Z(getAxisZ());

        return true;
    }
}
