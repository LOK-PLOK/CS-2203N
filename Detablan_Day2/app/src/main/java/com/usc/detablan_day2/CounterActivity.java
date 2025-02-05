package com.usc.detablan_day2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CounterActivity extends AppCompatActivity {

    Button AddBtn, MinusBtn, BackBtn;
    TextView Counter, Name, Course, Year, WHAM;
    ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        AddBtn = findViewById(R.id.addBtn);
        MinusBtn = findViewById(R.id.minusBtn);
        Counter = findViewById(R.id.counter);
        BackBtn = findViewById(R.id.btnback);
        Name = findViewById(R.id.txtname);
        Course = findViewById(R.id.txtcourse);
        Year = findViewById(R.id.txtyear);
        WHAM = findViewById(R.id.txtwham);
        Image = findViewById(R.id.image);

        // Get the data from the intent
        String sName = getIntent().getStringExtra("name");
        String sCourse = getIntent().getStringExtra("course");
        String sYear = getIntent().getStringExtra("year");
        String sWham = getIntent().getStringExtra("wham");
        String imagePath = getIntent().getStringExtra("imagePath");
        boolean isValid = getIntent().getBooleanExtra("isValid", false);

        if (isValid) {
            Name.setText(sName);
            Course.setText(sCourse);
            Year.setText(sYear);
            WHAM.setText(sWham);
            // Load image using the path
            Image.setImageURI(Uri.parse(imagePath));
        }else{
//          Optional
            Name.setVisibility(View.GONE);
            Course.setVisibility(View.GONE);
            Year.setVisibility(View.GONE);
            WHAM.setVisibility(View.GONE);
            Image.setVisibility(View.GONE);
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        AddBtn.setOnClickListener(v -> {
            int val = Integer.parseInt(Counter.getText().toString());
            val++;
            Counter.setText(String.valueOf(val));
        });

        MinusBtn.setOnClickListener(v -> {
            int val = Integer.parseInt(Counter.getText().toString());
            if(val != 0){
                val--;
                Counter.setText(String.valueOf(val));
            }
        });
    }
}