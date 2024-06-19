package com.techverse.controller;
 

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techverse.model.ContactForm;
import com.techverse.service.ContactFormService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitContactForm( @RequestBody ContactForm contactForm) {
        ContactForm savedContactForm = contactFormService.saveContactForm(contactForm);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Contact form submitted successfully");
        response.put("data", savedContactForm);
        return ResponseEntity.ok(response);
    }
}

