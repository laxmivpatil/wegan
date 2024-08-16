package com.techverse.model;

 
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
     
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
     
    private String title;
    
    private String site;
    
    private int quantity;
    
    private String description;
   
    private String product_tags; 
    
    private String policy;
    
     
    private Integer product_price;
    
    private double service_charges;
    
    private double seller_price;
     
    private String imageUrl1;
    
    private String imageUrl2;
    
    private String imageUrl3;
    
    private String imageUrl4;
    
    private String imageUrl5;
    
    private String imageUrl6;
    
    
    private Long sgst;
    
    private Long igst;
    
    private double weight;
    
    private Integer base_price;
    
    private Long discount_per;
    
    private boolean discount;
    
    private String discount_type;
    
    private double discount_price;
    
    private double igst_price;
    
    private double sgst_price;
    
    private double final_price;
    
   

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    

    
    private LocalDateTime createdAt;

    
    public Product() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	 

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getProduct_tags() {
		return product_tags;
	}


	public void setProduct_tags(String product_tags) {
		this.product_tags = product_tags;
	}


	public String getPolicy() {
		return policy;
	}


	public void setPolicy(String policy) {
		this.policy = policy;
	}


	 	public Integer getProduct_price() {
		return product_price;
	}


	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}


	public double getService_charges() {
		return service_charges;
	}


	public void setService_charges(double service_charges) {
		this.service_charges = service_charges;
	}


	public double getSeller_price() {
		return seller_price;
	}


	public void setSeller_price(double seller_price) {
		this.seller_price = seller_price;
	}


	public String getImageUrl1() {
		return imageUrl1;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}


	public String getImageUrl2() {
		return imageUrl2;
	}


	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}


	public String getImageUrl3() {
		return imageUrl3;
	}


	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}


	public String getImageUrl4() {
		return imageUrl4;
	}


	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}


	public String getImageUrl5() {
		return imageUrl5;
	}


	public void setImageUrl5(String imageUrl5) {
		this.imageUrl5 = imageUrl5;
	}


	public String getImageUrl6() {
		return imageUrl6;
	}


	public void setImageUrl6(String imageUrl6) {
		this.imageUrl6 = imageUrl6;
	}


	public List<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public Long getSgst() {
		return sgst;
	}


	public void setSgst(Long sgst) {
		this.sgst = sgst;
	}


	public Long getIgst() {
		return igst;
	}


	public void setIgst(Long igst) {
		this.igst = igst;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public Integer getBase_price() {
		return base_price;
	}


	public void setBase_price(Integer base_price) {
		this.base_price = base_price;
	}


	 

	public Long getDiscount_per() {
		return discount_per;
	}


	public void setDiscount_per(Long discount_per) {
		this.discount_per = discount_per;
	}


	public boolean isDiscount() {
		return discount;
	}


	public void setDiscount(boolean discount) {
		this.discount = discount;
	}


	public String getDiscount_type() {
		return discount_type;
	}


	public void setDiscount_type(String discount_type) {
		this.discount_type = discount_type;
	}


	public double getDiscount_price() {
		return discount_price;
	}


	public void setDiscount_price(double discount_price) {
		this.discount_price = discount_price;
	}


	public double getIgst_price() {
		return igst_price;
	}


	public void setIgst_price(double igst_price) {
		this.igst_price = igst_price;
	}


	public double getSgst_price() {
		return sgst_price;
	}


	public void setSgst_price(double sgst_price) {
		this.sgst_price = sgst_price;
	}


	public double getFinal_price() {
		return final_price;
	}


	public void setFinal_price(double final_price) {
		this.final_price = final_price;
	}


	 
	 

	 
    
    
    // Constructors, getters, and setters
}

