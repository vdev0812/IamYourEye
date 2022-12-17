package com.example.iamyoureye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tv_point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        tv_point = findViewById(R.id.tv_point);
        int p = getIntent().getExtras().getInt("points");
        tv_point.setText("Point "+p+"/24");
    }
}