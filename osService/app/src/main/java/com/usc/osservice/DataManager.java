package com.usc.osservice;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final String TAG = "DataManager";
    private static DataManager instance;
    private Context context;

    private List<Category> allCategories;
    private Map<String, List<Service>> categoryServices;
    private List<Cleaner> allCleaners;

    private DataManager(Context context) {
        this.context = context.getApplicationContext();
        this.allCategories = new ArrayList<>();
        this.categoryServices = new HashMap<>();
        this.allCleaners = new ArrayList<>();
        loadData();
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    private void loadData() {
        loadCategories();
        loadCleaners();
    }

    private void loadCategories() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset("service_list.json"));
            JSONArray categoriesArray = jsonObject.getJSONArray("categories");

            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject categoryObject = categoriesArray.getJSONObject(i);
                String categoryName = categoryObject.getString("name");
                String iconName = categoryObject.getString("icon");

                // Get resource ID for icon
                int iconResourceId = getResourceId(iconName, "drawable");

                // Create category
                Category category = new Category(iconResourceId, categoryName);
                allCategories.add(category);

                // Load services for this category
                List<Service> services = new ArrayList<>();
                JSONArray servicesArray = categoryObject.getJSONArray("services");

                for (int j = 0; j < servicesArray.length(); j++) {
                    JSONObject serviceObject = servicesArray.getJSONObject(j);
                    String serviceName = serviceObject.getString("name");
                    String description = serviceObject.getString("description");
                    double price = serviceObject.getDouble("price");

                    // Create service
                    Service service = new Service(iconResourceId, serviceName, description);
                    services.add(service);
                }

                // Store services for this category
                categoryServices.put(categoryName, services);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing service list JSON: " + e.getMessage());
        }
    }

    private void loadCleaners() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset("cleaner_list.json"));
            JSONArray cleanersArray = jsonObject.getJSONArray("cleaners");

            for (int i = 0; i < cleanersArray.length(); i++) {
                JSONObject cleanerObject = cleanersArray.getJSONObject(i);

                String id = cleanerObject.getString("id");
                String name = cleanerObject.getString("name");
                String imageName = cleanerObject.getString("image");
                float rating = (float) cleanerObject.getDouble("rating");
                int age = cleanerObject.getInt("age");
                String schedule = cleanerObject.getString("schedule");
                String address = cleanerObject.getString("address");
                String mobile = cleanerObject.getString("mobile");

                // Get resource ID for image
                int imageResourceId = getResourceId(imageName, "drawable");

                // Create cleaner
                Cleaner cleaner = new Cleaner(imageResourceId, name, rating, age, schedule);

                // Load services for this cleaner
                JSONArray servicesArray = cleanerObject.getJSONArray("services");
                for (int j = 0; j < servicesArray.length(); j++) {
                    String service = servicesArray.getString(j);
                    cleaner.addService(service);
                }

                // Load capabilities
                JSONObject capabilitiesObject = cleanerObject.getJSONObject("capabilities");
                float attitude = (float) capabilitiesObject.getDouble("attitude");
                float quality = (float) capabilitiesObject.getDouble("quality");
                float satisfaction = (float) capabilitiesObject.getDouble("satisfaction");

                // Store cleaner
                allCleaners.add(cleaner);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing cleaner list JSON: " + e.getMessage());
        }
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public List<Service> getServicesForCategory(String categoryName) {
        return categoryServices.getOrDefault(categoryName, new ArrayList<>());
    }

    public List<Cleaner> getAllCleaners() {
        return allCleaners;
    }

    public List<Cleaner> getCleanersForService(String serviceName) {
        List<Cleaner> matchingCleaners = new ArrayList<>();

        for (Cleaner cleaner : allCleaners) {
            if (cleaner.providesService(serviceName)) {
                matchingCleaners.add(cleaner);
            }
        }

        return matchingCleaners;
    }

    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.e(TAG, "Error reading JSON file: " + ex.getMessage());
            return null;
        }
        return json;
    }

    private int getResourceId(String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }
}