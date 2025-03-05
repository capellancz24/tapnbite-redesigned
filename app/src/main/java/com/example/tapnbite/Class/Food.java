package com.example.tapnbite.Class;

public class Food {
    private String foodId;
    private String name;
    private String description;
    private int price;
    private int prepTime;
    private String store;
    private String imageUrl;
    private String canteenNo;

    private String categoryId;

    private String canteenStaffId;
    private String availability;

    public Food(String foodId, String name, String description, int price, int prepTime, String imageUrl, String categoryId, String canteenStaffId, String availability) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.prepTime = prepTime;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.canteenStaffId = canteenStaffId;
        this.availability = availability;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public String getStore() {
        return store;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCanteeNo() {
        return canteenNo;
    }

    public String getAvailability() {
        return availability;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCanteenStaffId() {
        return canteenStaffId;
    }
}