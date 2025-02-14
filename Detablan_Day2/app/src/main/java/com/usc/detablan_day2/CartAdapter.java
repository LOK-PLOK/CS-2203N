package com.usc.detablan_day2;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private final Activity context;
    private final ArrayList<CartItem> cartItems;

    public CartAdapter(Activity context, ArrayList<CartItem> cartItems) {
        super(context, R.layout.cart_item_layout, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.cart_item_layout, null, true);

        // Find views from cart_item_layout
        ImageView imageView = rowView.findViewById(R.id.item_image);
        TextView nameText = rowView.findViewById(R.id.item_name);
        TextView priceText = rowView.findViewById(R.id.item_price);
        TextView quantityText = rowView.findViewById(R.id.item_quantity);
        TextView totalText = rowView.findViewById(R.id.item_total);

        CartItem item = cartItems.get(position);

        // Set the data to views
        try {
            imageView.setImageURI(Uri.parse(item.getMealImage()));
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }

        nameText.setText(item.getMealName());
        priceText.setText(item.getMealPrice());
        quantityText.setText(String.valueOf(item.getQuantity()));
        totalText.setText(String.format("Total: $%.2f", item.getTotalPrice()));

        return rowView;
    }
}