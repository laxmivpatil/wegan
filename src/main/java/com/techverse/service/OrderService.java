package com.techverse.service;

import java.util.List;
import java.util.Optional;

import com.razorpay.RazorpayException;
import com.techverse.exception.OrderException;
 
import com.techverse.model.Order;
import com.techverse.model.ShippingAddress;
import com.techverse.model.User;

public interface OrderService {
	 
	public Order createOrder(User user, ShippingAddress address) throws RazorpayException;
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);
	public List<Order> usersOrderHistorydesc(User user);
	
	public Order getOrderByOrderId(String orderId) throws OrderException;
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId) throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancledOrder(Long orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public List<Order> getConfirmedOrders(); 
	public List<Order> getPlacedOrders() ;
	 
	public List<Order> getPendingOrders();  
	 
	
	
	public void deleteOrder(Long orderId)throws OrderException;
	
	  
	
	
	

}
