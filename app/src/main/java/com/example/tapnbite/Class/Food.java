package com.example.tapnbite.Class;

public class Food {
    private String foodId; // Unique identifier for the food item
    private String name;
    private String description;
    private String category;
    private String canteen;
    private String store;
    private String prepTime;
    private double price;
    private String imageUrl;

    public Food(String foodId, String name, String description, String category, String canteen, String store, String prepTime, int price, String imageUrl) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.canteen = canteen; //
        this.store = store; //violation?
        this.prepTime = prepTime;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public String getCategory() {
        return category;
    }

    public String getCanteen() {
        return canteen;
    }

    public String getStore() {
        return store;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFoodPrice(){
        return String.valueOf(price);
    }

}
