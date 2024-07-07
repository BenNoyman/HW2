package com.example.hw2_new;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw2_new.Utilities.LocationHelper;
import com.google.android.material.button.MaterialButton;


public class StartActivity extends AppCompatActivity {

    private MaterialButton button;
    private MaterialButton sensor;
    private MaterialButton top_ten_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        checkLocationPermission();
    }

    private void findViews() {
        button = findViewById(R.id.start_button);
        sensor = findViewById(R.id.start_sensor);
        top_ten_button = findViewById(R.id.top_ten_button);
    }

    private void checkLocationPermission() {
        if (!LocationHelper.checkLocationPermission(this)) {
            LocationHelper.requestLocationPermission(this);
        } else {
            initViews();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationHelper.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initViews();
            } else {
                // if permission denied, show a message to the user
                Toast.makeText(this, "Location permission is required to play the game", Toast.LENGTH_SHORT).show();
                checkLocationPermission();
            }
        }
    }

    private void initViews() {
        button.setOnClickListener(v -> startGame(0));
        sensor.setOnClickListener(v -> startGame(1));
        top_ten_button.setOnClickListener(v -> topTenActivity());
    }

    private void topTenActivity() {
        Intent intent = new Intent(this, TopTenRecordsActivity.class);
        startActivity(intent);
        finish();
    }

    private void startGame(int i) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_MODE, i);
        startActivity(intent);
        finish();
    }
}
