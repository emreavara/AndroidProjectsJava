package com.avara.memorybook.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;

import com.avara.memorybook.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class NewMemoryActivity extends AppCompatActivity {
    EditText newTitleText;
    EditText newDateText;
    ImageView newImageView;
    EditText newMemoryText;
    Uri selectedImageData;
    Bitmap selectedImage;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memory);

        newTitleText = findViewById(R.id.titleEditableTextView);
        newDateText  = findViewById(R.id.editTextDate);
        newImageView = findViewById(R.id.newImageView);
        newMemoryText= findViewById(R.id.newMemoryTextView);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth      = FirebaseAuth.getInstance();

        firebaseStorage   = FirebaseStorage.getInstance();
        storageReference  = firebaseStorage.getReference();

    }

    public void selectImage(View view){
        if(ContextCompat.checkSelfPermission(NewMemoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewMemoryActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }else {
                Toast.makeText(NewMemoryActivity.this, "Permission Denied !", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK &&data != null){
            selectedImageData = data.getData();
            try {
                if(Build.VERSION.SDK_INT >=28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), selectedImageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageData);
                }
                newImageView.setImageBitmap(selectedImage);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void saveMemory(View view){
        if(selectedImageData != null) {

            UUID imageUUID = UUID.randomUUID();
            String imagePath = "images" + imageUUID + ".jpg";

            storageReference.child(imagePath).putFile(selectedImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imagePath);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();
                            String titleText   = newTitleText.getText().toString();
                            String dateText    = newDateText.getText().toString();
                            String memoryText  = newMemoryText.getText().toString();

                            HashMap<String, Object> postData = new HashMap<>();
                            postData.put("downloadurl", downloadUrl);
                            postData.put("title", titleText);
                            postData.put("date", dateText);
                            postData.put("memory", memoryText);

                            firebaseFirestore.collection("Memories").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intentToMemories = new Intent(NewMemoryActivity.this, MemoryBookActivity.class);
                                    intentToMemories.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentToMemories);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewMemoryActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewMemoryActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG);
                }
            });



        }



    }
}