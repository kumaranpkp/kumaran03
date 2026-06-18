package com.example.shop.controller;

import com.example.shop.data.DataStore;
import com.example.shop.model.BusinessPartner;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.Vendor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AdminController {

    record AddProductRequest(String requestingUsername, String id, String name, String category, String description, double price, int stock, String vendor, String businessPartner, String image) {}
    record AddEntityRequest(String requestingUsername, String id, String name, String contact) {}
    record UpdateStockRequest(String requestingUsername, String itemId, int newStock) {}
    record VerifyUserRequest(String requestingUsername, String usernameToVerify) {}
    record UpdateUserRequest(String requestingUsername, String username, String fullName, String email, String address, String phone, String accountDetails, Boolean verified) {}

    @GetMapping("/api/admin/items")
    public List<Product> getItems() {
        return DataStore.PRODUCTS;
    }

    @GetMapping("/api/admin/users")
    public List<User> getUsers() {
        return DataStore.USERS;
    }

    @GetMapping("/api/admin/vendors")
    public List<Vendor> getVendors() {
        return DataStore.VENDORS;
    }

    @GetMapping("/api/admin/partners")
    public List<BusinessPartner> getPartners() {
        return DataStore.BUSINESS_PARTNERS;
    }

    @PostMapping("/api/admin/add-item")
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody AddProductRequest request) {
        if (!DataStore.isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may perform this action."));
        }
        Optional<Product> existing = DataStore.findProduct(request.id());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Item already exists."));
        }
        Product product = new Product(request.id(), request.name(), request.category(), request.description(), request.price(), request.image() == null || request.image().isBlank() ? "https://via.placeholder.com/120?text=Item" : request.image(), request.stock(), request.vendor(), request.businessPartner());
        DataStore.PRODUCTS.add(product);
        return ResponseEntity.ok(Map.of("success", true, "product", product));
    }

    @PostMapping("/api/admin/update-stock")
    public ResponseEntity<Map<String, Object>> updateStock(@RequestBody UpdateStockRequest request) {
        if (!DataStore.isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may update stock."));
        }
        Optional<Product> existing = DataStore.findProduct(request.itemId());
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Product not found."));
        }
        existing.get().setStock(request.newStock());
        return ResponseEntity.ok(Map.of("success", true, "product", existing.get()));
    }

    @PostMapping("/api/admin/add-vendor")
    public ResponseEntity<Map<String, Object>> addVendor(@RequestBody AddEntityRequest request) {
        if (!DataStore.isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may add vendors."));
        }
        Vendor vendor = new Vendor(request.id(), request.name(), request.contact());
        DataStore.VENDORS.add(vendor);
        return ResponseEntity.ok(Map.of("success", true, "vendor", vendor));
    }

    @PostMapping("/api/admin/add-partner")
    public ResponseEntity<Map<String, Object>> addPartner(@RequestBody AddEntityRequest request) {
        if (!DataStore.isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may add partners."));
        }
        BusinessPartner partner = new BusinessPartner(request.id(), request.name(), request.contact());
        DataStore.BUSINESS_PARTNERS.add(partner);
        return ResponseEntity.ok(Map.of("success", true, "partner", partner));
    }

    @PostMapping("/api/admin/verify-user")
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody VerifyUserRequest request) {
        if (!DataStore.isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may verify users."));
        }
        Optional<User> user = DataStore.findUser(request.usernameToVerify());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "User not found."));
        }
        user.get().setVerified(true);
        return ResponseEntity.ok(Map.of("success", true, "user", user.get()));
    }

    @PostMapping("/api/admin/update-user")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UpdateUserRequest request) {
        if (!DataStore.isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may update user profiles."));
        }
        Optional<User> user = DataStore.findUser(request.username());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "User not found."));
        }
        User found = user.get();
        if (request.fullName() != null) found.setFullName(request.fullName());
        if (request.email() != null) found.setEmail(request.email());
        if (request.address() != null) found.setAddress(request.address());
        if (request.phone() != null) found.setPhone(request.phone());
        if (request.accountDetails() != null) found.setAccountDetails(request.accountDetails());
        if (request.verified() != null) found.setVerified(request.verified());
        return ResponseEntity.ok(Map.of("success", true, "user", found));
    }
}
