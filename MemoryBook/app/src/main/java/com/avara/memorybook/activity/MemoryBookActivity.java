package com.avara.memorybook.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.avara.memorybook.R;
import com.avara.memorybook.model.CustomLayoutAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Map;

public class MemoryBookActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    ArrayList<String> titleArrayListFB;
    ArrayList<String> dateArrayListFB;
    ArrayList<String> imageArrayListFB;
    ArrayList<String> memoryArrayListFB;

    CustomLayoutAdaptor memoryLayoutAdaptor;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater optionsMenuInflater = getMenuInflater();
        optionsMenuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_new_memory_option){
            Intent intentToNewMemory = new Intent(MemoryBookActivity.this, NewMemoryActivity.class);
            startActivity(intentToNewMemory);
        }else if (item.getItemId() == R.id.signout_option){
            firebaseAuth.signOut();
            Intent intentToLogIn = new Intent(MemoryBookActivity.this, LoginActivity.class);
            startActivity(intentToLogIn);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_book);


        titleArrayListFB = new ArrayList<>();
        dateArrayListFB  = new ArrayList<>();
        imageArrayListFB = new ArrayList<>();
        memoryArrayListFB= new ArrayList<>();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getMemoryDataFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        memoryLayoutAdaptor = new CustomLayoutAdaptor(titleArrayListFB, dateArrayListFB, imageArrayListFB, memoryArrayListFB);

        recyclerView.setAdapter(memoryLayoutAdaptor);

    }

    public void getMemoryDataFromFirestore(){
        CollectionReference collectionReference = firebaseFirestore.collection("Memories");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(MemoryBookActivity.this, error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String title = (String) data.get("title");
                        String downloadUrl = (String) data.get("downloadurl");
                        String date = (String) data.get("date");
                        String memory = (String) data.get("memory");

                        titleArrayListFB.add(title);
                        imageArrayListFB.add(downloadUrl);
                        dateArrayListFB.add(date);
                        memoryArrayListFB.add(memory);

                        memoryLayoutAdaptor.notifyDataSetChanged();


                    }


                }


            }
        });


    }
}