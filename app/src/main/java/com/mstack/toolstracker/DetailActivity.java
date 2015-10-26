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
import com.mstack.toolstracker.model.TrackingModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

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
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.d(TAG, "onFailure() returned: ");

            }
        });
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
