package com.almeida.natanaels.identity_physical_movements.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.almeida.natanaels.identity_physical_movements.Entities.Movement;

public class GyroscopeSensor extends SensorAbstract {
    public GyroscopeSensor(Context context) {
        super(context, Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public boolean add(Movement movement, SensorEvent event){
        super.add(movement, event);

        movement.setRotation_X(getAxisX());
        movement.setRotation_Y(getAxisY());
        movement.setRotation_Z(getAxisZ());

        return true;
    }
}
