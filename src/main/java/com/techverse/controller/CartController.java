package com.techverse.controller;

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
	public ResponseEntity<Cart> findGuestCartById(@RequestParam Long cartId) throws UserException{
		 
		Cart cart= cartService.findGuestCart(cartId);
		 return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
 	
	@PostMapping("/cart/")
	public ResponseEntity<Cart> createGuestCart() throws UserException{
		 
		Cart cart= cartService.createTempCart();
		 return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException,ProductException{
		User user=userService.findUserProfileByJwt(jwt);
		  cartService.addCartItem(user.getId(), req);
		  ApiResponse res=new ApiResponse();
			 res.setMessage("Item added to cart successfully");
			 res.setStatus(true);
			 return new ResponseEntity<>(res,HttpStatus.OK);
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
