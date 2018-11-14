package com.almeida.natanaels.identity_physical_movements.Service.Interface;

import com.almeida.natanaels.identity_physical_movements.Entities.Classification;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IClassifierService {

    @POST("classifier")
    Call<Classification> classify(@Body Map<String, Object> data);

}
