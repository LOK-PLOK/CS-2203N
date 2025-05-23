package com.usc.osservice;

public class Service {
    private int iconResource;
    private String name;
    private String description;

    public Service(int iconResource, String name, String description) {
        this.iconResource = iconResource;
        this.name = name;
        this.description = description;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}