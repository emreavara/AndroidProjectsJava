package com.avara.memorybook.model;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avara.memorybook.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomLayoutAdaptor extends RecyclerView.Adapter<CustomLayoutAdaptor.MemoryHolder>{

    private ArrayList<String> titleArrayList;
    private ArrayList<String> dateArrayList;
    private ArrayList<String> memoryImageArrayList;
    private ArrayList<String> memoryTextArrayList;


    @NonNull
    @Override
    public MemoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryHolder holder, int position) {
        holder.titleTextView.setText(titleArrayList.get(position));
        holder.memoryTextView.setText(memoryTextArrayList.get(position));
        holder.dateTextView.setText(dateArrayList.get(position));



    }

    @Override
    public int getItemCount() {
        return titleArrayList.size();
    }

    public class MemoryHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        ImageView memoryImageView;
        TextView memoryTextView;

        public MemoryHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView   = itemView.findViewById(R.id.titleTextView);
            dateTextView    = itemView.findViewById(R.id.dateTextView);
            memoryImageView = itemView.findViewById(R.id.memoryImageView);
            memoryTextView  = itemView.findViewById(R.id.memoryTextView);
        }
    }
}
