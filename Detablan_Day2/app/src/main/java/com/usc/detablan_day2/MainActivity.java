package com.usc.detablan_day2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    TextView sName,sCourse,sYear,sWham;
    ImageView img;
    Button btn;
    EditText edName,edCourse,edYear,edWham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sName = (TextView) findViewById(R.id.sname);
        sCourse = (TextView) findViewById(R.id.scourse);
        sYear = (TextView) findViewById(R.id.syear);
        sWham = (TextView) findViewById(R.id.swham);
        img = findViewById(R.id.img);
        btn = findViewById(R.id.button);
        edName = findViewById(R.id.edname);
        edCourse = findViewById(R.id.edcourse);
        edYear = findViewById(R.id.edyear);
        edWham = findViewById(R.id.edwham);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                String course = edCourse.getText().toString();
                String year = edYear.getText().toString();
                String wham = edWham.getText().toString();

                sName.setText(name);
                sCourse.setText(course);
                sYear.setText(year);
                sWham.setText(wham);
                img.setImageResource(R.drawable.profile);
            }
        });

    }
}