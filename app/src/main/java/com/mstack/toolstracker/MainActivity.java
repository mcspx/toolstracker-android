package com.mstack.toolstracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.mstack.toolstracker.api.Api;
import com.mstack.toolstracker.model.TrackingModel;
import com.mstack.toolstracker.scanqr.ZBarConstants;

import net.sourceforge.zbar.Symbol;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final int ZBAR_SCANNER_REQUEST = 1;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.btn_scan)
    public void scanQR() {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ScanQRActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_setting)
    public void btnSetting(){
        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String resultData;
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                resultData = String.valueOf(data.getStringExtra(ZBarConstants.SCAN_RESULT));
                Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("resultData", resultData);
                Log.d(TAG, "onActivityResult() returned: " + resultData);
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED && data != null) {
                String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                if (!TextUtils.isEmpty(error)) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

}
