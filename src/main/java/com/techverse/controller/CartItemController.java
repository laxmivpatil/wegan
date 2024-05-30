package com.techverse.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.CartItem;
import com.techverse.model.User;
import com.techverse.response.ApiResponse;
import com.techverse.service.CartItemService;
import com.techverse.service.CartService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("api/cart_items")
public class CartItemController {
	 
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<Map<String,Object>> removeCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
				
		 Map<String,Object> response = new HashMap<>();
				User user=userService.findUserProfileByJwt(jwt);
				cartItemService.removeCartItem(user.getId(), cartItemId);
				
				 ApiResponse res=new ApiResponse();
				 res.setMessage("Item deleted from cart successfully");
				 res.setStatus(true);
				 
				 
				 response.put("cart", cartService.findUserCart(user.getId()));
					response.put("status", true);
			        response.put("message", "Item deleted from cart successfully");
			        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
				 
				
			}
	@PutMapping("/cart")
	public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId,
			@RequestBody CartItem cartItem,
			@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
				
				
				User user=userService.findUserProfileByJwt(jwt);
				CartItem updatedCartItem=cartItemService.updateCartItem(user.getId(),cartItemId, cartItem);
				 
				 return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
				
			}
	
	
  
}
