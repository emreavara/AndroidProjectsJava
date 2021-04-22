package com.avara.landmarkquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void startGame(View view){
        Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void exitGame(View view){
        //Intent intent = new Intent();
        finish();
    }
}