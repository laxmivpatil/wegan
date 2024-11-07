package com.techverse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_details")
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String ifscCode;
    private String accountHolderName;
    private String bankName;
    private String state;
    private String city;
    private String branch;
    private String bankVerificationStatus = "Pending"; // Default value

    // Getters and Setters

    // Constructors
    public BankDetails() {}

    public BankDetails(String accountNumber, String ifscCode, String accountHolderName, 
                       String bankName, String state, String city, String branch) {
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.accountHolderName = accountHolderName;
        this.bankName = bankName;
        this.state = state;
        this.city = city;
        this.branch = branch;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBankVerificationStatus() {
		return bankVerificationStatus;
	}

	public void setBankVerificationStatus(String bankVerificationStatus) {
		this.bankVerificationStatus = bankVerificationStatus;
	}

    
    
    
    
    // Other fields as required
}
