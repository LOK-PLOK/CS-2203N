package com.usc.detablan_day2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SpinnerListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner listSpin;
    ListView List;

    private String[] courses = {
            "Android",
            "Java",
            "Python",
            "C programming",
            "C++",
            "C#"
    };

    String[] topics = {
            "Algorithms",
            "Data Anal",
            "Calculus",
            "Techno",
            "Design Proj",
            "TPE"
    };

    Integer[] imgid = {
            R.drawable.morty,
            R.drawable.ic_launcher_background,
            R.drawable.morty,
            R.drawable.morty,
            R.drawable.ic_launcher_background,
            R.drawable.morty
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_list);

        listSpin = findViewById(R.id.listspin);
        List = findViewById(R.id.listview);
        listSpin.setOnItemSelectedListener(this);

        MyListAdapter adapter = new MyListAdapter(this, courses,topics,imgid);
        List.setAdapter(adapter);

        ArrayAdapter<String> arrCourses = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,courses);
        listSpin.setAdapter(arrCourses);

//        ArrayAdapter<String> arrTopics = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item,topics);
//        List.setAdapter(arrTopics);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(),courses[position],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}