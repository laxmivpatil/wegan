package com.techverse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.CartItemRepository;
import com.techverse.repository.CartRepository;
import com.techverse.repository.UserRepository;


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
	
	
	 @Override
	    public Cart mergeCarts(Long userId, Long cartId) throws UserException, CartItemException {
	        // Find user's cart
	        Cart userCart = cartRepository.findByUserId(userId);
	        if (userCart == null) {
	            // If user's cart doesn't exist, create a new one
	            userCart = new Cart();
	            userCart.setUser(userService.findUserById(userId));
	            userCart = cartRepository.save(userCart);
	        }
	        
	        // Find the given cart by cartId
	        Optional<Cart> optionalCart = cartRepository.findById(cartId);
	        if (!optionalCart.isPresent()) {
	            throw new CartItemException("Cart not found with id " + cartId);
	        }
	        Cart givenCart = optionalCart.get();  //755
	        
	        // Merge the items from givenCart into userCart
	        for (CartItem givenCartItem : givenCart.getCartItems()) {
	            CartItem existingCartItem = cartItemRepository.isCartItemExist1(givenCartItem.getProduct(), userId);
	            
	           
	            if (existingCartItem != null) {
	            	 System.out.println("hjgjhgjhg"+existingCartItem.getId());
	                // If item exists in userCart, update the quantity and price
	                existingCartItem.setQuantity(existingCartItem.getQuantity() + givenCartItem.getQuantity());
	                existingCartItem.setPrice(existingCartItem.getQuantity() * existingCartItem.getProduct().getProduct_price());
	                existingCartItem.setDiscountedPrice(0 * existingCartItem.getQuantity()); // Adjust as needed
	                cartItemRepository.save(existingCartItem);
	            } else {
	                // If item doesn't exist in userCart, add it
	                CartItem newCartItem = new CartItem();
	                newCartItem.setCart(userCart);
	                newCartItem.setUserId(userId);
	                newCartItem.setProduct(givenCartItem.getProduct());
	                newCartItem.setQuantity(givenCartItem.getQuantity());
	                newCartItem.setPrice(givenCartItem.getQuantity() * givenCartItem.getProduct().getProduct_price());
	                newCartItem.setDiscountedPrice(0 * givenCartItem.getQuantity()); // Adjust as needed
	                
	                userCart.getCartItems().add(newCartItem);
	                cartItemRepository.save(newCartItem);
	            }
	        }
	        
	        
	    	 
			int productCount=0;
			int totalPrice=0;
			int totalDiscountedPrice=0;
			int totalItem=0;
			double tax=0.0;
			double shipping=0.0;
			double totalwithcharges=0.0;
			
			for(CartItem cartItem:userCart.getCartItems()) {
				totalPrice =totalPrice+cartItem.getPrice();
				totalDiscountedPrice =totalDiscountedPrice+cartItem.getDiscountedPrice();
				totalItem =totalItem+cartItem.getQuantity();
				++productCount;
			}
			
			
			userCart.setTotalDicountedPrice(totalDiscountedPrice);
			userCart.setTotalItem(totalItem);
			userCart.setTotalPrice(totalPrice);
			userCart.setDiscounte(totalPrice-totalDiscountedPrice);
			userCart.setProductCount(productCount);
			
			tax=totalPrice*5/100;
			userCart.setTax(tax);
			
			shipping=totalPrice*7/100;
			userCart.setShipping(shipping);
			
			totalwithcharges=totalPrice+tax+shipping;
			userCart.setTotalpricewithcharges(totalwithcharges);
			
			
			 cartRepository.delete(givenCart);
			return cartRepository.save(userCart);
	        
	        
	        
	        
	       /* 
	        // Recalculate totals for userCart
	        Long totalPrice = 0L;
	        Long totalDiscountedPrice = 0L;
	        int totalItem = 0;
	        
	        for (CartItem cartItem : userCart.getCartItems()) {
	            totalPrice += cartItem.getPrice();
	            totalDiscountedPrice += cartItem.getDiscountedPrice();
	            totalItem += cartItem.getQuantity();
	        }
	        
	        userCart.setTotalDiscountedPrice(totalDiscountedPrice);
	        userCart.setTotalItem(totalItem);
	        userCart.setTotalPrice(totalPrice);
	        userCart.setDiscount(totalPrice - totalDiscountedPrice);
	        
	        return cartRepository.save(userCart);
	        */
	    }

	 
	
	
	
	
	
	
}
