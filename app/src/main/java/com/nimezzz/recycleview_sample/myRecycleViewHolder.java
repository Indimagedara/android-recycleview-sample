package com.nimezzz.recycleview_sample;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class myRecycleViewHolder extends RecyclerView.ViewHolder {
    public TextView mUserName, mUserStatus;
    public Button mDeleteRow;

    public myRecycleViewHolder(View itemView){
        super(itemView);

        mUserName = itemView.findViewById(R.id.mRowUserName);
        mUserStatus = itemView.findViewById(R.id.mRowUserStatus);
        mDeleteRow = itemView.findViewById(R.id.mRowDeleteBtn);

    }
}
