package com.example.hw2_new.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hw2_new.Logic.GameManager;
import com.example.hw2_new.Logic.Record;

public class LocationHelper {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private Context context;
    private double latitude;
    private double longitude;


    public LocationHelper(Context context) {
        this.context = context;
    }

    public static boolean checkLocationPermission(Context context) {
        return (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED);
    }

    public static void requestLocationPermission(AppCompatActivity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        }, LOCATION_PERMISSION_REQUEST_CODE);
    }

    public Record setRecordLatlng(GameManager gameManager) {
        Record record = new Record();
        record.setScore("" + gameManager.getScore());
        record.setTitleScore("Score");

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
            return null;
        }
        android.location.Location currLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (currLocation == null) {
            currLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (currLocation == null) {
                currLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
        }

        if (currLocation != null) {
            latitude = currLocation.getLatitude();
            longitude = currLocation.getLongitude();
        }
        if (latitude == 0.0) {
            latitude = 32.095230;
        }
        if (longitude == 0.0) {
            longitude = 34.972642;
        }

        record.setLatitude(latitude);
        record.setLongitude(longitude);

        return record;
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
