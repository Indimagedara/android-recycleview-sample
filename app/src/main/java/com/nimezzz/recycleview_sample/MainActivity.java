package com.nimezzz.recycleview_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db;
    RecyclerView mRecycleView;
    ArrayList<User> userArrayList;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userArrayList = new ArrayList<>();

        setUpRecyclerView();
        setUpFireBase();
        addTestDataToFirebase();
        loadDataFromFirebase();
        



    }

    public void loadDataFromFirebase() {
        if(userArrayList.size() > 0){
            userArrayList.clear();
        }

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()){
                            User user = new User(
                                    querySnapshot.getId(),
                                    querySnapshot.getString("name"),
                                    querySnapshot.getString("status"));
                            userArrayList.add(user);
                        }
                        adapter = new MyRecyclerViewAdapter(MainActivity.this, userArrayList);
                        mRecycleView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error 1", Toast.LENGTH_SHORT).show();
                        Log.w("--1--",e.getMessage());
                    }
                });
    }

    private void addTestDataToFirebase() {
        Random random = new Random();

        for (int i = 0;i<2;i++) {


            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("name", "try" + random.nextInt(50));
            dataMap.put("status", "try" + random.nextInt(50));


            db.collection("users")
                    .add(dataMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setUpFireBase(){
        db = FirebaseFirestore.getInstance();
        
    }

    private void setUpRecyclerView() {
        mRecycleView = findViewById(R.id.mRecyclerView);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }
}
