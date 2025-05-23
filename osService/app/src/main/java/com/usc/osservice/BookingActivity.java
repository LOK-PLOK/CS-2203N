package com.usc.osservice;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    private String cleanerName;
    private int cleanerImage;
    private String[] cleanerServices;
    private TextView cleanerNameText, selectedDateTimeText, couponStatusText;
    private ImageView cleanerImageView;
    private EditText addressEditText, couponEditText;
    private Spinner serviceItemsSpinner;
    private Button datePickerButton, timePickerButton, applyCouponButton, confirmBookingButton;
    
    private Calendar selectedDateTime = Calendar.getInstance();
    private boolean isDateSelected = false;
    private boolean isTimeSelected = false;
    
    // Price map for services
    private Map<String, Double> servicePrices = new HashMap<>();
    
    private double serviceFee = 0.00;
    private double discount = 0.00;
    private double total = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Get data from intent
        cleanerName = getIntent().getStringExtra("CLEANER_NAME");
        cleanerImage = getIntent().getIntExtra("CLEANER_IMAGE", R.drawable.johndoe); // Default image
        cleanerServices = getIntent().getStringArrayExtra("CLEANER_SERVICES");
        
        if (cleanerName == null) {
            cleanerName = "Cleaner";
        }

        // Setup service prices
        setupServicePrices();
        
        // Initialize views
        initializeViews();
        
        // Set cleaner information
        cleanerNameText.setText(cleanerName);
        cleanerImageView.setImageResource(cleanerImage);
        
        // Setup service items spinner
        setupServiceItemsSpinner();
        
        // Setup click listeners
        setupClickListeners();
        
        // Update price summary
        updatePriceSummary();
    }

    private void setupServicePrices() {
        // Standard prices for common services
        servicePrices.put("Home Cleaning", 45.00);
        servicePrices.put("Office Cleaning", 65.00);
        servicePrices.put("Deep Cleaning", 85.00);
        servicePrices.put("Move-In/Out Cleaning", 95.00);
        servicePrices.put("Post-Construction Cleaning", 120.00);
        
        servicePrices.put("Refrigerator Cleaning", 40.00);
        servicePrices.put("Oven Cleaning", 35.00);
        servicePrices.put("Washing Machine Maintenance", 50.00);
        servicePrices.put("Dishwasher Cleaning", 30.00);
        servicePrices.put("Microwave Cleaning", 25.00);
        
        servicePrices.put("Daytime Babysitting", 20.00); // per hour
        servicePrices.put("Evening Babysitting", 25.00); // per hour
        servicePrices.put("Weekend Babysitting", 30.00); // per hour
        servicePrices.put("Infant Care", 35.00); // per hour
        servicePrices.put("After-School Care", 22.00); // per hour
        
        servicePrices.put("Basic Exterior Wash", 30.00);
        servicePrices.put("Interior Detailing", 65.00);
        servicePrices.put("Complete Detailing", 120.00);
        servicePrices.put("Premium Wash & Wax", 85.00);
        servicePrices.put("Engine Bay Cleaning", 50.00);
        
        servicePrices.put("AC Maintenance", 70.00);
        servicePrices.put("AC Repair", 120.00);
        servicePrices.put("AC Installation", 250.00);
        servicePrices.put("AC Deep Cleaning", 90.00);
        servicePrices.put("Filter Replacement", 40.00);
        
        // Additional services
        servicePrices.put("Pet Care", 40.00); // per hour
        servicePrices.put("Gardening", 60.00);
        servicePrices.put("Plumbing Service", 80.00);
        servicePrices.put("Electrician", 85.00);
        servicePrices.put("Housekeeping", 55.00);
        servicePrices.put("Painting Service", 75.00);
        servicePrices.put("Laundry Service", 35.00);
    }

    private void initializeViews() {
        cleanerNameText = findViewById(R.id.cleanerNameText);
        cleanerImageView = findViewById(R.id.cleanerImage);
        selectedDateTimeText = findViewById(R.id.selectedDateTimeText);
        couponStatusText = findViewById(R.id.couponStatusText);
        
        addressEditText = findViewById(R.id.addressEditText);
        couponEditText = findViewById(R.id.couponEditText);
        
        serviceItemsSpinner = findViewById(R.id.serviceItemsSpinner);
        
        datePickerButton = findViewById(R.id.datePickerButton);
        timePickerButton = findViewById(R.id.timePickerButton);
        applyCouponButton = findViewById(R.id.applyCouponButton);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);
        
        // Setup back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void setupServiceItemsSpinner() {
        // If we have cleaner services, use those
        String[] serviceItems;
        
        if (cleanerServices != null && cleanerServices.length > 0) {
            // Create items based on the cleaner's actual services with prices
            serviceItems = new String[cleanerServices.length + 1];
            serviceItems[0] = "Select a service";
            
            for (int i = 0; i < cleanerServices.length; i++) {
                String service = cleanerServices[i];
                double price = servicePrices.containsKey(service) ? 
                              servicePrices.get(service) : 50.00; // default price if not found
                
                serviceItems[i + 1] = service + " - $" + String.format("%.2f", price);
            }
        } else {
            // Fallback to default services
            serviceItems = new String[] {
                "Select a service",
                "Regular Cleaning - $50.00",
                "Deep Cleaning - $80.00",
                "Window Cleaning - $35.00",
                "Carpet Cleaning - $60.00",
                "Bathroom Cleaning - $45.00"
            };
        }
        
        // Create an adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            serviceItems
        );
        
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        // Apply the adapter to the spinner
        serviceItemsSpinner.setAdapter(adapter);
        
        // Set listener for service selection to update price
        serviceItemsSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    serviceFee = 0.00;
                } else {
                    String selectedItem = serviceItems[position];
                    // Extract the price from the string (format: "Service - $XX.XX")
                    try {
                        int priceStartIndex = selectedItem.lastIndexOf("$") + 1;
                        String priceStr = selectedItem.substring(priceStartIndex);
                        serviceFee = Double.parseDouble(priceStr);
                    } catch (Exception e) {
                        // If parsing fails, use a default price
                        serviceFee = 50.00;
                    }
                }
                
                updatePriceSummary();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupClickListeners() {
        // Date picker button
        datePickerButton.setOnClickListener(v -> {
            // Get current date
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            
            // Create DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set selected date to our calendar
                    selectedDateTime.set(Calendar.YEAR, selectedYear);
                    selectedDateTime.set(Calendar.MONTH, selectedMonth);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, selectedDay);
                    isDateSelected = true;
                    updateSelectedDateTime();
                },
                year, month, day);
            
            // Set minimum date to today
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            
            // Show dialog
            datePickerDialog.show();
        });
        
        // Time picker button
        timePickerButton.setOnClickListener(v -> {
            // Get current time
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            
            // Create TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    // Set selected time to our calendar
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    selectedDateTime.set(Calendar.MINUTE, selectedMinute);
                    isTimeSelected = true;
                    updateSelectedDateTime();
                },
                hour, minute, false);
            
            // Show dialog
            timePickerDialog.show();
        });
        
        // Apply coupon button
        applyCouponButton.setOnClickListener(v -> {
            String couponCode = couponEditText.getText().toString().trim();
            
            if (couponCode.isEmpty()) {
                Toast.makeText(this, "Please enter a coupon code", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Simple validation - in a real app this would check against a database
            if (couponCode.equals("CLEAN10")) {
                discount = serviceFee * 0.10; // 10% discount
                couponStatusText.setText("10% discount applied!");
                couponStatusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else if (couponCode.equals("CLEAN20")) {
                discount = serviceFee * 0.20; // 20% discount
                couponStatusText.setText("20% discount applied!");
                couponStatusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else if (couponCode.equals("WELCOME")) {
                discount = serviceFee * 0.15; // 15% discount
                couponStatusText.setText("15% welcome discount applied!");
                couponStatusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else if (couponCode.equals("FIRSTBOOK")) {
                discount = 10.00; // $10 off
                couponStatusText.setText("$10.00 off your first booking!");
                couponStatusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                discount = 0;
                couponStatusText.setText("Invalid coupon code");
                couponStatusText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            
            couponStatusText.setVisibility(View.VISIBLE);
            updatePriceSummary();
        });
        
        // Confirm booking button
        confirmBookingButton.setOnClickListener(v -> {
            if (validateBookingDetails()) {
                showBookingConfirmationDialog();
            }
        });
    }

    private void updateSelectedDateTime() {
        if (isDateSelected && isTimeSelected) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy 'at' h:mm a", Locale.getDefault());
            selectedDateTimeText.setText(sdf.format(selectedDateTime.getTime()));
        } else if (isDateSelected) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
            selectedDateTimeText.setText(sdf.format(selectedDateTime.getTime()) + " (Time not selected)");
        } else if (isTimeSelected) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
            selectedDateTimeText.setText("(Date not selected) at " + sdf.format(selectedDateTime.getTime()));
        }
    }

    private void updatePriceSummary() {
        total = serviceFee - discount;
        if (total < 0) total = 0; // Ensure total doesn't go negative
        
        // Update text views
        TextView serviceFeeText = findViewById(R.id.serviceFeeText);
        TextView discountText = findViewById(R.id.discountText);
        TextView totalPriceText = findViewById(R.id.totalPriceText);
        
        serviceFeeText.setText(String.format("$%.2f", serviceFee));
        discountText.setText(String.format("$%.2f", discount));
        totalPriceText.setText(String.format("$%.2f", total));
    }

    private boolean validateBookingDetails() {
        // Validate address
        if (addressEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter a service address", Toast.LENGTH_SHORT).show();
            addressEditText.requestFocus();
            return false;
        }
        
        // Validate service item selection
        if (serviceItemsSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a service", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        // Validate date and time
        if (!isDateSelected || !isTimeSelected) {
            Toast.makeText(this, "Please select both date and time", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }

    private void showBookingConfirmationDialog() {
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Booking");
        
        // Create the message
        String message = "Cleaner: " + cleanerName + "\n" +
                "Service: " + serviceItemsSpinner.getSelectedItem().toString() + "\n" +
                "Date/Time: " + selectedDateTimeText.getText().toString() + "\n" +
                "Address: " + addressEditText.getText().toString() + "\n" +
                "Total Price: $" + String.format("%.2f", total);
        
        builder.setMessage(message);
        
        // Add buttons
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            // In a real app, this would save the booking to the backend
            Toast.makeText(BookingActivity.this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
            finish();
        });
        
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        
        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}