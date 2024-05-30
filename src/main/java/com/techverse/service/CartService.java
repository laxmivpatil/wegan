package com.techverse.service;

import com.techverse.exception.ProductException;
import com.techverse.model.Cart;
import com.techverse.model.User;
import com.techverse.request.AddItemRequest;

public interface CartService {
	 
	
	public Cart createCart(User user);
	
	public Cart addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public String addGuestCartItem(AddItemRequest req) throws ProductException; 
	
	public Cart findUserCart(Long userId);
	
	public Cart findGuestCart(Long cartId);
	 
	public Cart createTempCart();

}
