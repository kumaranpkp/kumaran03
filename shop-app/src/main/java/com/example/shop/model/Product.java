package com.example.shop.model;

public class Product {
    private String id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String image;
    private int stock;
    private String vendor;
    private String businessPartner;

    public Product() {
    }

    public Product(String id, String name, String category, String description, double price, String image) {
        this(id, name, category, description, price, image, 0, "", "");
    }

    public Product(String id, String name, String category, String description, double price, String image, int stock, String vendor, String businessPartner) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
        this.stock = stock;
        this.vendor = vendor;
        this.businessPartner = businessPartner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(String businessPartner) {
        this.businessPartner = businessPartner;
    }
}
