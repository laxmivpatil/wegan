package com.techverse.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    private String id; // Use String for a 7-digit ID

    private String url;

    private LocalDate date;

    @JsonIgnore
    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    // Constructor
    public Invoice() {
        this.id = generateId();
    }

    private String generateId() {
        // Generate a random number between 0 and 9999999
        long uniqueId = (long) (Math.random() * 10000000);
        return String.format("%07d", uniqueId);
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

