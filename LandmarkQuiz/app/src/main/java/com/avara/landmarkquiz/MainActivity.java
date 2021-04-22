package com.avara.landmarkquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView landmarkImage;
    ListView answerList;
    TextView highestScoreText;
    Random random;
    SharedPreferences score;

    int selectedImage;
    int highestScore;
    int newScore = 0;
    Runnable runnable;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        landmarkImage = findViewById(R.id.imageView);
        answerList    = findViewById(R.id.answerListView);
        highestScoreText = findViewById(R.id.highestScoreText);

        score = this.getSharedPreferences("com.avara.landmarkquiz", Context.MODE_PRIVATE);

        ArrayList<String> answerListArray = new ArrayList<>();

        answerListArray.add("Pisa, Italy");
        answerListArray.add("Colosseo, Italy");
        answerListArray.add("Eiffel, France");;
        answerListArray.add("London Bridge, UK");

        Bitmap pisa = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pisa);
        Bitmap colosseo = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.colosseo);
        Bitmap eiffel = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.eiffel);
        Bitmap londonbridge = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.londonbridge);

        ArrayList<Bitmap> landmarkImagesArray = new ArrayList<>();
        landmarkImagesArray.add(pisa);
        landmarkImagesArray.add(colosseo);
        landmarkImagesArray.add(eiffel);
        landmarkImagesArray.add(londonbridge);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, answerListArray);

        random = new Random();
        answerList.setAdapter(arrayAdapter);
        highestScore = score.getInt("highestScore", 0);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                selectedImage = random.nextInt(4);
                landmarkImage.setImageBitmap(landmarkImagesArray.get(selectedImage));
                answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(selectedImage == position){
                            newScore++;
                            Toast.makeText(MainActivity.this, "True", Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(MainActivity.this, "False", Toast.LENGTH_LONG);
                        }
                        System.out.println("new score : " + newScore);
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        highestScoreText.setText("Score : " + newScore);
                    }
                });
                handler.postDelayed(runnable, 5000);
            }
        };
        handler.post(runnable);
    }
}