package com.almeida.natanaels.identity_physical_movements.Service;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.almeida.natanaels.identity_physical_movements.Entities.Classification;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Classifier;
import com.almeida.natanaels.identity_physical_movements.Service.Interface.IClassifierService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassifierService {

    private final Retrofit retrofit;

    public ClassifierService() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.180:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void classifier(Map<String, Object> data, final Classifier classifier) {
        classifier.loading(View.VISIBLE);
        IClassifierService _classifier = retrofit.create(IClassifierService.class);
        _classifier.classify(data).enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(@NonNull Call<Classification> call, @NonNull Response<Classification> response) {
                Classification classification = response.body();
                if(classification != null) {
                    classifier.loading(View.GONE);
                    classifier.classification(classification.getPredict(), classification.getAudio());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Classification> call, @NonNull Throwable t) {
                Log.e("ClassifierService", t.getMessage());
                classifier.loading(View.GONE);
            }
        });
    }
}

