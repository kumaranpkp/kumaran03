package com.example.shop.controller;

import com.example.shop.model.BusinessPartner;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.Vendor;
import com.example.shop.repository.BusinessPartnerRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.repository.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final BusinessPartnerRepository businessPartnerRepository;

    public AdminController(UserRepository userRepository, ProductRepository productRepository,
                          VendorRepository vendorRepository, BusinessPartnerRepository businessPartnerRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
        this.businessPartnerRepository = businessPartnerRepository;
    }

    record AddProductRequest(String requestingUsername, String id, String name, String category, String description, double price, int stock, String vendor, String businessPartner, String image) {}
    record AddEntityRequest(String requestingUsername, String id, String name, String contact) {}
    record UpdateStockRequest(String requestingUsername, String itemId, int newStock) {}
    record VerifyUserRequest(String requestingUsername, String usernameToVerify) {}
    record UpdateUserRequest(String requestingUsername, String username, String fullName, String email, String address, String phone, String accountDetails, Boolean verified) {}

    private boolean isAdmin(String username) {
        return userRepository.findByUsername(username)
                .map(user -> "manager".equals(user.getRole())).orElse(false);
    }

    @GetMapping("/api/admin/items")
    public List<Product> getItems() {
        return productRepository.findAll();
    }

    @GetMapping("/api/admin/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/admin/vendors")
    public List<Vendor> getVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/admin/partners")
    public List<BusinessPartner> getPartners() {
        return businessPartnerRepository.findAll();
    }

    @PostMapping("/api/admin/add-item")
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody AddProductRequest request) {
        if (!isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may perform this action."));
        }
        if (productRepository.existsById(request.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Item already exists."));
        }
        Product product = new Product(request.id(), request.name(), request.category(), request.description(), request.price(), request.image() == null || request.image().isBlank() ? "https://via.placeholder.com/120?text=Item" : request.image(), request.stock(), request.vendor(), request.businessPartner());
        Product saved = productRepository.save(product);
        return ResponseEntity.ok(Map.of("success", true, "product", saved));
    }

    @PostMapping("/api/admin/update-stock")
    public ResponseEntity<Map<String, Object>> updateStock(@RequestBody UpdateStockRequest request) {
        if (!isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may update stock."));
        }
        var product = productRepository.findById(request.itemId());
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Product not found."));
        }
        product.get().setStock(request.newStock());
        Product updated = productRepository.save(product.get());
        return ResponseEntity.ok(Map.of("success", true, "product", updated));
    }

    @PostMapping("/api/admin/add-vendor")
    public ResponseEntity<Map<String, Object>> addVendor(@RequestBody AddEntityRequest request) {
        if (!isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may add vendors."));
        }
        Vendor vendor = new Vendor(request.id(), request.name(), request.contact());
        Vendor saved = vendorRepository.save(vendor);
        return ResponseEntity.ok(Map.of("success", true, "vendor", saved));
    }

    @PostMapping("/api/admin/add-partner")
    public ResponseEntity<Map<String, Object>> addPartner(@RequestBody AddEntityRequest request) {
        if (!isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may add partners."));
        }
        BusinessPartner partner = new BusinessPartner(request.id(), request.name(), request.contact());
        BusinessPartner saved = businessPartnerRepository.save(partner);
        return ResponseEntity.ok(Map.of("success", true, "partner", saved));
    }

    @PostMapping("/api/admin/verify-user")
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody VerifyUserRequest request) {
        if (!isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may verify users."));
        }
        var user = userRepository.findByUsername(request.usernameToVerify());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "User not found."));
        }
        user.get().setVerified(true);
        User updated = userRepository.save(user.get());
        return ResponseEntity.ok(Map.of("success", true, "user", updated));
    }

    @PostMapping("/api/admin/update-user")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UpdateUserRequest request) {
        if (!isAdmin(request.requestingUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Only admin users may update user profiles."));
        }
        var user = userRepository.findByUsername(request.username());
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
        User updated = userRepository.save(found);
        return ResponseEntity.ok(Map.of("success", true, "user", updated));
    }
}
