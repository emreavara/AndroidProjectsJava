package com.avara.landmarkbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

//import static com.avara.landmarkbook.MainActivity.selectedImage;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView landmarkName;
    TextView countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        imageView = findViewById(R.id.imageView);
        landmarkName = findViewById(R.id.landmarkNameText);
        countryName = findViewById(R.id.countryNameText);

        Intent intent = getIntent();
        String landmarkNameText = intent.getStringExtra("landmarkName");
        landmarkName.setText(landmarkNameText);

        String countryNameText = intent.getStringExtra("countryName");
        countryName.setText(countryNameText);
        Singleton singleton = Singleton.getInstance();
        imageView.setImageBitmap(singleton.getChosenImage());
        //imageView.setImageBitmap(selectedImage);

    }
}