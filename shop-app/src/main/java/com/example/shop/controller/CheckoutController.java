package com.example.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckoutController {

    public static class CheckoutRequest {
        public String username;
        public String paymentMethod;
        public String shippingAddress;
        public double total;
    }

    @PostMapping("/api/checkout")
    public ResponseEntity<Map<String, Object>> checkout(@RequestBody CheckoutRequest request) {
        if (request.username == null || request.username.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "User must be logged in to checkout."));
        }
        if (request.shippingAddress == null || request.shippingAddress.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Shipping address is required."));
        }
        if (request.paymentMethod == null || request.paymentMethod.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Payment method is required."));
        }

        String paymentDetail;
        switch (request.paymentMethod) {
            case "Credit Card" -> paymentDetail = "Payment processed with credit card.";
            case "PayPal" -> paymentDetail = "Payment processed through PayPal.";
            case "COB" -> paymentDetail = "Payment confirmed via Cash on Delivery.";
            default -> paymentDetail = "Payment method processed.";
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Order completed successfully.",
                "paymentDetail", paymentDetail,
                "orderTotal", request.total,
                "shippingAddress", request.shippingAddress
        ));
    }
}
