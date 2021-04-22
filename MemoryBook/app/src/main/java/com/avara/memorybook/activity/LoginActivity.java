package com.avara.memorybook.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avara.memorybook.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailText;
    EditText passwordText;

    private String emailAddress;
    private String password;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText    = findViewById(R.id.emailAddressEditText);
        passwordText = findViewById(R.id.passwordEditText);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            Intent intentToMemoryBook = new Intent(LoginActivity.this, MemoryBookActivity.class);
            startActivity(intentToMemoryBook);
            finish();
        }



    }

    public void signIn(View view){

        emailAddress = emailText.getText().toString();
        password     = passwordText.getText().toString();

        if (emailAddress != null && password != null ){
            firebaseAuth.signInWithEmailAndPassword(emailAddress, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if(authResult != null){
                        Intent intentToMemoryBook = new Intent(LoginActivity.this, MemoryBookActivity.class);
                        startActivity(intentToMemoryBook);
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Please enter valid email address and valid password !", Toast.LENGTH_LONG).show();
        }



    }

    public void signUp(View view){

        emailAddress = emailText.getText().toString();
        password     = passwordText.getText().toString();

        if (emailAddress != null && password != null ){
            firebaseAuth.createUserWithEmailAndPassword(emailAddress, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if(authResult != null){
                        Intent intentToMemoryBook = new Intent(LoginActivity.this, MemoryBookActivity.class);
                        startActivity(intentToMemoryBook);
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}