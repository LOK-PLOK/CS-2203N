package com.usc.osservice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllCategoriesActivity extends AppCompatActivity {

    private RecyclerView categoriesRecyclerView;
    private AllCategoryAdapter categoryAdapter;
    private EditText searchEditText;
    private TextView noResultsText;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        // Initialize DataManager
        dataManager = DataManager.getInstance(this);

        initViews();
        setupCategoriesRecyclerView();
        setupSearchFunctionality();
    }

    private void initViews() {
        categoriesRecyclerView = findViewById(R.id.allCategoriesRecyclerView);
        searchEditText = findViewById(R.id.searchCategoryEditText);
        noResultsText = findViewById(R.id.noCategoriesResultsText);

        // Setup back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void setupCategoriesRecyclerView() {
        List<Category> categories = dataManager.getAllCategories();
        categoryAdapter = new AllCategoryAdapter(categories);

        // Use a grid layout with 2 columns for better display of all categories
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        categoriesRecyclerView.setLayoutManager(layoutManager);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(position -> {
            Category category = categories.get(position);
            // Launch CategorySelectionActivity with selected category
            Intent intent = new Intent(AllCategoriesActivity.this, CategorySelectionActivity.class);
            intent.putExtra("CATEGORY_NAME", category.getName());
            startActivity(intent);
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
        categoryAdapter.filter(searchText);

        // Show/hide "No results" message if needed
        if (categoryAdapter.getItemCount() == 0 && !searchText.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }
    }
}