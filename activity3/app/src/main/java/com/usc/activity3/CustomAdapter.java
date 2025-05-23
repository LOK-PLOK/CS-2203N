package com.usc.activity3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    ArrayList<String> personName;
    ArrayList<String> personPhone;
    ArrayList<String> personEmail;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> personName, ArrayList<String> personPhone, ArrayList<String> personEmail) {
        this.context = context;
        this.personName = personName;
        this.personPhone = personPhone;
        this.personEmail = personEmail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(personName.get(position));
        holder.phone.setText(personPhone.get(position));
        holder.email.setText(personEmail.get(position));
    }

    @Override
    public int getItemCount() {
        return personName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, email;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            phone = itemView.findViewById(R.id.Phone);
            email = itemView.findViewById(R.id.Email);
        }
    }
}