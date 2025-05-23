package com.usc.osservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cleaner {
    private int imageResource;
    private String name;
    private float rating;
    private int age;
    private String schedule;
    private List<String> services;  // Added services list

    public Cleaner(int imageResource, String name, float rating, int age, String schedule) {
        this.imageResource = imageResource;
        this.name = name;
        this.rating = rating;
        this.age = age;
        this.schedule = schedule;
        this.services = new ArrayList<>();  // Initialize empty services list
    }
    
    // Constructor with services
    public Cleaner(int imageResource, String name, float rating, int age, String schedule, String... services) {
        this.imageResource = imageResource;
        this.name = name;
        this.rating = rating;
        this.age = age;
        this.schedule = schedule;
        this.services = Arrays.asList(services);
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public int getAge() {
        return age;
    }

    public String getSchedule() {
        return schedule;
    }
    
    public List<String> getServices() {
        return services;
    }
    
    public void addService(String service) {
        if (services == null) {
            services = new ArrayList<>();
        }
        services.add(service);
    }
    
    public boolean providesService(String service) {
        return services != null && services.stream()
            .anyMatch(s -> s.toLowerCase().contains(service.toLowerCase()) || 
                          service.toLowerCase().contains(s.toLowerCase()));
    }
}