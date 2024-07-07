package com.example.hw2_new;


import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hw2_new.Logic.TopTenRecords;
import com.google.android.material.textview.MaterialTextView;

public class EndOfGameActivity extends AppCompatActivity {

    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_SCORE = "KEY_SCORE";
    private MaterialTextView main_status;
    private AppCompatButton main_Reset_button;
    private AppCompatButton main_TopTen;
    private MaterialTextView mainEnd_ScoreTitle;
    private MaterialTextView mainEnd_Score ;
    private  int score ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        findViews();
        initViews();
    }
    private void initViews() {
        Intent previousIntent = getIntent();
        score =previousIntent.getIntExtra(KEY_SCORE,0);
        String status = previousIntent.getStringExtra(KEY_STATUS);
        main_status.setText(status);
        main_Reset_button.setOnClickListener(v -> resetGame());
        main_TopTen.setOnClickListener(v -> topTen());
        mainEnd_Score.setText(String.valueOf(score));
    }

    private void resetGame() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    private void topTen(){
        Intent intent = new Intent(this, TopTenRecordsActivity.class);
        startActivity(intent);
        finish();

    }

    private void findViews() {
        main_status = findViewById(R.id.main_status);
        main_Reset_button =findViewById(R.id.main_Reset_button);
        mainEnd_Score= findViewById(R.id.mainEnd_Score);
        main_TopTen =findViewById(R.id.main_TopTen);
        mainEnd_ScoreTitle = findViewById(R.id.mainEnd_ScoreTitle);
    }
}