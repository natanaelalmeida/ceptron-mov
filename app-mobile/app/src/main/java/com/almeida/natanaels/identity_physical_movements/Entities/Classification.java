package com.almeida.natanaels.identity_physical_movements.Entities;

import com.google.gson.annotations.SerializedName;

public class Classification {

    @SerializedName("predict")
    private String predict;

    public String getPredict() {
        return MovementsLabels.getDescForLabel(predict);
    }

    public int getAudio() {
        return MovementsLabels.getAudio(predict);
    }
}
