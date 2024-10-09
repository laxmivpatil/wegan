package com.techverse.service;

import java.util.List;

import com.techverse.exception.UserException;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;

public interface OrderItemService {
 
	public OrderItem createOrderItem(OrderItem orderItem);
	
	public List<OrderItem> getOrderItemsBySeller(Long sellerId);

	public List<OrderItem> getOrderItemsBySellerAndStatus(String jwt, String orderStatus)throws UserException;
	public List<OrderItem> getOrderItemsByBuyerAndStatus(String jwt, String orderStatus)throws UserException;
	public List<OrderItem> getOrderItemsByBuyer(String jwt)throws UserException;
	public List<OrderItem> getOrderItemsByOrderId(String jwt,Order order) throws UserException;
	
	 public OrderItem updateOrderItemStatus(Long orderItemId, String newStatus); 
	 public OrderItem updateOrderItemStatusandReason(Long orderItemId, String newStatus,String reason); 
}

