package com.nimezzz.recycleview_sample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<myRecycleViewHolder> {

    MainActivity mainActivity;
    ArrayList<User> userArrayList;

    public MyRecyclerViewAdapter(MainActivity mainActivity, ArrayList<User> userArrayList) {
        this.mainActivity = mainActivity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public myRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.single_row,parent,false);


        return new myRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleViewHolder holder, final int position) {
        holder.mUserName.setText(userArrayList.get(position).getUserName());
        holder.mUserStatus.setText(userArrayList.get(position).getUserStatus());
        holder.mDeleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelectedRow(position);
            }
        });
    }

    private void deleteSelectedRow(int position) {
        mainActivity.db.collection("users")
                .document(userArrayList.get(position).getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mainActivity, "Deleted", Toast.LENGTH_SHORT).show();
                        mainActivity.loadDataFromFirebase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mainActivity, "Unable to delete", Toast.LENGTH_SHORT).show();
                Log.w("--2--", e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
