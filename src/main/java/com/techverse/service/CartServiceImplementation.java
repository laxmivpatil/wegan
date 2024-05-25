package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.ProductException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.CartRepository;
import com.techverse.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService {
 
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;  
	
	
	@Override
	public Cart createCart(User user) {   
		
		Cart cart=new Cart();
		cart.setUser(user);
		
		
		return cartRepository.save(cart);
	}

	@Override
	public Cart createTempCart() {   
	  Cart cart=new Cart();
		return cartRepository.save(cart);
	}
	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart=cartRepository.findByUserId(userId);
		
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			//int price=req.getQuantity()*product.getDiscountedPrice();
			int price=req.getQuantity()*product.getProduct_price();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem=cartItemService.createCartitem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		
		
		return "Item Add To Cart";
	}
	
	@Override
	public String addGuestCartItem(AddItemRequest req) throws ProductException {
		Cart cart=cartRepository.findById(req.getCartId()).get();
		
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isGuestCartItemExist(cart, product);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			//cartItem.setUserId(userId);
			
			//int price=req.getQuantity()*product.getDiscountedPrice();
			int price=req.getQuantity()*product.getProduct_price();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem=cartItemService.createCartitem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		
		
		return "Item Add To Cart";
	}
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public Cart findUserCart(Long userId) { 
		
		Cart cart=cartRepository.findByUserId(userId);
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice =totalPrice+cartItem.getPrice();
			totalDiscountedPrice =totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem =totalItem+cartItem.getQuantity();
		}
		
		
		cart.setTotalDicountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}
	
	
	@Override
	public Cart findGuestCart(Long cartId) { 
		
		Cart cart=cartRepository.findById(cartId).get();
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice =totalPrice+cartItem.getPrice();
			totalDiscountedPrice =totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem =totalItem+cartItem.getQuantity();
		}
		
		
		cart.setTotalDicountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}
 
}
