package com.techverse.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.UserException;
import com.techverse.model.Order;
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
		
		if(orderStatus.equals("accept")) {
			 return orderItemRepository.findAllBySellerIdAndOrderStatus(user.getId(), orderStatus,"request for cancellation reject");
		}
		if(orderStatus.equals("cancelled")) {
			 return orderItemRepository.findAllBySellerIdAndOrderStatus(user.getId(), orderStatus,"request for cancellation accept");
		}
		if(orderStatus.equals("return")) {
			 return orderItemRepository.findAllBySellerIdAndOrderStatus(user.getId(), orderStatus,"request for return accept");
		}
		if(orderStatus.equals("delivered")) {
			 return orderItemRepository.findAllBySellerIdAndOrderStatus(user.getId(), orderStatus,"request for return reject");
		}
        return orderItemRepository.findAllBySellerIdAndOrderStatus(user.getId(), orderStatus);
        
        
        
    }
	@Override
	public List<OrderItem> getOrderItemsByBuyerAndStatus(String jwt, String orderStatus) throws UserException {
		User user =userService.findUserProfileByJwt(jwt);
		
		
        return orderItemRepository.findAllByBuyerIdAndOrderStatus(user.getId(), orderStatus);
    }
	@Override
	public List<OrderItem> getOrderItemsByBuyer(String jwt) throws UserException {
		User user =userService.findUserProfileByJwt(jwt);
		
		
        return orderItemRepository.findAllByBuyerId(user.getId());
    }
	@Override
	public List<OrderItem> getOrderItemsByOrderId(String jwt,Order order) throws UserException {
		User user =userService.findUserProfileByJwt(jwt);
		
		
        return orderItemRepository.findByOrder(order);
    }
	// Method to update the status of an OrderItem by its ID
	@Override
    public OrderItem updateOrderItemStatus(Long orderItemId, String newStatus) {
        // Fetch the OrderItem from the database
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);

        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            // Update the status
            orderItem.setOrderItemStatus(newStatus);
            
            if(newStatus.equals("accept")) {
            	
            	
            }
            
            
            
            LocalDate currentDate = LocalDate.now();
            orderItem.setAccepttortddate(currentDate);;
            // Save the updated OrderItem back to the database
            return orderItemRepository.save(orderItem);
        } else {
            throw new RuntimeException("OrderItem not found with id " + orderItemId);
        }
    }
	@Override
    public OrderItem updateOrderItemStatusandReason(Long orderItemId, String newStatus,String reason) {
        // Fetch the OrderItem from the database
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);

        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            // Update the status
            orderItem.setOrderItemStatus(newStatus);
            LocalDate currentDate = LocalDate.now();
            orderItem.setRejectdate(currentDate);
            orderItem.setReason(reason);   
            // Save the updated OrderItem back to the database
            return orderItemRepository.save(orderItem);
        } else {
            throw new RuntimeException("OrderItem not found with id " + orderItemId);
        }
    }
	@Override
    public OrderItem requstCancelOrderItemStatusandReason(Long orderItemId, String newStatus,String reason) {
        // Fetch the OrderItem from the database
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);

        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            // Update the status
            orderItem.setOrderItemStatus(newStatus);
            LocalDate currentDate = LocalDate.now();
            orderItem.setRequestcancellation(currentDate);
            orderItem.setReason(reason);   
            // Save the updated OrderItem back to the database
            return orderItemRepository.save(orderItem);
        } else {
            throw new RuntimeException("OrderItem not found with id " + orderItemId);
        }
    }
	
	
	
	//by buyer
	
	@Override
    public OrderItem requstreturnOrderItemStatusandReason(Long orderItemId, String newStatus,String reason) {
        // Fetch the OrderItem from the database
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);

        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            // Update the status
            orderItem.setOrderItemStatus(newStatus);
            //LocalDate currentDate = LocalDate.now();
             
            orderItem.setReason(reason);   
            // Save the updated OrderItem back to the database
            return orderItemRepository.save(orderItem);
        } else {
            throw new RuntimeException("OrderItem not found with id " + orderItemId);
        }
    }
}


//seller name
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
