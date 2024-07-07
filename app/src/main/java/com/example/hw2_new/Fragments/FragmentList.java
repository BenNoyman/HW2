package com.example.hw2_new.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw2_new.Interface.Map_Callback;
import com.example.hw2_new.Logic.RecordListAdapter;
import com.example.hw2_new.Logic.TopTenRecords;
import com.example.hw2_new.R;
import com.example.hw2_new.SharePreferences;

public class FragmentList extends Fragment {
    private RecyclerView main_LST_Records;
    private TopTenRecords topTenRecords;
    private Map_Callback mapCallback ;

    public FragmentList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initViews(v);
        return v;
    }

    private void findViews(View v) {
        main_LST_Records = v.findViewById(R.id.main_LST_Records);
    }

    private void initViews(View v) {
        topTenRecords = SharePreferences.getInstance().loadFromJson();
        RecordListAdapter recordListAdapter = new RecordListAdapter(topTenRecords , mapCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_Records.setAdapter(recordListAdapter);
        main_LST_Records.setLayoutManager(linearLayoutManager);
    }

    public void setMapCallback(Map_Callback mapCallback) {
        this.mapCallback = mapCallback;
    }
}