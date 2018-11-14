package com.almeida.natanaels.identity_physical_movements.Interfaces;

public interface Classifier {
    void classification(String label, int audio);
    void loading(int visibility);
    void statusButtons(boolean status);
}
