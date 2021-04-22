package com.avara.halal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Permission;

public class ProductInfoActivity extends AppCompatActivity {
    // View
    ImageView productImageView;
    EditText productBrandText;
    EditText productNameText;
    EditText productSerialNumberText;
    EditText halalStatusText;
    Button saveButton;

    // Database
    SQLiteDatabase localDatabase;

    // Variables
    int externalStorageRqstCode = 1;
    int actionPickRqstCode      = 2;
    Bitmap selectedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        // View
        productImageView        = findViewById(R.id.addProductImageView);
        productBrandText        = findViewById(R.id.productBrandEditableText);
        productNameText         = findViewById(R.id.productNameEditableText);
        productSerialNumberText = findViewById(R.id.serialNumberEditableText);
        halalStatusText         = findViewById(R.id.halalStatusEditableText);
        saveButton              = findViewById(R.id.saveButton);


        // Database
        localDatabase           = openOrCreateDatabase("HalalProducts", MODE_PRIVATE, null);

        Intent intent = getIntent();
        String calledBy = intent.getStringExtra("calledBy");

        if (calledBy.matches("existingProduct")){
            int productId = intent.getIntExtra("productId",1);
            saveButton.setVisibility(View.INVISIBLE);

            try {
                Cursor cursor = localDatabase.rawQuery("SELECT * FROM halalproducts WHERE id = ?", new String[] {String.valueOf(productId)});

                int productBrandNameIndex = cursor.getColumnIndex("productbrandname");
                int productNameIndex      = cursor.getColumnIndex("productname");
                int serialNumberIndex     = cursor.getColumnIndex("serialnumber");
                int halalStatusIndex      = cursor.getColumnIndex("halalstatus");
                int productImageIndex     = cursor.getColumnIndex("productimage");

                while (cursor.moveToNext()){
                    productBrandText.setText(cursor.getString(productBrandNameIndex));
                    productNameText.setText(cursor.getString(productNameIndex));
                    productSerialNumberText.setText(cursor.getString(serialNumberIndex));
                    halalStatusText.setText("Not Verified");

                    byte[] imageBytes = cursor.getBlob(productImageIndex);
                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(imageBytes,0, imageBytes.length);
                    productImageView.setImageBitmap(bitmapImage);

                }
                cursor.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        } else{
            saveButton.setVisibility(View.VISIBLE);

            Bitmap selectedImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.addproduct);
            productImageView.setImageBitmap(selectedImage);
        }
    }

    public void addProductImage(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},externalStorageRqstCode);
        }else{
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, actionPickRqstCode);
        }
        // if(ContextCompat.checkSelfPermission(this.checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) != )
    }
    public void saveProductInfo(View view){
        String productBrandName = productNameText.getText().toString();
        String productName      = productNameText.getText().toString();
        String productSerialNumber = productSerialNumberText.getText().toString();
        String halalStatus      = halalStatusText.getText().toString();

        Bitmap smallImage = makeSmallerImage(selectedImage, 250);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        if( productBrandName.matches(" ") && productName.matches(" ") && productSerialNumber.matches(" ")){
            Toast.makeText(ProductInfoActivity.this, "Invalid Inputs, Please Fill All Fields", Toast.LENGTH_LONG).show();
        } else{
            try{

                localDatabase = openOrCreateDatabase("HalalProducts", MODE_PRIVATE, null);
                localDatabase.execSQL("CREATE TABLE IF NOT EXISTS halalproducts (id INTEGER PRIMARY KEY, productbrand VARCHAR, productname VARCHAR, serialnumber VARCHAR, halalstatus VARCHAR, productimage BLOB)");

                String sqlString = "INSERT INTO halalproducts (productbrand, productname, serialnumber, halalstatus, productimage) VALUES (?, ?, ?, ?, ?)";
                SQLiteStatement sqLiteStatement = localDatabase.compileStatement(sqlString);
                sqLiteStatement.bindString(1, productBrandName);
                sqLiteStatement.bindString(2, productName);
                sqLiteStatement.bindString(3, productSerialNumber);
                sqLiteStatement.bindString(4, halalStatus);
                sqLiteStatement.bindBlob(5, byteArray);

                sqLiteStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(ProductInfoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == externalStorageRqstCode){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,actionPickRqstCode);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == actionPickRqstCode && resultCode == RESULT_OK && data != null ){
            Uri imageData = data.getData();

            try {
                if (Build.VERSION.SDK_INT >=28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                }
                productImageView.setImageBitmap(selectedImage);
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public Bitmap makeSmallerImage(Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width/ (float)height;

        if( bitmapRatio > 1){
            width = maxSize;
            height = (int)(height/bitmapRatio);
        }else{
            height = maxSize;
            width  = (int)(width/bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }
}