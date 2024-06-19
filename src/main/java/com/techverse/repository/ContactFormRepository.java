package com.techverse.repository;

 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techverse.model.ContactForm;
 

@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
