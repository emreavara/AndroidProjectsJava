package com.avara.halal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView productList;
    SQLiteDatabase localDatabase;
    ArrayList<String> productNameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList    = findViewById(R.id.productListView);
        productNameArray = new ArrayList<>();
        idArray        = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productNameArray);
        productList.setAdapter(arrayAdapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ProductInfoActivity.class);
                intent.putExtra("productId", idArray.get(position));
                intent.putExtra("calledBy", "existingProduct");
                startActivity(intent);

            }
        });

        getData();
    }

    public void getData(){

        localDatabase = openOrCreateDatabase("HalalProducts", MODE_PRIVATE, null);

        Cursor cursor = localDatabase.rawQuery("SELECT * FROM halalproducts", null);
        int productNameIndex = cursor.getColumnIndex("productname");
        int id = cursor.getColumnIndex("id");

        while(cursor.moveToNext()){
            productNameArray.add(cursor.getString(productNameIndex));
            idArray.add(cursor.getInt(id));
        }

        arrayAdapter.notifyDataSetChanged();

        cursor.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addProuctMenu){
            Intent productInfoIntent = new Intent(MainActivity.this, ProductInfoActivity.class);
            productInfoIntent.putExtra("calledBy", "newProduct");
            startActivity(productInfoIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}