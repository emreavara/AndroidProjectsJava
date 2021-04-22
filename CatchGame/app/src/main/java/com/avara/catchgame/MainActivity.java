package com.avara.catchgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView timeText;
    TextView healthText;
    int screenX, screenY;
    ImageView imageView;
    int imageX, imageY;
    Runnable runnable;
    Handler handler;
    int health = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        timeText = findViewById(R.id.timeText);
        healthText = findViewById(R.id.healthText);
        imageView = findViewById(R.id.emreImage);
        imageX = imageView.getWidth();
        imageY = imageView.getHeight();

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time : " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Time Over !", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "You Lost ! ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainScreen.class);
                finish();
                startActivity(intent);
            }
        }.start();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                imageView.setX((imageX/2)+(int)(Math.random()*(screenY - imageX/2)));
                imageView.setY((imageY/2)+(int)(Math.random()*(screenX - imageY/2)));
                handler.postDelayed(runnable,500);
            }
        };
        handler.post(runnable);



    }
    public void decreaseHealth(View view){
        health -=5;
        healthText.setText("♥ : " + health);
        if(health == 0){
            Toast.makeText(MainActivity.this, "You Win ! ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MainScreen.class);
            finish();
            startActivity(intent);
        }

    }
}