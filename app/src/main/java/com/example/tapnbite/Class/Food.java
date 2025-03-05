package com.example.tapnbite.Class;

public class Food {
    private String foodId;
    private String name;
    private String description;
    private int price;
    private int prepTime;
    private String categoryId;
    private String imageUrl;
    private String canteenStaffId;
    private String availability;

    public Food(String foodId, String name, String description, int price, int prepTime, String categoryId, String imageUrl, String canteenStaffId, String availability) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.prepTime = prepTime;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
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

    public String getCategoryId() {
        return categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCanteenStaffId() {
        return canteenStaffId;
    }

    public String getAvailability() {
        return availability;
    }
}