package com.usc.detablan_day2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView sName, sCourse, sYear, sWham;
    ImageView img;
    Button btn, btnNext, btnBrowse;
    EditText edName, edCourse, edYear, edWham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sName = findViewById(R.id.sname);
        sCourse = findViewById(R.id.scourse);
        sYear = findViewById(R.id.syear);
        sWham = findViewById(R.id.swham);
        img = findViewById(R.id.img);
        btn = findViewById(R.id.button);
        edName = findViewById(R.id.edname);
        edCourse = findViewById(R.id.edcourse);
        edYear = findViewById(R.id.edyear);
        edWham = findViewById(R.id.edwham);
        btnNext = findViewById(R.id.btnnext);
        btnBrowse = findViewById(R.id.btnbrowse);

        // Set the default image and its resource ID as a tag
        img.setImageResource(R.drawable.profile);
        img.setTag(R.drawable.profile);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sName.getText().toString();
                String course = sCourse.getText().toString();
                String year = sYear.getText().toString();
                String wham = sWham.getText().toString();
                
                // Get the resource name instead of ID
                String imagePath = "android.resource://" + getPackageName() + "/drawable/profile";
        
                boolean isValid = !TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(course) &&
                        !TextUtils.isEmpty(year) &&
                        !TextUtils.isEmpty(wham);
        
                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("course", course);
                intent.putExtra("year", year);
                intent.putExtra("wham", wham);
                intent.putExtra("imagePath", imagePath); // Pass the image path instead
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));
                startActivity(i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the EditText
                String name = edName.getText().toString();
                String course = edCourse.getText().toString();
                String year = edYear.getText().toString();
                String wham = edWham.getText().toString();
                int imageId = R.drawable.profile;

                // Set the values to the TextView
                sName.setText(name);
                sCourse.setText(course);
                sYear.setText(year);
                sWham.setText(wham);
                if (!name.isEmpty() || !course.isEmpty() || !year.isEmpty() || !wham.isEmpty()) {
                    img.setImageResource(imageId);
                    img.setTag(imageId); // Store the image resource ID as a tag
                }
            }
        });
    }
}