package com.mstack.toolstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

        edUrlApi.setText(preferenceManager.getBaseApi());
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

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Save Sucessful")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            finish();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();


        }
    }

}
