package com.techverse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.CartItemRepository;
import com.techverse.repository.CartRepository;


@Service
public class CartItemServiceImplementation implements CartItemService{
	 
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepository cartRepository;
	 

	@Override
	public CartItem createCartitem(CartItem cartItem) {
		 
		//cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getProduct_price()*cartItem.getQuantity());
		//cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		cartItem.setDiscountedPrice(0*cartItem.getQuantity());
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
		
		
		
	}

	@Override
	public CartItem updateUserCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		 
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getProduct_price());
			//item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
			item.setDiscountedPrice(0*item.getQuantity());
			
		}
		
		
		return cartItemRepository.save(item);
	}
	
	@Override
	public CartItem updateGuestCartItem(Long id, CartItem cartItem) throws CartItemException {
		 
		CartItem item=findCartItemById(id); 
		
		 
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getProduct_price());
			//item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
			item.setDiscountedPrice(0*item.getQuantity());
			
		 
		
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product,  Long userId) {
		 
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, userId);
		
		
		
		return cartItem;
	}
	@Override
	public CartItem isGuestCartItemExist(Cart cart, Product product) {
		 
		CartItem cartItem=cartItemRepository.isGuestCartItemExist(cart, product);
		
		
		
		return cartItem;
	}

	@Override
	public void removeUserCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		
		CartItem cartItem=findCartItemById(cartItemId);
		User user=userService.findUserById(cartItem.getUserId());
		
		User reqUser=userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}
		else {
			throw new UserException("you cant remove another users item");
		}
		 
		
	}

	@Override
	public Cart removeGuestCartItem( Long cartItemId) throws CartItemException {
		
		CartItem cartItem=findCartItemById(cartItemId);
		 
			cartItemRepository.deleteById(cartItemId);
		 
		 return cartItem.getCart();
		
	}
	@Override
	public void clearUserCart(Long userId) {
	    List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
	    
	     
	    cartItemRepository.deleteAll(cartItems);
	}
	@Override
	public Optional<Cart> clearGuestCart(Long cartId) {
		Optional<Cart> cart=cartRepository.findById(cartId);
	    List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
	    
	  
	    cartItemRepository.deleteAll(cartItems);
	    if(cart.isPresent()) {
	    	cart.get().setTotalPrice(0.0);
	        cart.get().setTotalItem(0);
	        cart.get().setProductCount(0);
	        cart.get().setTotalDicountedPrice(0);
	        cart.get().setDiscounte(0);
	        cart.get().setTax(0.0);
	        cart.get().setShipping(0.0);
	        cart.get().setTotalpricewithcharges(0.0);
	    	
	     cartRepository.save(cart.get());
	    }
	    return cart;
	}
	
	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		//find
		Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
		
		if(cartItem.isPresent()) {
			return cartItem.get();
		}
		
		throw new CartItemException("cart item not found with id "+cartItemId);
	}
}
