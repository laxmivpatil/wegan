package com.techverse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.UserException;
import com.techverse.model.OrderItem;
import com.techverse.model.User;
import com.techverse.repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService {
	 
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private  UserService userService;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		 
		return orderItemRepository.save(orderItem);
		 
	}
	
	
	 
	@Override
    public List<OrderItem> getOrderItemsBySeller(Long sellerId) {
        return orderItemRepository.findAllBySellerId(sellerId);
    }
	 
	@Override
	public List<OrderItem> getOrderItemsBySellerAndStatus(String jwt, String orderStatus) throws UserException {
		User user =userService.findUserProfileByJwt(jwt);
		
		
        return orderItemRepository.findAllBySellerIdAndOrderStatus(user.getId(), orderStatus);
    }

}
