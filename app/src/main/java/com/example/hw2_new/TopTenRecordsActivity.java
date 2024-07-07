package com.example.hw2_new;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hw2_new.Fragments.FragmentList;
import com.example.hw2_new.Fragments.FragmentMap;
import com.example.hw2_new.Interface.Map_Callback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;


public class TopTenRecordsActivity extends AppCompatActivity {
    private FragmentList listFragment;
    private FragmentMap mapFragment;
    private MaterialButton  menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_records);

        menu_button = findViewById(R.id. menu_button);
        initViews();
    }

    private void initViews() {

        Map_Callback mapCallback = new Map_Callback() {
            @Override
            public void clickRecord(double latitude, double longitude) {
                Log.d("latitude,longitude ",latitude+"  " +"  "+ longitude);
                mapFragment.mapControl(new LatLng(latitude,longitude));
            }
        };

        listFragment = new FragmentList();
        listFragment.setMapCallback(mapCallback);
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list , listFragment).commit();

        mapFragment = new FragmentMap();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map , mapFragment).commit();

        menu_button.setOnClickListener(v -> returnToMenu());

    }

    private void returnToMenu() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}