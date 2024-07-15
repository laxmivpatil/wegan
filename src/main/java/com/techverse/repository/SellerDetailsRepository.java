package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.SellerDetails;

public interface SellerDetailsRepository extends JpaRepository<SellerDetails, Long> {
    // Custom query methods (if needed) can be defined here
}