package com.usc.osservice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoriesRecyclerView;
    private RecyclerView cleanersRecyclerView;
    private CategoryAdapter categoryAdapter;
    private CleanerAdapter cleanerAdapter;
    private EditText searchEditText;
    private TextView noResultsText;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DataManager
        dataManager = DataManager.getInstance(this);

        initViews();
        setupCategoriesRecyclerView();
        setupCleanersRecyclerView();
        setupClickListeners();
        setupSearchFunctionality();
    }

    private void initViews() {
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        cleanersRecyclerView = findViewById(R.id.cleanersRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        // Add a TextView for "No results found" message
        noResultsText = new TextView(this);
        noResultsText.setText("No results found");
        noResultsText.setTextSize(16);
        noResultsText.setVisibility(View.GONE);
        ((ViewGroup) cleanersRecyclerView.getParent()).addView(noResultsText);
        noResultsText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        noResultsText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        noResultsText.setPadding(0, 50, 0, 50);
    }

    private void setupCategoriesRecyclerView() {
        List<Category> categories = dataManager.getAllCategories();
        categoryAdapter = new CategoryAdapter(categories);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerView.setLayoutManager(layoutManager);
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void setupCleanersRecyclerView() {
        List<Cleaner> cleaners = dataManager.getAllCleaners();
        cleanerAdapter = new CleanerAdapter(cleaners);

        cleanersRecyclerView.setAdapter(cleanerAdapter);
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

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                String searchText = searchEditText.getText().toString().trim();
                performSearch(searchText);
                return true;
            }
            return false;
        });
    }

    private void performSearch(String searchText) {
        // Filter both categories and cleaners
        categoryAdapter.filter(searchText);
        cleanerAdapter.filter(searchText);

        // Show/hide "No results" message if needed
        boolean hasCategories = categoryAdapter.getItemCount() > 0;
        boolean hasCleaners = cleanerAdapter.getItemCount() > 0;

        if (!hasCategories && !hasCleaners && !searchText.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }
    }

    private void setupClickListeners() {
        // Update "More Categories" button to navigate to AllCategoriesActivity
        findViewById(R.id.moreCategoriesButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllCategoriesActivity.class);
            startActivity(intent);
        });

        // Update "More Cleaners" button to navigate to AllCleanersActivity
        findViewById(R.id.moreCleanersButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllCleanersActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.notificationButton).setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.profileButton).setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show()
        );

        categoryAdapter.setOnItemClickListener(position -> {
            Category category = dataManager.getAllCategories().get(position);
            // Launch CategorySelectionActivity with selected category
            Intent intent = new Intent(MainActivity.this, CategorySelectionActivity.class);
            intent.putExtra("CATEGORY_NAME", category.getName());
            startActivity(intent);
        });

        cleanerAdapter.setOnItemClickListener(new CleanerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Cleaner cleaner = dataManager.getAllCleaners().get(position);
                // Launch CleanerProfileActivity with selected cleaner
                Intent intent = new Intent(MainActivity.this, CleanerProfileActivity.class);
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
                Toast.makeText(MainActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}