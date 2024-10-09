package com.techverse.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayException;
import com.techverse.exception.OrderException;
 
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;
import com.techverse.model.ShippingAddress;
import com.techverse.model.User;
import com.techverse.repository.OrderItemRepository;
import com.techverse.repository.OrderRepository;
import com.techverse.repository.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService{
 

	@Autowired
	private OrderRepository orderRepository;
	
	 
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService1 orderService1;
	
	@Autowired
	private UserRepository userRepository;
	 
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	 
	
	
	

	@Override
	public Order createOrder(User user, ShippingAddress address) throws RazorpayException{
		 
		 		 
		 
		
		
		Cart cart =cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item:cart.getCartItems())
		{
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			 
			orderItem.setBuyerAddressfullName(address.getFullName());
			orderItem.setBuyerAddressmobile(address.getMobile());
			orderItem.setBuyerAddresspincode(address.getPincode());
			orderItem.setBuyerAddresslocality(address.getLocality());
			orderItem.setBuyerAddress(address.getAddress());
			orderItem.setBuyerAddresscity(address.getCity());
			orderItem.setBuyerAddressstate(address.getState());
			orderItem.setBuyerAddresslandmark(address.getLandmark());
			orderItem.setBuyerAddressalternateMobile(address.getAlternateMobile());
			
			
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		 Map<String, String> result=orderService1.createOrder(cart.getTotalpricewithcharges(), "INR","123456",user);
		
	//	String newId="as";
		Order createdOrder=new Order();
		createdOrder.setOrderId(result.get("orderId"));
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setToatalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDicountedPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());
		createdOrder.setTax(cart.getTax());
		createdOrder.setShipping(cart.getShipping());
		createdOrder.setTotalpricewithcharges(cart.getTotalpricewithcharges());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.getPaymentDetails().setPaymentMethod(result.get("paymentLink"));
		createdOrder.setCreatedAt(LocalDateTime.now());
		createdOrder.setOrderItemCount(orderItems.size());
		Order savedOrder=orderRepository.save(createdOrder);
		
		
		for(OrderItem item: orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		 Optional<Order> opt=orderRepository.findById(orderId);
		 if(opt.isPresent())
		 {
		return opt.get();
		 }
		 throw new OrderException("Order not Exist with id "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		//List<Order> orders=orderRepository.getUsersOreders(userId);
		List<Order> orders=orderRepository.getUsersAllOreders(userId);
		
		return orders;
	}

	@Override
	public List<Order> usersOrderHistorydesc(User user) {
		//List<Order> orders=orderRepository.getUsersOreders(userId);
		List<Order> orders=orderRepository.findByUserOrderByCreatedAtDesc(user);
		
		return orders;
	}
	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		
		return orderRepository.save(order);
		
	 
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		 
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		 
		return orderRepository.save(order);
		 
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		 
		return orderRepository.save(order);
		 
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		 
		return orderRepository.save(order);
		 
	}

	@Override
	public List<Order> getAllOrders() {
		 
		return orderRepository.findAll();
	}
	
	@Override
	public List<Order> getConfirmedOrders() {
		 
		return orderRepository.findByOrderStatusOrderByCreatedAtAsc("CONFIRMED");
	}
	@Override
	public List<Order> getPlacedOrders() {
		 
		return orderRepository.findByOrderStatusOrderByCreatedAtAsc("PLACED");
	}
	@Override
	public List<Order> getPendingOrders() {
		 
		return orderRepository.findByOrderStatusOrderByCreatedAtAsc("PENDING");
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException { 
		
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
	}
 
	
	
	
	
	
}
