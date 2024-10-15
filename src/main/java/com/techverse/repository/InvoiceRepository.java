package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.Address;
import com.techverse.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
