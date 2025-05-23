package com.usc.osservice;

public class Category {
    private int iconResource;
    private String name;

    public Category(int iconResource, String name) {
        this.iconResource = iconResource;
        this.name = name;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getName() {
        return name;
    }
}