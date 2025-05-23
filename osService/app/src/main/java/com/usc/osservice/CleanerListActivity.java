package com.usc.osservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CleanerListActivity extends AppCompatActivity {

    private RecyclerView cleanersListRecyclerView;
    private CleanerAdapter cleanerAdapter;
    private String serviceName;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_list);

        // Initialize DataManager
        dataManager = DataManager.getInstance(this);

        // Get service name from intent
        serviceName = getIntent().getStringExtra("SERVICE_NAME");
        if (serviceName == null) {
            serviceName = "Service";
        }

        // Set service name in toolbar
        TextView serviceTitleText = findViewById(R.id.serviceTitleText);
        serviceTitleText.setText(serviceName);

        // Initialize views
        cleanersListRecyclerView = findViewById(R.id.cleanersListRecyclerView);

        // Setup back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Setup filter button
        findViewById(R.id.filterButton).setOnClickListener(v -> 
            Toast.makeText(this, "Filter options", Toast.LENGTH_SHORT).show()
        );

        // Setup cleaners list
        setupCleanersList();
    }

    private void setupCleanersList() {
        List<Cleaner> cleaners = dataManager.getCleanersForService(serviceName);
        cleanerAdapter = new CleanerAdapter(cleaners);
        cleanersListRecyclerView.setAdapter(cleanerAdapter);

        cleanerAdapter.setOnItemClickListener(new CleanerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Cleaner cleaner = cleaners.get(position);
                // Launch CleanerProfileActivity with selected cleaner
                Intent intent = new Intent(CleanerListActivity.this, CleanerProfileActivity.class);
                intent.putExtra("CLEANER_NAME", cleaner.getName());
                intent.putExtra("CLEANER_AGE", cleaner.getAge());
                intent.putExtra("CLEANER_RATING", cleaner.getRating());
                intent.putExtra("CLEANER_SCHEDULE", cleaner.getSchedule());
                intent.putExtra("CLEANER_IMAGE", cleaner.getImageResource());
                
                // Pass services as a string array
                String[] servicesArray = cleaner.getServices().toArray(new String[0]);
                intent.putExtra("CLEANER_SERVICES", servicesArray);
                
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(int position) {
                Toast.makeText(CleanerListActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}