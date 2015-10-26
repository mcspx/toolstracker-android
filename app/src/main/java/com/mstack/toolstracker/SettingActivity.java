package com.mstack.toolstracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.mstack.toolstracker.api.Api;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

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

    }

    @OnClick(R.id.btn_save)
    public void save(){
        urlApi = edUrlApi.getText().toString();
        if (TextUtils.isEmpty(urlApi)) {
            edUrlApi.setError("please enter your API URL");
            return;
        }else {
            preferenceManager.SetBaseApi(urlApi);
            Api.URL = urlApi;
        }
    }

}
