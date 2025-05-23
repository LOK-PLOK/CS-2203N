package com.usc.activity3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JSONParserActivity extends AppCompatActivity {
    RecyclerView RV;

    ArrayList<String> personName = new ArrayList<>();
    ArrayList<String> personPhone = new ArrayList<>();
    ArrayList<String> personEmail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparser);

        RV = findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(linearLayoutManager);

        try {
            // get Json object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            // fetch data
            JSONArray userArray = obj.getJSONArray("users");

            for(int i = 0; i < userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);
                personName.add(userDetail.getString("name"));
                personPhone.add(userDetail.getString("phone"));
                personEmail.add(userDetail.getString("email"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(
                this,
                personName,
                personPhone,
                personEmail
        );
        RV.setAdapter(customAdapter);
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("user_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}