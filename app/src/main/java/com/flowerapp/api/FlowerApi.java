package com.flowerapp.api;

import android.content.Context;
import android.util.Log;

import com.flowerapp.bean.Flower;
import com.flowerapp.bean.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sandeep Tiwari on 6/15/2016.
 */

public class FlowerApi {

    private  static FlowerApi instance = null;
    private Gson gson = null;

    public static  FlowerApi getInstance(Context context){
        if(instance == null){
            instance = new FlowerApi(context);
        }
        return instance;
    }
    private FlowerApi(Context context){
        gson = new GsonBuilder()
                .setDateFormat(ApiConstants.DATE_FORMAT)
                .setLenient()
                .create();
     }

    public void reqFlower(){
        Log.i("RESPONCE", "CALL");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FlowerApiEndpointInterface apiService =
                retrofit.create(FlowerApiEndpointInterface.class);

        Call<Result> results = apiService.getAllFlowers();
        Log.i("RESPONCE", "CALL1");
        results.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("RESPONCE", ""+response.code() +" Json "+response.body().toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("RESPONCE", t.getMessage());
            }
        });
    }
}
