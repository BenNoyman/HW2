package com.example.hw2_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.example.hw2_new.Logic.GameManager;
import com.example.hw2_new.Logic.Record;
import com.example.hw2_new.Utilities.LocationHelper;
import com.example.hw2_new.Utilities.MoveDetector;
import com.example.hw2_new.Utilities.SignalManager;
import com.example.hw2_new.Utilities.SoundPlayer;
import com.example.hw2_new.interfaces.MoveCallback;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_MODE = "KEY_MODE";
    private Timer timer;
    private ImageView skyImageView1 ,skyImageView2;
    private ExtendedFloatingActionButton main_LBL_left;
    private ExtendedFloatingActionButton main_LBL_right;
    private AppCompatImageView[] main_IMG_planes;
    private AppCompatImageView[][] main_IMG_obstacles;
    private AppCompatImageView[] main_IMG_hearts;
    private MaterialTextView main_LBL_score;
    private boolean timerOn = false;
    private ImageView helicopter;
    private int gameModeIndex;
    GameManager gameManager;
    SignalManager signalManager;
    MoveDetector moveDetector;
    private Record record;
    private double latitude;
    private double longitude;
    private Context context;
    private LocationHelper locationHelper;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        gameManager = new GameManager();
        signalManager.init(this);
        locationHelper = new LocationHelper(this);
        initViews();
        startAnimation();
    }

    private void startAnimation(){
        TranslateAnimation skyAnimation1 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        skyAnimation1.setDuration(10000);
        skyAnimation1.setRepeatCount(Animation.INFINITE);
        skyImageView1.startAnimation(skyAnimation1);

        TranslateAnimation skyAnimation2 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f);
        skyAnimation2.setDuration(10000);

        skyAnimation2.setRepeatCount(Animation.INFINITE);
        skyAnimation1.setRepeatMode(Animation.RESTART);
        skyAnimation2.setRepeatMode(Animation.RESTART);
        skyImageView2.startAnimation(skyAnimation2);
    }

    private void findViews() {
        main_LBL_left = findViewById(R.id.main_LBL_left);
        main_LBL_right = findViewById(R.id.main_LBL_right);
        skyImageView1 = findViewById(R.id.skyImageView1);
        skyImageView2 = findViewById(R.id.skyImageView2);
        main_LBL_score = findViewById(R.id.main_LBL_score);

        main_IMG_planes = new AppCompatImageView[]{
                findViewById(R.id.LeftHeli),
                findViewById(R.id.MidLHeli),
                findViewById(R.id.MidHeli),
                findViewById(R.id.MidRHeli),
                findViewById(R.id.RightHeli),
        };
        main_IMG_obstacles = new AppCompatImageView[][]{
                {findViewById(R.id.main_LBL_api1), findViewById(R.id.main_LBL_api2), findViewById(R.id.main_LBL_api3), findViewById(R.id.main_LBL_api4), findViewById(R.id.main_LBL_api5)},
                {findViewById(R.id.main_LBL_api6), findViewById(R.id.main_LBL_api7), findViewById(R.id.main_LBL_api8), findViewById(R.id.main_LBL_api9), findViewById(R.id.main_LBL_api10)},
                {findViewById(R.id.main_LBL_api11), findViewById(R.id.main_LBL_api12), findViewById(R.id.main_LBL_api13), findViewById(R.id.main_LBL_api14), findViewById(R.id.main_LBL_api15)},
                {findViewById(R.id.main_LBL_api16), findViewById(R.id.main_LBL_api17), findViewById(R.id.main_LBL_api18), findViewById(R.id.main_LBL_api19), findViewById(R.id.main_LBL_api20)},
                {findViewById(R.id.main_LBL_api21), findViewById(R.id.main_LBL_api22), findViewById(R.id.main_LBL_api23), findViewById(R.id.main_LBL_api24), findViewById(R.id.main_LBL_api25)},
                {findViewById(R.id.main_LBL_api26), findViewById(R.id.main_LBL_api27), findViewById(R.id.main_LBL_api28), findViewById(R.id.main_LBL_api29), findViewById(R.id.main_LBL_api30)},
        };
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
    }

    private void initViews() {

        Intent previousIntent = getIntent();
        gameModeIndex = previousIntent.getIntExtra(KEY_MODE, 0);

        if (gameModeIndex == 0) {
            main_LBL_left.setOnClickListener(v -> moveLeft(-1));
            main_LBL_right.setOnClickListener(v -> moveRight(1));
        } else if (gameModeIndex == 1) {
            main_LBL_left.setVisibility(View.INVISIBLE);
            main_LBL_right.setVisibility(View.INVISIBLE);

            moveDetector = new MoveDetector(this, new MoveCallback() {
                @Override
                public void moveX() {
                    if (moveDetector.getMoveX() < 0)
                        moveLeft(moveDetector.getMoveX());
                    else if (moveDetector.getMoveX() > 0)
                        moveRight(moveDetector.getMoveX());
                }
            });
        }

        main_LBL_score.setText(String.valueOf(gameManager.getScore()));

        for (int i = 0; i < main_IMG_planes.length; i++) {
            Glide.with(this).asGif().load(R.drawable.helicopter_new).into(main_IMG_planes[i]);

            if (gameManager.getCurrentPlane()[i] == 1)
                main_IMG_planes[i].setVisibility(View.VISIBLE);
            else
                main_IMG_planes[i].setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < main_IMG_obstacles.length - 1; i++) {
            for (int j = 0; j < main_IMG_obstacles[0].length; j++) {
                main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }
        startTimer();
    }


    private void moveLeft(int moveX) {
        int currentPosition = getCurrentPosition();
        if (currentPosition > 0) {
            gameManager.updatePlanePosition(currentPosition - 1);
            updatePlaneVisibility();
        }
    }

    private void moveRight(int moveX) {
        int currentPosition = getCurrentPosition();
        if (currentPosition < main_IMG_planes.length - 1) {
            gameManager.updatePlanePosition(currentPosition + 1);
            updatePlaneVisibility();
        }
    }

    private int getCurrentPosition() {
        int[] currentPlane = gameManager.getCurrentPlane();
        for (int i = 0; i < currentPlane.length; i++) {
            if (currentPlane[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    private void updatePlaneVisibility() {
        int[] currentPlane = gameManager.getCurrentPlane();
        for (int i = 0; i < main_IMG_planes.length; i++) {
            if (currentPlane[i] == 1) {
                main_IMG_planes[i].setVisibility(View.VISIBLE);
            } else {
                main_IMG_planes[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void startTimer() {
        if (!timerOn) {
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateUI());
                }
            }, 0L, 650);
        }
    }

    private void updateUI() {

        gameManager.updateObstacleUI();

        main_LBL_score.setText(String.valueOf(gameManager.getScore()));

        for (int i = 0; i < gameManager.getCurrentObstacles().length; i++) {
            for (int j = 0; j < gameManager.getCurrentObstacles()[0].length; j++) {
                if (gameManager.getCurrentObstacles()[i][j] == 1) {
                    main_IMG_obstacles[i][j].setImageResource(R.drawable.astroid);
                    main_IMG_obstacles[i][j].setVisibility(View.VISIBLE);
                } else if (gameManager.getCurrentObstacles()[i][j] == 2) {
                    main_IMG_obstacles[i][j].setImageResource(R.drawable.coin);
                    main_IMG_obstacles[i][j].setVisibility(View.VISIBLE);
                } else
                    main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }

        handleCollisions();

        if (gameManager.getCrash() >= 3) {
            gameOver();
        }
    }

    public void gameOver() {
        skyImageView1.clearAnimation();
        skyImageView2.clearAnimation();
        record = locationHelper.setRecordLatlng(gameManager);
        if (record != null) {
            SharePreferences.getInstance().saveToJson(gameManager.getScore(), record.getLatitude(), record.getLongitude());
        } else {
            SignalManager.getInstance().toast("Last known location is null");
        }
        timer.cancel();
        changeActivity("GAME OVER");
    }

    private void handleCollisions() {
        for (int i = 0; i < gameManager.getCurrentPlane().length; i++) {
            if (gameManager.getCurrentObstacles()[gameManager.getCurrentObstacles().length - 1][i] == 1 &&
                    gameManager.getCurrentPlane()[i] == 1) {
                main_IMG_hearts[gameManager.getCrash() - 1].setVisibility(View.INVISIBLE);
                main_IMG_obstacles[gameManager.getTOTAL_ROWS() - 1][i].setVisibility(View.INVISIBLE); //after the crash the obstacle will not continue
                SoundPlayer crashSoundPlayer = new SoundPlayer(this);
                crashSoundPlayer.playCoinCrashSound(R.raw.crash_sound);
                SignalManager.getInstance().toast("BOOM 💥");
                SignalManager.getInstance().vibrate(300L);
            } else if (gameManager.getCurrentObstacles()[gameManager.getCurrentObstacles().length - 1][i] == 2 &&
                    gameManager.getCurrentPlane()[i] == 1) {
                main_IMG_obstacles[gameManager.getTOTAL_ROWS() - 1][i].setVisibility(View.INVISIBLE);
                SignalManager.getInstance().toast("🪙 +10");
                SoundPlayer coinSoundPlayer = new SoundPlayer(this);
                coinSoundPlayer.playCoinCrashSound(R.raw.collect_coin);
            }
        }
    }

    private void changeActivity(String status) {
        Intent intent = new Intent(this, EndOfGameActivity.class);
        intent.putExtra(EndOfGameActivity.KEY_STATUS, status);
        intent.putExtra(EndOfGameActivity.KEY_SCORE,gameManager.getScore());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        if (gameModeIndex == 1)
            moveDetector.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        if (gameModeIndex == 1)
            moveDetector.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}