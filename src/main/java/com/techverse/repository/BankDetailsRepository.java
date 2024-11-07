package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.BankDetails;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {}


