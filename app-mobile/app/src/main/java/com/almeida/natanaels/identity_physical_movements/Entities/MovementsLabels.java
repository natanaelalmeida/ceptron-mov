package com.almeida.natanaels.identity_physical_movements.Entities;

import com.almeida.natanaels.identity_physical_movements.R;

public class MovementsLabels {

    public static String getDescForLabel(String label){

        switch (label){
            case "andando":
                return "Andando";
            case "andando_passos_longos":
                return "Andar a passos largos";
            case "correndo":
                return "Correndo";
            default:
                return "";
        }
    }

    public static String getLabelForDesc(String desc){
        switch (desc){
            case "Andar":
                return "andando";
            case "Andar a passos largos":
                return "andando_passos_longos";
            case "Correr":
                return "correndo";
            default:
                return "";
        }
    }

    public static int getAudio(String label){
        switch (label){
            case "andando":
                return R.raw.andando;
            case "andando_passos_longos":
                return R.raw.andando_passos_longos;
            case "correndo":
                return R.raw.correndo;
            default:
                return 0;
        }
    }
}
