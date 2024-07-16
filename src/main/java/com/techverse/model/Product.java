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
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String email;
    
    private String title;
    
    private String site;
    
    private int quantity;
    
    private String description;
   
    private String product_tags; 
    
    private String policy;
    
    private String no_of_days;
    
    private Integer product_price;
    
    private double service_charges;
    
    private double seller_price;
     
    private String imageUrl1;
    
    private String imageUrl2;
    
    private String imageUrl3;
    
    private String imageUrl4;
    
    private String imageUrl5;
    
    private String imageUrl6;
   

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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public String getNo_of_days() {
		return no_of_days;
	}


	public void setNo_of_days(String no_of_days) {
		this.no_of_days = no_of_days;
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


	public Product(Long id, Category category, String email, String title, String site, int quantity,
			String description, String product_tags, String policy, String no_of_days, Integer product_price,
			double service_charges, double seller_price, String imageUrl1, String imageUrl2, String imageUrl3,
			String imageUrl4, String imageUrl5, String imageUrl6, List<Rating> ratings, List<Review> reviews,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.category = category;
		this.email = email;
		this.title = title;
		this.site = site;
		this.quantity = quantity;
		this.description = description;
		this.product_tags = product_tags;
		this.policy = policy;
		this.no_of_days = no_of_days;
		this.product_price = product_price;
		this.service_charges = service_charges;
		this.seller_price = seller_price;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.imageUrl4 = imageUrl4;
		this.imageUrl5 = imageUrl5;
		this.imageUrl6 = imageUrl6;
		this.ratings = ratings;
		this.reviews = reviews;
		this.createdAt = createdAt;
	}


	 

	 
    
    
    // Constructors, getters, and setters
}

