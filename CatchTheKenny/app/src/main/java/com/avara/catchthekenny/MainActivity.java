package com.avara.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ContentHandlerFactory;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView scoreText;
    TextView timeText;
    Runnable runnable;
    Handler handler;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        imageView = findViewById(R.id.imageView);
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);

        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time  : " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                alertDialog.setTitle("Game Over");
                alertDialog.setMessage("Score : " + score);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeText.setText("Time : 10");
                        scoreText.setText("Score : 0");
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over. Score : "+ score, Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.show();
                handler.removeCallbacks(runnable);

            }
        }.start();



        handler = new Handler();
        runnable =  new Runnable() {
            @Override
            public void run() {

                imageView.setX(100+(int)(Math.random()*600));
                imageView.setY(100 + (int)(Math.random()*1000));
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);
    }

    public void increaseScore(View view){
        score++;
        scoreText.setText("Score : " + score);
    }
}