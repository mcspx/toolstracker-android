package com.mstack.toolstracker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mstack.toolstracker.api.Api;
import com.mstack.toolstracker.database.History;
import com.mstack.toolstracker.database.History$Table;
import com.mstack.toolstracker.model.TrackingModel;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    @InjectView(R.id.recycleView)
    RecyclerView recycleView;

    String resultData;
    TrackingModel trackingModel;
    ProgressDialog progressDialog;
    History history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        history = new History();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Tools Tracker");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        Bundle bundle = getIntent().getExtras();
        resultData = bundle.getString("resultData");
        Log.d(TAG, "onCreate() returned: " + resultData);
        loadQRCode(resultData);

    }

    @OnClick(R.id.btn_back)
    public void back(){
        finish();
    }

    public void loadQRCode(String scanQR) {
        Call<TrackingModel> trackingModelCall = Api.api_loadQRCode().postQRCode(scanQR);
        trackingModelCall.enqueue(new Callback<TrackingModel>() {

            @Override
            public void onResponse(Response<TrackingModel> response, Retrofit retrofit) {
                trackingModel = response.body();
                if (trackingModel.getResultCode() == 200) {
                    recycleView.setHasFixedSize(true);
                    recycleView.addItemDecoration(new DividerItemDecoration(DetailActivity.this, DividerItemDecoration.VERTICAL_LIST));
                    recycleView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                    recycleView.setItemAnimator(new DefaultItemAnimator());
                    MyAdapter mAdapter = new MyAdapter(trackingModel);
                    recycleView.setAdapter(mAdapter);
                    progressDialog.dismiss();

//                    test(0);
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.d(TAG, "onFailure() returned: ");

            }
        });
    }

    public void test(int ooo){

        List<History> historyList = new Select().from(History.class).where(Condition.column(History$Table.ORDER).eq(ooo)).queryList();
        Log.d(TAG, "onCreate() returned: " + historyList);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private TrackingModel trackingModel;

        public MyAdapter(TrackingModel trackingModel) {
            this.trackingModel = trackingModel;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_detail, parent, false);

            final ViewHolder viewHolder = new ViewHolder(itemLayoutView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            if (trackingModel.getResultData().get(position).getState().equals("T")) {
                holder.txtViewLabel.setText(trackingModel.getResultData().get(position).getLabel());
                holder.txtViewValue.setText(trackingModel.getResultData().get(position).getValue());
                history.order = trackingModel.getResultData().get(position).getOrder();

            }

            if (trackingModel.getResultData().get(position).getLabel().equals("Service Code")){
                history.order = trackingModel.getResultData().get(position).getOrder();
                history.cServiceCode = trackingModel.getResultData().get(position).getValue();

                Log.d(TAG, "onBindViewHolder() cServiceCode: " + history.cServiceCode);
            }

            if (trackingModel.getResultData().get(position).getLabel().equals("Register Time")){
                history.order = trackingModel.getResultData().get(position).getOrder();
                history.cRegister_Time = trackingModel.getResultData().get(position).getValue();

                Log.d(TAG, "onBindViewHolder() cRegister_Time: " + history.cRegister_Time);
            }

            if (trackingModel.getResultData().get(position).getLabel().equals("TAT All")){
                history.order = trackingModel.getResultData().get(position).getOrder();
                history.cTAT_All = trackingModel.getResultData().get(position).getValue();

                Log.d(TAG, "onBindViewHolder() cTAT_All: " + history.cTAT_All);
            }

            if (trackingModel.getResultData().get(position).getLabel().equals("Condition #3")){
                history.order = trackingModel.getResultData().get(position).getOrder();
                history.cCondition3 = trackingModel.getResultData().get(position).getValue();

                Log.d(TAG, "onBindViewHolder() cCondition3: " + history.cCondition3);
            }


        }

        @Override
        public int getItemCount() {
            return trackingModel.getResultData().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txtViewLabel, txtViewValue;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                txtViewLabel = (TextView) itemLayoutView.findViewById(R.id.label);
                txtViewValue = (TextView) itemLayoutView.findViewById(R.id.value);

            }
        }
    }
}
