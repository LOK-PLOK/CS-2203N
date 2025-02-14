package com.usc.detablan_day2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class MenuPage extends AppCompatActivity {
    private MaterialCardView meal1Card, meal2Card, meal3Card, meal4Card, meal5Card;
    private TextView welcomeText;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        // Initialize MaterialCardViews
        meal1Card = findViewById(R.id.meal1_card);
        meal2Card = findViewById(R.id.meal2_card);
        meal3Card = findViewById(R.id.meal3_card);
        meal4Card = findViewById(R.id.meal4_card);
        meal5Card = findViewById(R.id.meal5_card);
        welcomeText = findViewById(R.id.welcome_text);
        profileImage = findViewById(R.id.profile_image);

        // Get the data from the intent
        String Name = getIntent().getStringExtra("name");
        String Address = getIntent().getStringExtra("address");
        String Payment = getIntent().getStringExtra("payment");
        String imagePath = getIntent().getStringExtra("imagePath");
        boolean isValid = getIntent().getBooleanExtra("isValid", false);

        if (isValid) {
            welcomeText.setText("Hello " + Name);
            profileImage.setImageURI(Uri.parse(imagePath));
        }

        // Set click listeners for each card
        meal1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = "A hamburger with a slice of melted cheese on top of the meat patty, added near the end of the cooking time.";
                String image1 = "android.resource://" + getPackageName() + "/drawable/burger";
                Intent intent = new Intent(MenuPage.this, CounterActivity.class);
                intent.putExtra("meal_name", "Cheesy Burger");
                intent.putExtra("meal_price", "$45.99");
                intent.putExtra("meal_Image", image1);
                intent.putExtra("description", Description);
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                intent.putExtra("imagePath", imagePath);
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

        meal2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = "An Italian dish of flattened bread dough topped with sauce and other ingredients, then baked.";
                String image2 = "android.resource://" + getPackageName() + "/drawable/pizza";
                Intent intent = new Intent(MenuPage.this, CounterActivity.class);
                intent.putExtra("meal_name", "Pizza");
                intent.putExtra("meal_price", "$49.99");
                intent.putExtra("meal_Image", image2);
                intent.putExtra("description", Description);
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                intent.putExtra("imagePath", imagePath);
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

        meal3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = "A side dish or snack typically made from deep-fried potatoes that have been cut into various shapes, especially thin strips.";
                String image3 = "android.resource://" + getPackageName() + "/drawable/fry";
                Intent intent = new Intent(MenuPage.this, CounterActivity.class);
                intent.putExtra("meal_name", "French Fries");
                intent.putExtra("meal_price", "$12.99");
                intent.putExtra("meal_Image", image3);
                intent.putExtra("description", Description);
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                intent.putExtra("imagePath", imagePath);
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

        meal4Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = "Is a pasta dish made with fatty cured pork, hard cheese, eggs, salt, and black pepper.";
                String image4 = "android.resource://" + getPackageName() + "/drawable/carbonara";
                Intent intent = new Intent(MenuPage.this, CounterActivity.class);
                intent.putExtra("meal_name", "Spaghetti Carbonara");
                intent.putExtra("meal_price", "$29.99");
                intent.putExtra("meal_Image", image4);
                intent.putExtra("description", Description);
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                intent.putExtra("imagePath", imagePath);
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

        meal5Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = "Is a thick, flat cut of meat or fish that's usually cooked by grilling or frying. ";
                String image5 = "android.resource://" + getPackageName() + "/drawable/stk";
                Intent intent = new Intent(MenuPage.this, CounterActivity.class);
                intent.putExtra("meal_name", "Steak");
                intent.putExtra("meal_price", "$599.99");
                intent.putExtra("meal_Image", image5);
                intent.putExtra("description", Description);
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                intent.putExtra("imagePath", imagePath);
                intent.putExtra("isValid", isValid);
                startActivity(intent);
            }
        });

         // Initialize the cart button
        ImageButton cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPage.this, addCart.class);
                intent.putExtra("name", Name);
                intent.putExtra("address", Address);
                intent.putExtra("payment", Payment);
                intent.putExtra("imagePath", imagePath);
                startActivity(intent);
            }
        });
    }
}