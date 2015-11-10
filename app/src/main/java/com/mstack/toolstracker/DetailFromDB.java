package com.mstack.toolstracker;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mstack.toolstracker.R;
import com.mstack.toolstracker.model.TrackingModel;
import com.mstack.toolstracker.util.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailFromDB extends AppCompatActivity {

    private static final String TAG = "DetailFromDB";
    @InjectView(R.id.recycleView)
    RecyclerView recycleView;
    JSONUtils jsonUtils;
    String trackingModel;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        Bundle bundle = getIntent().getExtras();
        position = bundle.getString("position");
        jsonUtils = new JSONUtils();

        trackingModel = JSONUtils.stringJSONArraySD(Environment.getExternalStorageDirectory() + "/toolstracker/" + position +".json");


        recycleView.setHasFixedSize(true);
        recycleView.addItemDecoration(new DividerItemDecoration(DetailFromDB.this, DividerItemDecoration.VERTICAL_LIST));
        recycleView.setLayoutManager(new LinearLayoutManager(DetailFromDB.this));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        DetailFromDBAdapter mAdapter = new DetailFromDBAdapter(new Gson().fromJson(trackingModel,TrackingModel.class));
        recycleView.setAdapter(mAdapter);
    }

    private class DetailFromDBAdapter extends RecyclerView.Adapter<DetailFromDBAdapter.ViewHolder> {

        private TrackingModel trackingModel;

        public DetailFromDBAdapter(TrackingModel trackingModel) {
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
