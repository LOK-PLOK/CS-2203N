package com.usc.detablan_day2;

public class CartItem {
    private String mealName;
    private String mealPrice;
    private String mealImage;
    private int quantity;
    private double totalPrice;

    public CartItem(String mealName, String mealPrice, String mealImage, int quantity, double totalPrice) {
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealImage = mealImage;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public String getMealName() { return mealName; }
    public String getMealPrice() { return mealPrice; }
    public String getMealImage() { return mealImage; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
}
