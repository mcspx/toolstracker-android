package com.mstack.toolstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mstack.toolstracker.api.Api;
import com.mstack.toolstracker.database.History;
import com.mstack.toolstracker.scanqr.ZBarConstants;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.language.Select;

import net.sourceforge.zbar.Symbol;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int ZBAR_SCANNER_REQUEST = 1;
    private static final String TAG = "MainActivity";
    @InjectView(R.id.mRecycleview)
    RecyclerView mRecycleview;
    @InjectView(R.id.txtNodata)
    TextView txtNodata;
    private List<History> histories;
    History history;
    PreferenceManager preferenceManager;
    FlowQueryList<History> flowQueryList;
    MyHistoryAdapter myHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        FlowManager.init(this);
        flowQueryList = new FlowQueryList<History>(History.class);
        preferenceManager = new PreferenceManager(this);
        if (null != preferenceManager.getBaseApi()) {
            Api.URL = preferenceManager.getBaseApi();
        }

        txtNodata.setVisibility(View.INVISIBLE);
        mRecycleview.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
//        initRecycle();
//        Log.d(TAG, "onResume() returned: " + histories.size());
    }

    private void initData() {
        histories = new Select().from(History.class).queryList();
//        FlowQueryList<History> flowQueryList = new FlowQueryList<History>(History.class);
        if (histories.size() != 0) {
            Log.d(TAG, "initData() returned: " + histories.get(0).cServiceCode);
            initRecycle();
            txtNodata.setVisibility(View.INVISIBLE);
            mRecycleview.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "clear() returned: " + histories.size());
//        Log.d(TAG, "initData() returned: " + flowQueryList.get(0).cServiceCode);

    }

    private void initRecycle() {
        myHistoryAdapter = new MyHistoryAdapter(histories);
        mRecycleview.setHasFixedSize(true);
        mRecycleview.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mRecycleview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        mRecycleview.setAdapter(myHistoryAdapter);
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
    public void btnSetting() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_clear)
    public void clear() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to clear history ?")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flowQueryList.removeAll(histories);
                        Log.d(TAG, "clear() returned: " + histories.size());
                        txtNodata.setVisibility(View.VISIBLE);
                        mRecycleview.setVisibility(View.INVISIBLE);
                    }
                })
                ;
        final AlertDialog alert = builder.create();
        alert.show();


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

    private class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.ViewHolder> {

        private List<History> histories;

        public MyHistoryAdapter(List<History> histories) {
            this.histories = histories;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_history, parent, false);

            final ViewHolder viewHolder = new ViewHolder(itemLayoutView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.txtViewServiceCode.setText(histories.get(position).cServiceCode);
            holder.txtViewRegisterTime.setText(histories.get(position).cRegister_Time);
            holder.txtViewTatAll.setText(histories.get(position).cTAT_All);
//
            Log.d(TAG, "cServiceCode returned: " + histories.get(position).cServiceCode);
            Log.d(TAG, "cRegister_Time returned: " + histories.get(position).cRegister_Time);
            Log.d(TAG, "cTAT_All returned: " + histories.get(position).cTAT_All);

            if (histories.get(position).cCondition3.equals("00")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_00);
            } else if (histories.get(position).cCondition3.equals("11")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_11);
            } else if (histories.get(position).cCondition3.equals("21")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_21);
            } else if (histories.get(position).cCondition3.equals("22")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_22);
            } else if (histories.get(position).cCondition3.equals("31")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_31);
            } else if (histories.get(position).cCondition3.equals("32")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_32);
            } else if (histories.get(position).cCondition3.equals("99")) {
                holder.imgViewCondition3.setImageResource(R.drawable.ic_99);
            }

            holder.txtViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flowQueryList.remove(histories.remove(position));
//                    Log.d(TAG, "onClick() returned: " + histories.get(position).cServiceCode);
                }
            });
        }

        @Override
        public int getItemCount() {
            return histories.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txtViewServiceCode, txtViewRegisterTime, txtViewTatAll, txtViewDelete;
            public ImageView imgViewCondition3;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                txtViewServiceCode = (TextView) itemLayoutView.findViewById(R.id.txtServiceCode);
                txtViewRegisterTime = (TextView) itemLayoutView.findViewById(R.id.txtRegisterTime);
                txtViewTatAll = (TextView) itemLayoutView.findViewById(R.id.txtTAT);
                imgViewCondition3 = (ImageView) itemLayoutView.findViewById(R.id.imgCondition3);
                txtViewDelete = (TextView) itemLayoutView.findViewById(R.id.txtDelete);

            }
        }
    }
}