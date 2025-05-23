package com.usc.activity3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText name,phone,email;
    Button Submit;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.inputName);
        phone = (EditText) findViewById(R.id.inputPhone);
        email = (EditText) findViewById(R.id.inputEmail);
        Submit = findViewById(R.id.submit);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        getData();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameV = name.getText().toString();
                String phoneV = phone.getText().toString();
                String emailV = email.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Name,nameV);
                editor.putString(Phone,phoneV);
                editor.putString(Email,emailV);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Data has been save!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(){
        if(sharedPreferences.contains(Name)){
            name.setText(sharedPreferences.getString(Name,""));
        }
        if(sharedPreferences.contains(Phone)){
            phone.setText(sharedPreferences.getString(Phone,""));
        }
        if(sharedPreferences.contains(Email)){
            email.setText(sharedPreferences.getString(Email,""));
        }
    }
}