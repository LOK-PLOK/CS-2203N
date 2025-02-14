package com.usc.detablan_day2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView sName, sAddress, sPayment;
    ImageView img;
    Button btn, btnNext;
    EditText edName, edAddress, edPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        sName = findViewById(R.id.sname);
        sAddress = findViewById(R.id.saddress);
        sPayment = findViewById(R.id.spayment);
        
        // Initialize ImageView
        img = findViewById(R.id.img);
        
        // Initialize Buttons
        btn = findViewById(R.id.button);
        btnNext = findViewById(R.id.btnnext);
        
        // Initialize EditTexts
        edName = findViewById(R.id.edname);
        edAddress = findViewById(R.id.edaddress);
        edPayment = findViewById(R.id.edpayment);

        // Set the default image
        img.setImageResource(R.drawable.ic_launcher_background);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sName.getText().toString();
                String address = sAddress.getText().toString();
                String payment = sPayment.getText().toString();
                
                String imagePath = "android.resource://" + getPackageName() + "/drawable/morty";
        
                boolean isValid = !TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(address) &&
                        !TextUtils.isEmpty(payment);
        
                Intent intent = new Intent(MainActivity.this, MenuPage.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("payment", payment);
                intent.putExtra("imagePath", imagePath);
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from EditText fields
                String name = edName.getText().toString();
                String address = edAddress.getText().toString();
                String payment = edPayment.getText().toString();

                // Set the values to the TextViews
                sName.setText(name);
                sAddress.setText(address);
                sPayment.setText(payment);

                // Update image if any field is not empty
                if (!name.isEmpty() || !address.isEmpty() || !payment.isEmpty()) {
                    img.setImageResource(R.drawable.morty);
                }
            }
        });
    }
}