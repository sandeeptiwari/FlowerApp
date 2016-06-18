package com.flowerapp.api;

import com.flowerapp.bean.Result;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sandeep Tiwari on 6/15/2016.
 */
//http://www.androidwarriors.com/2015/12/retrofit-20-android-example-web.html
public interface FlowerApiEndpointInterface {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("pictures")
    Call<Result> getAllFlowers();
}
