package com.usc.detablan_day2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyListAdapter extends ArrayAdapter<String> {


   private final Activity context;
   private final String[] courses;
   private final String[] topics;
   private final Integer[] imgid;

    public MyListAdapter(Activity context, String[] courses, String[] topics, Integer[] imgid) {
        super(context, R.layout.mylistlayout, courses);
        this.context = context;
        this.courses = courses;
        this.topics = topics;
        this.imgid = imgid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylistlayout,null,true);

        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        TextView textHeader = (TextView) rowView.findViewById(R.id.headertext);
        TextView textSub = (TextView) rowView.findViewById(R.id.subtext);

        img.setImageResource(imgid[position]);
        textHeader.setText(courses[position]);
        textSub.setText(topics[position]);
        return rowView;
    };
}
