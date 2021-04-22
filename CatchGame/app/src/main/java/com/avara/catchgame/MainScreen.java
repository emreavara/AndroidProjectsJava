package com.avara.catchgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }
    public void startGame(View view){
        Intent intent = new Intent(MainScreen.this, MainActivity.class);
        startActivity(intent);

    }
    public void exitGame(View view){
        Intent intent = new Intent();
        finish();
    }
}