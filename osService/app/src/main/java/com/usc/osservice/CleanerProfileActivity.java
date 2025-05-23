package com.usc.osservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CleanerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_profile);

        // Get data from intent
        Intent intent = getIntent();
        String cleanerName = intent.getStringExtra("CLEANER_NAME");
        int cleanerAge = intent.getIntExtra("CLEANER_AGE", 0);
        float cleanerRating = intent.getFloatExtra("CLEANER_RATING", 0);
        String cleanerSchedule = intent.getStringExtra("CLEANER_SCHEDULE");
        int cleanerImage = intent.getIntExtra("CLEANER_IMAGE", android.R.drawable.ic_menu_gallery);
        String[] cleanerServices = intent.getStringArrayExtra("CLEANER_SERVICES");

        // Initialize views
        ImageView cleanerImageView = findViewById(R.id.cleanerImageView);
        TextView cleanerNameTextView = findViewById(R.id.cleanerNameTextView);
        TextView cleanerAgeTextView = findViewById(R.id.cleanerAgeTextView);
        TextView cleanerScheduleTextView = findViewById(R.id.cleanerScheduleTextView);
        RatingBar cleanerRatingBar = findViewById(R.id.cleanerRatingBar);
        TextView cleanerRatingText = findViewById(R.id.cleanerRatingText);
        TextView cleanerAddressTextView = findViewById(R.id.cleanerAddressTextView);
        TextView cleanerMobileTextView = findViewById(R.id.cleanerMobileTextView);
        
        RatingBar attitudeRatingBar = findViewById(R.id.attitudeRatingBar);
        RatingBar qualityRatingBar = findViewById(R.id.qualityRatingBar);
        RatingBar satisfactionRatingBar = findViewById(R.id.satisfactionRatingBar);
        
        Button bookButton = findViewById(R.id.bookButton);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton shareButton = findViewById(R.id.shareButton);

        // Set data to views
        cleanerImageView.setImageResource(cleanerImage);
        cleanerNameTextView.setText(cleanerName);
        cleanerAgeTextView.setText("Age: " + cleanerAge);
        cleanerScheduleTextView.setText("Available: " + cleanerSchedule);
        cleanerRatingBar.setRating(cleanerRating);
        cleanerRatingText.setText(String.valueOf(cleanerRating));
        
        // Generate random address and mobile number (in a real app, this would come from the backend)
        String randomAddress = generateRandomAddress();
        String randomMobile = generateRandomMobile();
        
        cleanerAddressTextView.setText(randomAddress);
        cleanerMobileTextView.setText(randomMobile);
        
        // Generate random capability ratings (in a real app, this would come from the backend)
        float attitudeRating = (float) (3.5 + Math.random() * 1.5);
        float qualityRating = (float) (3.5 + Math.random() * 1.5);
        float satisfactionRating = (float) (3.5 + Math.random() * 1.5);
        
        attitudeRatingBar.setRating(attitudeRating);
        qualityRatingBar.setRating(qualityRating);
        satisfactionRatingBar.setRating(satisfactionRating);

        // Setup button click listeners
        backButton.setOnClickListener(v -> finish());
        
        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cleaner!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out " + cleanerName + ", a great cleaner with a rating of " + cleanerRating);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
        
        bookButton.setOnClickListener(v -> {
            // Launch BookingActivity with cleaner info
            Intent bookingIntent = new Intent(CleanerProfileActivity.this, BookingActivity.class);
            bookingIntent.putExtra("CLEANER_NAME", cleanerName);
            bookingIntent.putExtra("CLEANER_IMAGE", cleanerImage);
            bookingIntent.putExtra("CLEANER_SERVICES", cleanerServices);
            startActivity(bookingIntent);
        });
    }
    
    private String generateRandomAddress() {
        String[] streets = {"Main St", "Park Ave", "Oak Rd", "Pine St", "Cedar Ln"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
        
        Random random = new Random();
        int streetNumber = random.nextInt(1000) + 1;
        String street = streets[random.nextInt(streets.length)];
        String city = cities[random.nextInt(cities.length)];
        
        return streetNumber + " " + street + ", " + city;
    }
    
    private String generateRandomMobile() {
        Random random = new Random();
        return "+1 " + (random.nextInt(900) + 100) + " " + (random.nextInt(900) + 100) + " " + (random.nextInt(9000) + 1000);
    }
}