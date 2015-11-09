package com.mstack.toolstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mstack.toolstracker.api.Api;
import com.mstack.toolstracker.model.TrackingModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";
    @InjectView(R.id.ed_url_api)
    EditText edUrlApi;
    @InjectView(R.id.btn_save)
    ImageView btnSave;
    PreferenceManager preferenceManager;
    String urlApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        preferenceManager = new PreferenceManager(this);

        edUrlApi.setText(preferenceManager.getBaseApi());
//        edUrlApi.setText("http://api.echeck-tools.com");

    }

    @OnClick(R.id.btn_save)
    public void save() {

        urlApi = edUrlApi.getText().toString();

        if (TextUtils.isEmpty(urlApi)) {
            edUrlApi.setError("please enter your API URL");
            return;
        } else {
            preferenceManager.SetBaseApi(urlApi);
            Api.URL = urlApi;
            checkURL();
        }


    }


    private CheckURL Iapi_CheckURL;

    public CheckURL api_CheckURL() {

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Iapi_CheckURL = retrofit.create(CheckURL.class);

            return Iapi_CheckURL;

        } catch (Exception e) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setMessage("Please check your URL")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();

            preferenceManager.SetBaseApi("http://api.echeck-tools.com");
            Api.URL = urlApi;

        } finally {

            Log.d(TAG, "finally api_CheckURL() returned: " + Iapi_CheckURL);

            preferenceManager.SetBaseApi("http://api.echeck-tools.com");
            Api.URL = urlApi;
        }

        preferenceManager.SetBaseApi("http://api.echeck-tools.com");
        Api.URL = urlApi;

        return null;
    }

    public interface CheckURL {
        @GET("/checking")
        retrofit.Call<TrackingModel> getCheckURL();
    }

    public void checkURL() {
        Call<TrackingModel> trackingModelCall = api_CheckURL().getCheckURL();
        trackingModelCall.enqueue(new Callback<TrackingModel>() {
            @Override
            public void onResponse(Response<TrackingModel> response, Retrofit retrofit) {

                if (response.code() == 200) {
                    if (response.body().getResultCode() == 200) {
                        preferenceManager.SetBaseApi(urlApi);
                        Api.URL = urlApi;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setMessage("Save Sucessful")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();

                    } else {
                        preferenceManager.SetBaseApi("http://api.echeck-tools.com");
                        Api.URL = urlApi;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setMessage("Please check your URL")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }

                } else {

                    preferenceManager.SetBaseApi("http://api.echeck-tools.com");
                    Api.URL = urlApi;

                    final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                    builder.setMessage("Please check your URL")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();

                }

//                Log.d(TAG, "onResponse() returned: " + response.body().getResultCode());
                Log.d(TAG, "onResponse() returned: " + response.code());
                Log.d(TAG, "onResponse() returned: " + response.errorBody());
                Log.d(TAG, "onResponse() returned: " + response.message());
                Log.d(TAG, "onResponse() returned: " + response.raw());


            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
