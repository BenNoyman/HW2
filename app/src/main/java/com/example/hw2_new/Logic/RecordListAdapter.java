package com.example.hw2_new.Logic;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2_new.Interface.Map_Callback;
import com.example.hw2_new.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {
    private TopTenRecords topTenRecords ;
    private Map_Callback mapCallback;

    public RecordListAdapter(TopTenRecords topTenRecords , Map_Callback mapCallback ) {
        this.topTenRecords = topTenRecords;
        this.mapCallback = mapCallback;
        if (topTenRecords == null) {
            topTenRecords = new TopTenRecords();
            topTenRecords.setName("Fly Like The Wind");
        }
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_record, parent, false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(v);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        holder.record_LBL_title.setText(record.getTitleScore());
        holder.record_LBL_points.setText(record.getScore() + "");
        if (mapCallback != null) {
            holder.record_RL.setOnClickListener(v -> clickRecord(record.getLatitude(), record.getLongitude()));
        } else {
            Log.e("RecordListAdapter", "mapCallback is null");
        }
    }

    @Override
    public int getItemCount() {
        return this.topTenRecords == null ? 0 : this.topTenRecords.getRecords().size();
    }

    private Record getItem(int position){
        return this.topTenRecords.getRecords().get(position);
    }

    private void clickRecord(double latitude, double longitude){
        if (mapCallback != null)
            mapCallback.clickRecord(latitude,longitude);
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView record_IMG_img;
        private MaterialTextView record_LBL_title;
        private MaterialTextView record_LBL_points;
        private RelativeLayout record_RL;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            record_LBL_title =itemView.findViewById(R.id.record_LBL_title);
            record_IMG_img = itemView.findViewById(R.id.record_IMG_img);
            record_RL = itemView.findViewById(R.id.record_RL);
            record_LBL_points = itemView.findViewById(R.id.record_LBL_points);
        }
    }
}

