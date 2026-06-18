package com.example.shop.service;

import com.example.shop.model.BusinessPartner;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.model.Vendor;
import com.example.shop.repository.BusinessPartnerRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final BusinessPartnerRepository businessPartnerRepository;

    public DataInitializer(UserRepository userRepository, ProductRepository productRepository,
                           VendorRepository vendorRepository, BusinessPartnerRepository businessPartnerRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
        this.businessPartnerRepository = businessPartnerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (userRepository.count() == 0) {
                userRepository.save(new User("alice", "password123", "customer", "Alice Cooper", "alice@example.com", "123 Main St, Springfield", "555-1234", "Visa **** 4242", false));
                userRepository.save(new User("bob", "password123", "customer", "Bob Martin", "bob@example.com", "424 Elm St, Springfield", "555-5678", "Mastercard **** 3333", false));
                userRepository.save(new User("manager", "admin123", "manager", "Mia Manager", "manager@example.com", "1 Admin Plaza, Springfield", "555-0000", "Admin Account", true));
            }

            if (productRepository.count() == 0) {
                productRepository.save(new Product("sku-001", "Classic Sneakers", "Shoes", "Comfortable everyday sneakers.", 79.99, "https://via.placeholder.com/120?text=Sneakers", 50, "ShoeVendor Inc", "PartnerA"));
                productRepository.save(new Product("sku-002", "Denim Jacket", "Apparel", "Lightweight jacket for daily wear.", 109.99, "https://via.placeholder.com/120?text=Jacket", 30, "ApparelCo", "PartnerB"));
                productRepository.save(new Product("sku-003", "Wireless Headphones", "Electronics", "Noise-isolating headphones with rich sound.", 149.99, "https://via.placeholder.com/120?text=Headphones", 25, "TechGear Ltd", "PartnerA"));
                productRepository.save(new Product("sku-004", "Travel Backpack", "Accessories", "Durable pack with multiple compartments.", 64.99, "https://via.placeholder.com/120?text=Backpack", 40, "BagMakers Inc", "PartnerC"));
                productRepository.save(new Product("sku-005", "Eco Water Bottle", "Home", "Reusable bottle for staying hydrated.", 22.50, "https://via.placeholder.com/120?text=Bottle", 100, "EcoProducts Ltd", "PartnerB"));
                productRepository.save(new Product("sku-006", "Smart Watch", "Electronics", "Activity tracking with smart notifications.", 199.00, "https://via.placeholder.com/120?text=Watch", 15, "TechGear Ltd", "PartnerA"));
            }

            if (vendorRepository.count() == 0) {
                vendorRepository.save(new Vendor("vendor-001", "ShoeVendor Inc", "contact@shoevend.com"));
                vendorRepository.save(new Vendor("vendor-002", "ApparelCo", "info@apparelco.com"));
                vendorRepository.save(new Vendor("vendor-003", "TechGear Ltd", "sales@techgear.com"));
            }

            if (businessPartnerRepository.count() == 0) {
                businessPartnerRepository.save(new BusinessPartner("partner-001", "PartnerA", "partnerA@biz.com"));
                businessPartnerRepository.save(new BusinessPartner("partner-002", "PartnerB", "partnerB@biz.com"));
                businessPartnerRepository.save(new BusinessPartner("partner-003", "PartnerC", "partnerC@biz.com"));
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not initialize MongoDB data: " + e.getMessage());
            // Allow app to continue even if data initialization fails
        }
    }
}
