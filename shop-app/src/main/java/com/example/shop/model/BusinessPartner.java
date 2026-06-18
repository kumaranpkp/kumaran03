package com.example.shop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business_partners")
public class BusinessPartner {
    @Id
    private String id;
    private String name;
    private String contact;
    private long createdAt;

    public BusinessPartner() {
    }

    public BusinessPartner(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.createdAt = System.currentTimeMillis();
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
