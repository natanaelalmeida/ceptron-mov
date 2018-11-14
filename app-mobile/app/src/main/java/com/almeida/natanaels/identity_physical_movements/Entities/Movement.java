package com.almeida.natanaels.identity_physical_movements.Entities;

public class Movement {
    private float acceleration_X;
    private float acceleration_Y;
    private float acceleration_Z;

    private float rotation_X;
    private float rotation_Y;
    private float rotation_Z;

    public void setAcceleration_X(float acceleration_X) {
        this.acceleration_X = acceleration_X;
    }

    public void setAcceleration_Y(float acceleration_Y) {
        this.acceleration_Y = acceleration_Y;
    }

    public void setAcceleration_Z(float acceleration_Z) {
        this.acceleration_Z = acceleration_Z;
    }


    public void setRotation_X(float rotation_X) {
        this.rotation_X = rotation_X;
    }

    public void setRotation_Y(float rotation_Y) {
        this.rotation_Y = rotation_Y;
    }

    public void setRotation_Z(float rotation_Z) {
        this.rotation_Z = rotation_Z;
    }


    public float getAcceleration_X() {
        return acceleration_X;
    }

    public float getAcceleration_Y() {
        return acceleration_Y;
    }

    public float getAcceleration_Z() {
        return acceleration_Z;
    }


    public float getRotation_X() {
        return rotation_X;
    }

    public float getRotation_Y() {
        return rotation_Y;
    }

    public float getRotation_Z() {
        return rotation_Z;
    }
}
