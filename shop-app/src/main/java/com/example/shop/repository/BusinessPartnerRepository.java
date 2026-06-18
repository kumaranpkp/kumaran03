package com.example.shop.repository;

import com.example.shop.model.BusinessPartner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessPartnerRepository extends MongoRepository<BusinessPartner, String> {
}
