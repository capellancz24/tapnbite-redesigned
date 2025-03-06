package com.example.tapnbite.Class;

import java.util.List;

public class OrderDetail {
    private String orderId; // Unique identifier for the order
    private List<Food> foodItems; // List of food items in the order
    private String status; // e.g., "pending review", "on process", "completed", "canceled"
    private String customerId; // Reference to the customer who placed the order
    private String orderDate; // Date and time of the order

    public OrderDetail(String orderId, List<Food> foodItems, String status, String customerId, String orderDate) {
        this.orderId = orderId;
        this.foodItems = foodItems;
        this.status = status;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Food> getFoodItems() {
        return foodItems;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
