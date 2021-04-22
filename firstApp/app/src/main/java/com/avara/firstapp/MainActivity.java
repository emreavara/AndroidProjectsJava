package com.avara.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeImage(View view){
        ImageView imageView = findViewById(R.id.image1);

        if(counter == 0){
            imageView.setImageResource(R.drawable.image2);
            counter = 1;
        }
        else if (counter == 1){
            imageView.setImageResource(R.drawable.image1);
            counter = 0;
        }
    }
}