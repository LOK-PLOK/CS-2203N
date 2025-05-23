package com.usc.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class loginPage extends AppCompatActivity {
    EditText Vusername,Vpassword;
    Button login;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Vusername = findViewById(R.id.inputUserName);
        Vpassword = findViewById(R.id.inputPass);
        login = findViewById(R.id.btnlogin);
        close = findViewById(R.id.closebtn);
        String usernameText = getIntent().getStringExtra("username");
        String passwordText = getIntent().getStringExtra("password");
        boolean isValid = getIntent().getBooleanExtra("isValid", false);


        if(isValid){
            Vusername.setText(usernameText);
            Vpassword.setText(passwordText);
        }else{
            Vusername.setText("");
            Vpassword.setText("");
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginPage.this,MainActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginPage.this,MainActivity.class);
                i.putExtra("username",Vusername.getText().toString());
                i.putExtra("password",Vpassword.getText().toString());
                startActivity(i);
            }
        });
    }
}