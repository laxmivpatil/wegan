package com.techverse.service;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;

public interface CartItemService {
	 
	
	public CartItem createCartitem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException ,UserException;
	
	public void clearCart(Long userId); 
	
	public CartItem isCartItemExist(Cart cart,Product product,Long userId);
	
	public CartItem isGuestCartItemExist(Cart cart, Product product);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;
	 

}
