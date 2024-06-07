package com.techverse.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.User;
import com.techverse.response.ApiResponse;
import com.techverse.service.CartItemService;
import com.techverse.service.CartService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("")
public class CartItemController {
	 
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	
	@DeleteMapping("/api/cart_items/{cartItemId}")
	public ResponseEntity<Map<String,Object>> removeUserCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
				
		 Map<String,Object> response = new HashMap<>();
				User user=userService.findUserProfileByJwt(jwt);
				cartItemService.removeUserCartItem(user.getId(), cartItemId);
				
				 
				 
				 
				 response.put("cart", cartService.findUserCart(user.getId()));
					response.put("status", true);
			        response.put("message", "Item deleted from cart successfully");
			        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
				 
				
			}
	@DeleteMapping("/cart_items/{cartItemId}")
	public ResponseEntity<Map<String,Object>> removeGuestCartItem(@PathVariable Long cartItemId
			 )throws UserException,CartItemException{
				
		 Map<String,Object> response = new HashMap<>(); 
				Cart cart=cartItemService.removeGuestCartItem( cartItemId);
				
	 			 response.put("cart", cart);
					response.put("status", true);
			        response.put("message", "Item deleted from cart successfully");
			        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
				 
				
			}
	
	@DeleteMapping("/api/cart_items/clear")
	public ResponseEntity<Map<String, Object>> clearUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
	    Map<String, Object> response = new HashMap<>();
	    User user = userService.findUserProfileByJwt(jwt);
	    cartItemService.clearUserCart(user.getId());

	    response.put("cart", cartService.findUserCart(user.getId()));
	    response.put("status", true);
	    response.put("message", "Cart cleared successfully");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/cart_items/clear")
	public ResponseEntity<Map<String, Object>> clearGuestCart( @RequestParam String cartId) throws UserException {
	    Map<String, Object> response = new HashMap<>();
	    Long parsedCartId = Long.parseLong(cartId);
	    Optional<Cart> cart=cartItemService.clearGuestCart( parsedCartId);

	    response.put("cart", cart);
	    response.put("status", true);
	    response.put("message", "Cart cleared successfully");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/api/cart_items/{cartItemId}")
	public ResponseEntity<Map<String,Object>> updateUserCartItem(@PathVariable Long cartItemId,
			@RequestBody CartItem cartItem,
			@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
				
		 Map<String,Object> response = new HashMap<>();
			
				User user=userService.findUserProfileByJwt(jwt);
				CartItem updatedCartItem=cartItemService.updateUserCartItem(user.getId(),cartItemId, cartItem);
				 response.put("cart", cartService.findUserCart(user.getId()));
					response.put("status", true);
			        response.put("message", "Item Updated from cart successfully");
			        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
				  
				  
				
			}
	@PutMapping("/cart_items/{cartItemId}")
	public ResponseEntity<Map<String,Object>> updateGuestCartItem(@PathVariable Long cartItemId,
			@RequestBody CartItem cartItem)throws UserException,CartItemException{
				
		 Map<String,Object> response = new HashMap<>();
			
			 
				CartItem updatedCartItem=cartItemService.updateGuestCartItem(cartItemId, cartItem);
				 response.put("cart",   cartService.findGuestCart(updatedCartItem.getCart().getId()));
					response.put("status", true);
			        response.put("message", "Item Updated from cart successfully");
			        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
}
	
  
}
