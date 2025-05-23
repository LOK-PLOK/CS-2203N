package com.usc.practice;

import android.content.Intent;
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

public class signUpPage extends AppCompatActivity {
    EditText Editusername,Editname,Editpassword;
    ImageView close;
    Button save,createAcc;
    TextView username,name,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        username = findViewById(R.id.txtUserName);
        name = findViewById(R.id.txtName);
        pass = findViewById(R.id.txtPass);
        Editusername = findViewById(R.id.editUserName);
        Editname = findViewById(R.id.editName);
        Editpassword = findViewById(R.id.editPass);
        close = findViewById(R.id.closebtn);
        save = findViewById(R.id.save);
        createAcc = findViewById(R.id.createAcc);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Vusername = Editusername.getText().toString();
                String Vname = Editname.getText().toString();
                String Vpass = Editpassword.getText().toString();

                username.setText(Vusername);
                name.setText(Vname);
                pass.setText(Vpass);
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Vusername = username.getText().toString();
                String Vname = name.getText().toString();
                String Vpass = pass.getText().toString();
                boolean isValid = !Vusername.isEmpty() && !Vname.isEmpty() && !Vpass.isEmpty();

                Intent i = new Intent(signUpPage.this,loginPage.class);
                i.putExtra("username",Vusername);
                i.putExtra("name",Vname);
                i.putExtra("password",Vpass);
                i.putExtra("isValid",isValid);
                startActivity(i);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signUpPage.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}