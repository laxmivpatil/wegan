package com.techverse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@javax.persistence.Entity
public class Category {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	 private String categoryName;
	    private String blacklogoUrl;
	    private String whitelogoUrl;
	    private String imageUrl;
	    private Long sgst;
	    private Long igst;
	    
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		 
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public String getBlacklogoUrl() {
			return blacklogoUrl;
		}
		public void setBlacklogoUrl(String blacklogoUrl) {
			this.blacklogoUrl = blacklogoUrl;
		}
		public String getWhitelogoUrl() {
			return whitelogoUrl;
		}
		public void setWhitelogoUrl(String whitelogoUrl) {
			this.whitelogoUrl = whitelogoUrl;
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
	
	
	 

	

	 

	 
	
	
}
