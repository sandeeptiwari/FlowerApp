package com.flowerapp.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.flowerapp.R;
import com.flowerapp.api.ApiConstants;
import com.flowerapp.api.FlowerApi;
import com.flowerapp.api.FlowerApiEndpointInterface;
import com.flowerapp.bean.Flower;
import com.flowerapp.bean.Result;
import com.flowerapp.database.FlowerDataHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sandeep Tiwari on 6/15/2016.
 */

public class SplashActivity extends BaseActivity implements Callback<Result>{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
       // FlowerApi.getInstance(this).reqFlower();
        List<Flower> flowers = FlowerDataHandler.getAllFlowers(this);
        if(flowers == null || flowers.size() == 0) {
            Gson gson = new GsonBuilder()
                    .setDateFormat(ApiConstants.DATE_FORMAT)
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            FlowerApiEndpointInterface apiService =
                    retrofit.create(FlowerApiEndpointInterface.class);

            Call<Result> results = apiService.getAllFlowers();

            results.enqueue(this);
        }else {
            navigateToHomeActivity(flowers);
        }
    }

    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        Log.i("RESPONCE", "CALL1");
        Result result = response.body();
        List<Flower> flowers = result.getData();
        flowers.remove(0);
        int len = flowers.size();
        for (int index = 0; index < len; index++) {
            Flower flower = flowers.get(index);
            FlowerDataHandler.saveFlower(this, flower);
        }
        navigateToHomeActivity(flowers);
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {

    }

    private void navigateToHomeActivity(List<Flower> flowers){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, HomeActivity.class);
        bundle.putSerializable("data", (Serializable) flowers);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
