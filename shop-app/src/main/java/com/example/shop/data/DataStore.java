package com.example.shop.data;

import com.example.shop.model.BusinessPartner;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.Vendor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStore {
    public static final List<User> USERS = new CopyOnWriteArrayList<>(List.of(
            new User("alice", "password123", "customer", "Alice Cooper", "alice@example.com", "123 Main St, Springfield", "555-1234", "Visa **** 4242", false),
            new User("bob", "password123", "customer", "Bob Martin", "bob@example.com", "424 Elm St, Springfield", "555-5678", "Mastercard **** 3333", false),
            new User("manager", "admin123", "manager", "Mia Manager", "manager@example.com", "1 Admin Plaza, Springfield", "555-0000", "Admin Account", true)
    ));

    public static final List<Product> PRODUCTS = new CopyOnWriteArrayList<>(List.of(
            new Product("sku-001", "Classic Sneakers", "Shoes", "Comfortable everyday sneakers.", 79.99, "https://via.placeholder.com/120?text=Sneakers", 15, "FastGear", "Retail Partners"),
            new Product("sku-002", "Denim Jacket", "Apparel", "Lightweight jacket for daily wear.", 109.99, "https://via.placeholder.com/120?text=Jacket", 10, "ThreadWorks", "Apparel Alliance"),
            new Product("sku-003", "Wireless Headphones", "Electronics", "Noise-isolating headphones with rich sound.", 149.99, "https://via.placeholder.com/120?text=Headphones", 8, "SoundWave", "Audio Solutions"),
            new Product("sku-004", "Travel Backpack", "Accessories", "Durable pack with multiple compartments.", 64.99, "https://via.placeholder.com/120?text=Backpack", 20, "CarryKing", "Travel Network"),
            new Product("sku-005", "Eco Water Bottle", "Home", "Reusable bottle for staying hydrated.", 22.50, "https://via.placeholder.com/120?text=Bottle", 45, "GreenFlow", "Eco Partners"),
            new Product("sku-006", "Smart Watch", "Electronics", "Activity tracking with smart notifications.", 199.00, "https://via.placeholder.com/120?text=Watch", 5, "PulseTech", "Wearable Works")
    ));

    public static final List<Vendor> VENDORS = new CopyOnWriteArrayList<>(List.of(
            new Vendor("vendor-001", "FastGear", "gear@fastgear.example.com"),
            new Vendor("vendor-002", "ThreadWorks", "support@threadworks.example.com"),
            new Vendor("vendor-003", "SoundWave", "hello@soundwave.example.com")
    ));

    public static final List<BusinessPartner> BUSINESS_PARTNERS = new CopyOnWriteArrayList<>(List.of(
            new BusinessPartner("partner-001", "Retail Partners", "retail@partners.example.com"),
            new BusinessPartner("partner-002", "Apparel Alliance", "apparel@alliance.example.com"),
            new BusinessPartner("partner-003", "Audio Solutions", "audio@solutions.example.com")
    ));

    public static Optional<User> findUser(String username) {
        return USERS.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public static Optional<Product> findProduct(String productId) {
        return PRODUCTS.stream().filter(product -> product.getId().equals(productId)).findFirst();
    }

    public static boolean addUser(User user) {
        if (findUser(user.getUsername()).isPresent()) {
            return false;
        }
        USERS.add(user);
        return true;
    }

    public static boolean isAdmin(String username) {
        return findUser(username).map(user -> "manager".equals(user.getRole())).orElse(false);
    }
}
