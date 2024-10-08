package com.techverse.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
public class OrderItem {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@JsonIgnore
	@ManyToOne
	private Order order;
	
	
	@ManyToOne
	private Product product;
	
	private String size;
	
	private int quantity;
	
	private Integer price;
	
	private Integer discountedPrice;
	
	private Long userId;
	
	private LocalDateTime deliveryDate;
	
	private String orderItemStatus="pending";
	
	
	
	   private String buyerAddressfullName;
	    private String buyerAddressmobile;
	    private String buyerAddresspincode;
	    private String buyerAddresslocality;
	    private String buyerAddress;
	    private String buyerAddresscity;
	    private String buyerAddressstate;
	    private String buyerAddresslandmark;
	    private String buyerAddressalternateMobile;
	    private String reason="";
	    
	    
	    public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getBuyerAddressfullName() {
			return buyerAddressfullName;
		}

		public void setBuyerAddressfullName(String buyerAddressfullName) {
			this.buyerAddressfullName = buyerAddressfullName;
		}

		public String getBuyerAddressmobile() {
			return buyerAddressmobile;
		}

		public void setBuyerAddressmobile(String buyerAddressmobile) {
			this.buyerAddressmobile = buyerAddressmobile;
		}

		public String getBuyerAddresspincode() {
			return buyerAddresspincode;
		}

		public void setBuyerAddresspincode(String buyerAddresspincode) {
			this.buyerAddresspincode = buyerAddresspincode;
		}

		public String getBuyerAddresslocality() {
			return buyerAddresslocality;
		}

		public void setBuyerAddresslocality(String buyerAddresslocality) {
			this.buyerAddresslocality = buyerAddresslocality;
		}

		public String getBuyerAddress() {
			return buyerAddress;
		}

		public void setBuyerAddress(String buyerAddress) {
			this.buyerAddress = buyerAddress;
		}

		public String getBuyerAddresscity() {
			return buyerAddresscity;
		}

		public void setBuyerAddresscity(String buyerAddresscity) {
			this.buyerAddresscity = buyerAddresscity;
		}

		public String getBuyerAddressstate() {
			return buyerAddressstate;
		}

		public void setBuyerAddressstate(String buyerAddressstate) {
			this.buyerAddressstate = buyerAddressstate;
		}

		public String getBuyerAddresslandmark() {
			return buyerAddresslandmark;
		}

		public void setBuyerAddresslandmark(String buyerAddresslandmark) {
			this.buyerAddresslandmark = buyerAddresslandmark;
		}

		public String getBuyerAddressalternateMobile() {
			return buyerAddressalternateMobile;
		}

		public void setBuyerAddressalternateMobile(String buyerAddressalternateMobile) {
			this.buyerAddressalternateMobile = buyerAddressalternateMobile;
		}

		public String getBuyerAddressType() {
			return buyerAddressType;
		}

		public void setBuyerAddressType(String buyerAddressType) {
			this.buyerAddressType = buyerAddressType;
		}

		private String buyerAddressType;
	    
	    
	    
	    
	    
	
	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public OrderItem() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Integer discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public OrderItem(Long id, Order order, Product product, String size, int quantity, Integer price,
			Integer discountedPrice, Long userId, LocalDateTime deliveryDate) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.userId = userId;
		this.deliveryDate = deliveryDate;
	}
	
	
}
