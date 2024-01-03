package com.example.java;

public class Product {
    private String productId;
    private String productName;
    private int quantity;
    private String category;
    private double price;
    private String expiryDate;
    private double totalPrice;

    // Constructors, getters, and setters

    // Example constructor
    public Product(String productId, String productName, int quantity, String category, double price, String expiryDate) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public Product() {
        // Default constructor
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
