package com.usc.detablan_day2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class CounterActivity extends AppCompatActivity {

    Button addBtn, minusBtn, backBtn, addToCartBtn, checkOutBtn;
    TextView counter, mealName, mealPrice, mealDetails;
    ImageView mealImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        // Initialize Views
        addBtn = findViewById(R.id.addBtn);
        minusBtn = findViewById(R.id.minusBtn);
        counter = findViewById(R.id.counter);
        backBtn = findViewById(R.id.btnback);
        addToCartBtn = findViewById(R.id.add_to_cart);
        checkOutBtn = findViewById(R.id.check_out);
        
        mealName = findViewById(R.id.meal_name);
        mealPrice = findViewById(R.id.meal_price);
        mealDetails = findViewById(R.id.meal_details);
        mealImage = findViewById(R.id.meal_image);

        // Get the data from the intent
        String Name = getIntent().getStringExtra("name");
        String Address = getIntent().getStringExtra("address");
        String Payment = getIntent().getStringExtra("payment");
        String mealname = getIntent().getStringExtra("meal_name");
        String mealprice = getIntent().getStringExtra("meal_price");
        String mealimage = getIntent().getStringExtra("meal_Image");
        String imagePath = getIntent().getStringExtra("imagePath");
        String Description = getIntent().getStringExtra("description");
        boolean isValid = getIntent().getBooleanExtra("isValid", false);

        // Set default image
        mealImage.setImageURI(Uri.parse(mealimage));
        mealName.setText(mealname);
        mealPrice.setText(mealprice);
        mealDetails.setText(Description);

        // Button Click Listeners
        addBtn.setOnClickListener(v -> {
            int val = Integer.parseInt(counter.getText().toString());
            val++;
            counter.setText(String.valueOf(val));
        });

        minusBtn.setOnClickListener(v -> {
            int val = Integer.parseInt(counter.getText().toString());
            if(val > 0){
                val--;
                counter.setText(String.valueOf(val));
            }
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CounterActivity.this, MenuPage.class);
            intent.putExtra("name", Name);
            intent.putExtra("address", Address);
            intent.putExtra("payment", Payment);
            intent.putExtra("imagePath", imagePath);
            intent.putExtra("isValid", isValid);
            startActivity(intent);
        });

        addToCartBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt(counter.getText().toString());
            if (quantity > 0) {
                // Get existing cart data
                SharedPreferences prefs = getSharedPreferences("CartPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                
                // Save or update item in cart
                String itemKey = mealname.replaceAll("\\s+", "_").toLowerCase();
                int existingQuantity = prefs.getInt(itemKey + "_quantity", 0);
                
                editor.putString(itemKey + "_name", mealname);
                editor.putString(itemKey + "_price", mealprice);
                editor.putString(itemKey + "_image", mealimage);
                editor.putInt(itemKey + "_quantity", existingQuantity + quantity);
                editor.putFloat(itemKey + "_total", (float)calculateTotal(mealprice, existingQuantity + quantity));
                
                // Update item keys list
                Set<String> itemKeys = new HashSet<>(prefs.getStringSet("item_keys", new HashSet<>()));
                itemKeys.add(itemKey);
                editor.putStringSet("item_keys", itemKeys);
                
                editor.apply();
        
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        checkOutBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt(counter.getText().toString());
            if (quantity > 0) {
                Intent intent = new Intent(CounterActivity.this, checkOut.class);
                intent.putExtra("meal_name", mealname);
                intent.putExtra("meal_price", mealprice);
                intent.putExtra("meal_image", mealimage);
                intent.putExtra("quantity", quantity);
                intent.putExtra("total_price", calculateTotal(mealprice, quantity));
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                startActivity(intent);
            }
        });
    }

    private double calculateTotal(String price, int quantity) {
        String numericPrice = price.replace("$", "");
        return Double.parseDouble(numericPrice) * quantity;
    }
}