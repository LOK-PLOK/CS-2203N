package com.usc.detablan_day2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class checkOut extends AppCompatActivity {
    private Button confirmOrder, cancelOrder;
    private TextView userName, userAddress, userPayment;
    private TextView totalPrice;
    private LinearLayout itemsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        // Initialize views
        confirmOrder = findViewById(R.id.confirm_order);
        cancelOrder = findViewById(R.id.cancel_order);
        userName = findViewById(R.id.user_name);
        userAddress = findViewById(R.id.user_address);
        userPayment = findViewById(R.id.user_payment);
        totalPrice = findViewById(R.id.total_price);
        itemsContainer = findViewById(R.id.items_container);

        // Get user data from intent
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String payment = getIntent().getStringExtra("payment");

        // Get cart data from intent
        ArrayList<String> itemNames = getIntent().getStringArrayListExtra("item_names");
        ArrayList<String> itemPrices = getIntent().getStringArrayListExtra("item_prices");
        ArrayList<String> itemImages = getIntent().getStringArrayListExtra("item_images");
        ArrayList<Integer> itemQuantities = getIntent().getIntegerArrayListExtra("item_quantities");
        double[] itemTotals = getIntent().getDoubleArrayExtra("item_totals");
        double totalAmount = getIntent().getDoubleExtra("total_price", 0.0);

        // Update user info
        userName.setText("Name: " + name);
        userAddress.setText("Address: " + address);
        userPayment.setText("Payment Method: " + payment);

        // Display cart items
        if (itemNames != null && !itemNames.isEmpty()) {
            for (int i = 0; i < itemNames.size(); i++) {
                View itemView = getLayoutInflater().inflate(R.layout.cart_item, itemsContainer, false);

                // Set item details
                ImageView imageView = itemView.findViewById(R.id.item_image);
                try {
                    imageView.setImageURI(Uri.parse(itemImages.get(i)));
                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                }

                ((TextView) itemView.findViewById(R.id.item_name)).setText(itemNames.get(i));
                ((TextView) itemView.findViewById(R.id.item_price)).setText(itemPrices.get(i));
                ((TextView) itemView.findViewById(R.id.item_quantity)).setText("Quantity: " + itemQuantities.get(i));
                ((TextView) itemView.findViewById(R.id.item_total)).setText(String.format("Total: $%.2f", itemTotals[i]));

                itemsContainer.addView(itemView);
            }
        }

        // Update total amount
        totalPrice.setText(String.format("Total Amount: $%.2f", totalAmount));

        // Handle confirm order
        confirmOrder.setOnClickListener(v -> {
            // Clear cart after successful order
            SharedPreferences prefs = getSharedPreferences("CartPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            // Show confirmation message (optional)
            // Toast.makeText(this, "Order confirmed! Thank you for your purchase.", Toast.LENGTH_LONG).show();

            // Return to menu
            Intent intent = new Intent(checkOut.this, MenuPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
            intent.putExtra("payment", payment);
            startActivity(intent);
            finish();
        });

        // Handle cancel order
        cancelOrder.setOnClickListener(v -> {
            Intent intent = new Intent(checkOut.this, addCart.class);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
            intent.putExtra("payment", payment);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        // Navigate back to cart
        Intent intent = new Intent(this, addCart.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        intent.putExtra("address", getIntent().getStringExtra("address"));
        intent.putExtra("payment", getIntent().getStringExtra("payment"));
        startActivity(intent);
        finish();
    }
}