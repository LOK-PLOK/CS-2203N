package com.usc.osservice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllCleanersActivity extends AppCompatActivity {

    private RecyclerView cleanersRecyclerView;
    private CleanerAdapter cleanerAdapter;
    private EditText searchEditText;
    private TextView noResultsText;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cleaners);

        // Initialize DataManager
        dataManager = DataManager.getInstance(this);

        initViews();
        setupCleanersRecyclerView();
        setupSearchFunctionality();
    }

    private void initViews() {
        cleanersRecyclerView = findViewById(R.id.allCleanersRecyclerView);
        searchEditText = findViewById(R.id.searchCleanerEditText);
        noResultsText = findViewById(R.id.noCleanersResultsText);
        
        // Setup back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void setupCleanersRecyclerView() {
        List<Cleaner> cleaners = dataManager.getAllCleaners();
        cleanerAdapter = new CleanerAdapter(cleaners);
        
        cleanersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cleanersRecyclerView.setAdapter(cleanerAdapter);

        cleanerAdapter.setOnItemClickListener(new CleanerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Cleaner cleaner = cleaners.get(position);
                // Launch CleanerProfileActivity with selected cleaner
                Intent intent = new Intent(AllCleanersActivity.this, CleanerProfileActivity.class);
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
                Toast.makeText(AllCleanersActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearchFunctionality() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim();
                performSearch(searchText);
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void performSearch(String searchText) {
        cleanerAdapter.filter(searchText);
        
        // Show/hide "No results" message if needed
        if (cleanerAdapter.getItemCount() == 0 && !searchText.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }
    }
}