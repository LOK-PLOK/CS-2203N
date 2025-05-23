package com.usc.osservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectionActivity extends AppCompatActivity {

    private RecyclerView servicesRecyclerView;
    private ServiceAdapter serviceAdapter;
    private String categoryName;
    private EditText searchServiceEditText;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        // Initialize DataManager
        dataManager = DataManager.getInstance(this);

        // Get category name from intent
        categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        if (categoryName == null) {
            categoryName = "Category";
        }

        // Set category name in toolbar
        TextView categoryTitleText = findViewById(R.id.categoryTitleText);
        categoryTitleText.setText(categoryName);

        // Initialize views
        servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        searchServiceEditText = findViewById(R.id.searchServiceEditText);

        // Setup back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Setup filter button
        ImageButton filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> 
            Toast.makeText(this, "Filter options", Toast.LENGTH_SHORT).show()
        );

        // Setup search functionality
        setupSearch();

        // Setup services list
        setupServicesList();
    }

    private void setupSearch() {
        searchServiceEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                String query = searchServiceEditText.getText().toString().trim();
                if (!query.isEmpty()) {
                    filterServices(query);
                    return true;
                }
            }
            return false;
        });
    }

    private void filterServices(String query) {
        List<Service> allServices = dataManager.getServicesForCategory(categoryName);
        List<Service> filteredServices = new ArrayList<>();
        
        for (Service service : allServices) {
            if (service.getName().toLowerCase().contains(query.toLowerCase()) ||
                service.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredServices.add(service);
            }
        }
        
        if (filteredServices.isEmpty()) {
            Toast.makeText(this, "No services match your search", Toast.LENGTH_SHORT).show();
        } else {
            serviceAdapter = new ServiceAdapter(filteredServices);
            setupServiceClickListeners(filteredServices);
            servicesRecyclerView.setAdapter(serviceAdapter);
        }
    }

    private void setupServicesList() {
        List<Service> services = dataManager.getServicesForCategory(categoryName);
        serviceAdapter = new ServiceAdapter(services);
        servicesRecyclerView.setAdapter(serviceAdapter);
        
        setupServiceClickListeners(services);
    }
    
    private void setupServiceClickListeners(List<Service> services) {
        serviceAdapter.setOnItemClickListener(position -> {
            Service selectedService = services.get(position);
            // Show list of cleaners for selected service
            Intent intent = new Intent(CategorySelectionActivity.this, CleanerListActivity.class);
            intent.putExtra("SERVICE_NAME", selectedService.getName());
            startActivity(intent);
        });
    }
}