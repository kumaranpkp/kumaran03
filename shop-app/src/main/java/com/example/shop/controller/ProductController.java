package com.example.shop.controller;

import com.example.shop.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return List.of(
                new Product("sku-001", "Classic Sneakers", "Shoes", "Comfortable everyday sneakers.", 79.99, "https://via.placeholder.com/120?text=Sneakers"),
                new Product("sku-002", "Denim Jacket", "Apparel", "Lightweight jacket for daily wear.", 109.99, "https://via.placeholder.com/120?text=Jacket"),
                new Product("sku-003", "Wireless Headphones", "Electronics", "Noise-isolating headphones with rich sound.", 149.99, "https://via.placeholder.com/120?text=Headphones"),
                new Product("sku-004", "Travel Backpack", "Accessories", "Durable pack with multiple compartments.", 64.99, "https://via.placeholder.com/120?text=Backpack"),
                new Product("sku-005", "Eco Water Bottle", "Home", "Reusable bottle for staying hydrated.", 22.50, "https://via.placeholder.com/120?text=Bottle"),
                new Product("sku-006", "Smart Watch", "Electronics", "Activity tracking with smart notifications.", 199.00, "https://via.placeholder.com/120?text=Watch")
        );
    }
}
