package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.OrderException;
import com.techverse.exception.UserException;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;
import com.techverse.response.ApiResponse;
import com.techverse.service.OrderItemService;
import com.techverse.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;
	 
	 
	
	
	  @GetMapping("/orderItems/{orderStatus}")
	    public ResponseEntity<Map<String, Object>> getOrderItemsBySellerAndStatus(
	    		@RequestHeader("Authorization")String jwt,
	             
	            @PathVariable String orderStatus)throws UserException {
		  
		  List<OrderItem> orderItems=orderItemService.getOrderItemsBySellerAndStatus(jwt, orderStatus);
		  
		  Map<String,Object> response = new HashMap<>();
	        response.put("OrderItems", orderItems);
	         response.put("status", true);
	        response.put("message", "order Items get successfully");
	        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	    }
	  
	  
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllOrdersHandler(){
		
		List<Order> orders =orderService.getAllOrders();
		Map<String,Object> response = new HashMap<>();
        response.put("Order", orders);
         response.put("status", true);
        response.put("message", "order get successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
	 
		
	}
	@GetMapping("/pending")
	public ResponseEntity<Map<String, Object>>getPendingOrdersHandler(){
		
		List<Order> orders =orderService.getPendingOrders();
		Map<String,Object> response = new HashMap<>();
        response.put("Order", orders);
         response.put("status", true);
        response.put("message", "order get successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
		
	}
	@GetMapping("/confirmed")
	public ResponseEntity<Map<String, Object>> getConfirmedOrdersHandler(){
		
		List<Order> orders =orderService.getConfirmedOrders();
		Map<String,Object> response = new HashMap<>();
        response.put("Order", orders);
         response.put("status", true);
        response.put("message", "order get successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
		
	}
	@GetMapping("/placed")
	public ResponseEntity<Map<String, Object>> getPlacedOrdersHandler(){
		
		List<Order> orders =orderService.getPlacedOrders();
		Map<String,Object> response = new HashMap<>();
        response.put("Order", orders);
         response.put("status", true);
        response.put("message", "order get successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
		
	}
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException{
		
		Order order=orderService.confirmedOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException{
		
		Order order=orderService.shippedOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> DeliverOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException{
		
		Order order=orderService.deliveredOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException{
		
		Order order=orderService.cancledOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException{
		
		 orderService.deleteOrder(orderId);
		 ApiResponse res=new ApiResponse();
		 res.setMessage("order deleted successfully");
		 res.setStatus(true);
		 
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	 

}
