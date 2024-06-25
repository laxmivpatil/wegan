package com.techverse.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.User;
import com.techverse.request.AddItemRequest;
import com.techverse.response.ApiResponse;
import com.techverse.service.CartService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("")
public class CartController {
 
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	//for login user
 	@GetMapping("/api/cart/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Cart cart= cartService.findUserCart(user.getId());
		 return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	//for unregisterd user
 	@GetMapping("/cart/")
	public ResponseEntity<Cart> findGuestCartById(@RequestParam String cartId) throws UserException{
		 
		Cart cart= cartService.findGuestCart(Long.parseLong(cartId));
		 return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
 	
	@PostMapping("/cart/")
	public ResponseEntity<Cart> createGuestCart() throws UserException{
		 
		Cart cart= cartService.createTempCart();
		 return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/add")
	public ResponseEntity<Map<String,Object>>  addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException,ProductException{
		 Map<String,Object> response = new HashMap<>();
		User user=userService.findUserProfileByJwt(jwt);
		System.out.println("fhdjgjfhf"+user.getId());
		  Cart cart=cartService.addCartItem(user.getId(), req);
		   
		  Cart updatedCart=cartService.findUserCart(user.getId());
		  
		  response.put("ProductCount", cart.getProductCount());
			response.put("status", true);
	        response.put("message", "Item added to cart successfully");
	        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			 
			 
	}
	@PutMapping("/cart/add")
	public ResponseEntity<ApiResponse> addItemToGuestCart(@RequestBody AddItemRequest req
			 ) throws UserException,ProductException{
	 
		  cartService.addGuestCartItem(req);
		  
		  ApiResponse res=new ApiResponse();
			 res.setMessage("Item added to cart successfully");
			 res.setStatus(true);
			 return new ResponseEntity<>(res,HttpStatus.OK);
	}
	 
	
	
}
