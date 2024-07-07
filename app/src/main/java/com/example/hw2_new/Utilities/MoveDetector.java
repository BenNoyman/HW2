package com.example.hw2_new.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw2_new.interfaces.MoveCallback;

public class MoveDetector {

    private static final int MOVE_LEFT = -1;
    private static final int MOVE_RIGHT = 1;
    private static final int NO_MOVE = 0;
    private final SensorManager sensorManager;
    private final MoveCallback moveCallback ;
    private final Sensor sensor;
    private SensorEventListener sensorEventListener ;
    private int moveX =0;
    private long timestamp = 0l;




    public MoveDetector(Context context, MoveCallback moveCallback){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();

    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateMove(x);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //pass
            }
        };
    }
    private void calculateMove(float x) {
        if (System.currentTimeMillis() - timestamp > 350 ){
            timestamp= System.currentTimeMillis();

            if(x > 3.5)
                moveX = MOVE_LEFT;
            else if (x < -3.5)
                moveX = MOVE_RIGHT;
            else
                moveX = NO_MOVE;

            if(moveCallback != null)
                moveCallback.moveX();
        }
    }
    public void start(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop(){
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

    public int getMoveX() {
        return moveX;
    }

}
