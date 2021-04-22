package com.avara.storingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);
        sharedPreferences = this.getSharedPreferences("com.avara.storingdata", Context.MODE_PRIVATE);

        String storedText = sharedPreferences.getString("storedText","No");
        textView.setText("Stored Text : "+ storedText);
    }

    public void saveDataFunction(View view){

        if(!editText.getText().toString().matches(""))
        {
            String storedText = editText.getText().toString();
            textView.setText("Text : "+ storedText);
            sharedPreferences.edit().putString("storedText",storedText).apply();
        }
        else{
            textView.setText("Enter a text ");
        }
    }
    public void deleteStoredData(View view){
        String storedTextData = sharedPreferences.getString("storedText","");
        if(storedTextData != "")
            sharedPreferences.edit().remove("storedText").apply();
    }
}