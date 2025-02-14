package com.usc.detablan_day2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class addCart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageButton homeButton;
    private Button checkoutButton;
    private TextView totalItems, totalAmount;
    private ArrayList<CartItem> cartItems;
    private ListView cartList;

    private Spinner filterSpinner;
    private ArrayList<CartItem> allCartItems;
    private ArrayList<CartItem> filteredCartItems;
    
    private String[] categories = {
        "All Items",
        "Burger",
        "Pizza",
        "French Fries",
        "Carbonara",
        "Steak"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);

        // Initialize views
        homeButton = findViewById(R.id.home_button);
        checkoutButton = findViewById(R.id.checkout_button);
        totalItems = findViewById(R.id.total_items);
        totalAmount = findViewById(R.id.total_amount);
        cartList = findViewById(R.id.cart_list);
        filterSpinner = findViewById(R.id.filter_spinner);

        String imagePath = getIntent().getStringExtra("imagePath");
        boolean isValid = getIntent().getBooleanExtra("isValid", false);

        // Set up spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,  // Changed this line
            categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);
        filterSpinner.setOnItemSelectedListener(this);
        // Initialize arrays
        allCartItems = new ArrayList<>();
        filteredCartItems = new ArrayList<>();

        // Load cart items
        loadCartItems();

        // Set home button click listener
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(addCart.this, MenuPage.class);
            intent.putExtra("name", getIntent().getStringExtra("name"));
            intent.putExtra("address", getIntent().getStringExtra("address"));
            intent.putExtra("payment", getIntent().getStringExtra("payment"));
            intent.putExtra("imagePath", imagePath);
            intent.putExtra("isValid", true);
            startActivity(intent);
            finish();
        });

        // Handle checkout
        checkoutButton.setOnClickListener(v -> {
            if (filteredCartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            
            Intent intent = new Intent(addCart.this, checkOut.class);
            intent.putExtra("name", getIntent().getStringExtra("name"));
            intent.putExtra("address", getIntent().getStringExtra("address"));
            intent.putExtra("payment", getIntent().getStringExtra("payment"));
            
            // Pass cart information
            double totalPrice = 0;
            int totalQuantity = 0;
            for (CartItem item : filteredCartItems) {
                totalQuantity += item.getQuantity();
                totalPrice += item.getTotalPrice();
            }
            
            intent.putExtra("total_quantity", totalQuantity);
            intent.putExtra("total_price", totalPrice);
            
            // Pass filtered cart items
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<String> itemPrices = new ArrayList<>();
            ArrayList<String> itemImages = new ArrayList<>();
            ArrayList<Integer> itemQuantities = new ArrayList<>();
            ArrayList<Double> itemTotals = new ArrayList<>();
            
            for (CartItem item : filteredCartItems) {
                itemNames.add(item.getMealName());
                itemPrices.add(item.getMealPrice());
                itemImages.add(item.getMealImage());
                itemQuantities.add(item.getQuantity());
                itemTotals.add(item.getTotalPrice());
            }
            
            intent.putStringArrayListExtra("item_names", itemNames);
            intent.putStringArrayListExtra("item_prices", itemPrices);
            intent.putStringArrayListExtra("item_images", itemImages);
            intent.putIntegerArrayListExtra("item_quantities", itemQuantities);
            intent.putExtra("item_totals", itemTotals.stream().mapToDouble(Double::doubleValue).toArray());
            
            startActivity(intent);
        });
    }

    private void loadCartItems() {
        allCartItems.clear();
        SharedPreferences prefs = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        Set<String> itemKeys = prefs.getStringSet("item_keys", new HashSet<>());

        if (itemKeys.isEmpty()) {
            showEmptyCartMessage();
        } else {
            for (String key : itemKeys) {
                String name = prefs.getString(key + "_name", "");
                String price = prefs.getString(key + "_price", "");
                String image = prefs.getString(key + "_image", "");
                int quantity = prefs.getInt(key + "_quantity", 0);
                float itemTotal = prefs.getFloat(key + "_total", 0f);

                if (quantity > 0) {
                    allCartItems.add(new CartItem(name, price, image, quantity, itemTotal));
                }
            }
            
            // Initially show all items
            filterItems(0);
        }
    }

    private void filterItems(int position) {
        filteredCartItems.clear();
        double totalPrice = 0;
        int totalQuantity = 0;

        for (CartItem item : allCartItems) {
            String name = item.getMealName();
            
            boolean shouldAdd = false;
            switch (position) {
                case 0: // All Items
                    shouldAdd = true;
                    break;
                case 1: // Burger
                    shouldAdd = name.toLowerCase().contains("burger");
                    break;
                case 2: // Pizza
                    shouldAdd = name.toLowerCase().contains("pizza");
                    break;
                case 3: // French Fries
                    shouldAdd = name.toLowerCase().contains("fries");
                    break;
                case 4: // Carbonara
                    shouldAdd = name.toLowerCase().contains("carbonara");
                    break;
                case 5: // Steak
                    shouldAdd = name.toLowerCase().contains("steak");
                    break;
            }

            if (shouldAdd) {
                filteredCartItems.add(item);
                totalQuantity += item.getQuantity();
                totalPrice += item.getTotalPrice();
            }
        }

        CartAdapter adapter = new CartAdapter(this, filteredCartItems);
        cartList.setAdapter(adapter);

        totalItems.setText(String.valueOf(totalQuantity));
        totalAmount.setText(String.format("$%.2f", totalPrice));

        // Show message if no items found for the filter
        if (filteredCartItems.isEmpty() && position != 0) {
            Toast.makeText(this, "No items found for " + categories[position], Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmptyCartMessage() {
        TextView emptyCartMsg = new TextView(this);
        emptyCartMsg.setText("Your cart is empty");
        emptyCartMsg.setTextSize(20);
        emptyCartMsg.setTextColor(getResources().getColor(android.R.color.white));
        emptyCartMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        emptyCartMsg.setPadding(0, 50, 0, 0);
        ((ViewGroup) cartList.getParent()).addView(emptyCartMsg);
        cartList.setVisibility(View.GONE);
        checkoutButton.setEnabled(false);
        filterSpinner.setEnabled(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filterItems(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        filterItems(0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuPage.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        intent.putExtra("address", getIntent().getStringExtra("address"));
        intent.putExtra("payment", getIntent().getStringExtra("payment"));
        intent.putExtra("imagePath", getIntent().getStringExtra("imagePath"));
        intent.putExtra("isValid", true);
        startActivity(intent);
        finish();
    }
}