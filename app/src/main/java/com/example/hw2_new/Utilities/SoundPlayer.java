package com.example.hw2_new.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {

    private Context context;
    private Executor executor;
    private MediaPlayer backgroundPlayer;
    private MediaPlayer coinPlayer;

    public SoundPlayer(Context context){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playBackgroundSound(int resID){
        if (backgroundPlayer != null){
            executor.execute(() -> {
                backgroundPlayer =MediaPlayer.create(context,resID);
                backgroundPlayer.setLooping(true);
                backgroundPlayer.setVolume(1.0f,1.0f);
                backgroundPlayer.start();
            });
        }
    }
    public void stopBackgroundSound(){
        if (backgroundPlayer != null){
            executor.execute(() -> {
                backgroundPlayer.stop();
                backgroundPlayer.release();
                backgroundPlayer =null;
            });
        }
    }

    public void playCoinCrashSound(int resID){
        executor.execute(() -> {
            if (coinPlayer != null) {
                coinPlayer.stop();
                coinPlayer.release();
            }
            coinPlayer = MediaPlayer.create(context, resID);
            coinPlayer.setLooping(false);
            coinPlayer.setVolume(1.0f, 1.0f);
            coinPlayer.start();
            coinPlayer.setOnCompletionListener(mp -> {
                mp.release();
                coinPlayer = null;
            });
        });
    }





}
