package com.avara.simpsonbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

        Simpson homer = new Simpson("Homer Simpson","Nuclear", R.drawable.homersimpson);
        Simpson lisa  = new Simpson("Lisa Simpson", "Student", R.drawable.lisasimpson);
        Simpson bart  = new Simpson("Bart Simpson", "Student", R.drawable.bartsimpson);

        ArrayList<Simpson> simpsonList = new ArrayList<>();
        simpsonList.add(homer);
        simpsonList.add(lisa);
        simpsonList.add(bart);

        CustomAdapter customAdapter = new CustomAdapter(simpsonList, MainActivity.this);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("selectedSimpson", simpsonList.get(position));
                startActivity(intent);
            }
        });

    }
}