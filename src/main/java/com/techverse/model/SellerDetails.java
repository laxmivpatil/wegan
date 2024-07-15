package com.techverse.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class SellerDetails {
	
	  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private User user;
	
	//category
	private String companyType;
	
	private String description;
	
	private String businessMobile;
	
	private String gstNo;
	
	private String businessPanCard;
	
	private String pickupAddress;
	
	private String state;
	
	private String pincode;
	
	private String city;
	
	//private String referalCode="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessMobile() {
		return businessMobile;
	}

	public void setBusinessMobile(String businessMobile) {
		this.businessMobile = businessMobile;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getBusinessPanCard() {
		return businessPanCard;
	}

	public void setBusinessPanCard(String businessPanCard) {
		this.businessPanCard = businessPanCard;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

 
	
	
	
	
	
	
}
