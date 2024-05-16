package com.techverse.model;

public class Size {

	private String name;
	private Long quantity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Size() {
		 
	}
	public Size(String name, Long quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
	}
	
	
}
