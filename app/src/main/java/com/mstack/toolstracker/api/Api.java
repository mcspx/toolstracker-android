package com.mstack.toolstracker.api;

import android.widget.Toast;

import com.mstack.toolstracker.model.TrackingModel;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by pitipong on 26/10/2558.
 */
public class Api {

    public static String URL = "http://api.echeck-tools.com";


    public static String getApiUrl() {
        return URL;
    }

    public void setApiUrl(String api) {
        this.URL = api;
    }

    private static LoadQRCode Iapi_loadQRCode;

    public static LoadQRCode api_loadQRCode() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Iapi_loadQRCode = retrofit.create(LoadQRCode.class);

        return Iapi_loadQRCode;
    }

    public interface LoadQRCode {
        @FormUrlEncoded
        @POST("/tracking")
        retrofit.Call<TrackingModel> postQRCode(@Field("ssid") String ssid);
    }


}
