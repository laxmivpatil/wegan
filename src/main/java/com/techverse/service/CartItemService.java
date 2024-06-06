package com.techverse.service;

import java.util.Optional;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;

public interface CartItemService {
	 
	
	public CartItem createCartitem(CartItem cartItem);
	
	public CartItem updateUserCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException ,UserException;

	public CartItem updateGuestCartItem(Long id,CartItem cartItem) throws CartItemException;
	
	public void clearUserCart(Long userId); 
	public Optional<Cart> clearGuestCart(Long cartId); 
	
	public CartItem isCartItemExist(Cart cart,Product product,Long userId);
	
	public CartItem isGuestCartItemExist(Cart cart, Product product);
	
	public void removeUserCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public Cart removeGuestCartItem(Long cartItemId) throws CartItemException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;
	 

}
