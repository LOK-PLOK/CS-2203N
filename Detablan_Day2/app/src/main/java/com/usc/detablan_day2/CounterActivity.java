package com.usc.detablan_day2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CounterActivity extends AppCompatActivity {

    Button AddBtn, MinusBtn;
    TextView Counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        AddBtn = findViewById(R.id.addBtn);
        MinusBtn = findViewById(R.id.minusBtn);
        Counter = findViewById(R.id.counter);

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