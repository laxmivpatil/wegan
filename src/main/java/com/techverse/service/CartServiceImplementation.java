package com.techverse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.CartItemException;
import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
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
	

	@Autowired
	private UserService userService;
	
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
	public Cart addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart=cartRepository.findByUserId(userId);
		 System.out.println("hfjkhjhjhgjghjghjgh"+cart.getId());
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, userId);
		
		if(isPresent==null) {
			System.out.println("hfjkhjhjhgjghjghjgh"+cart.getId());
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
			System.out.println(cart.getCartItems().size());
			cart.getCartItems().add(createdCartItem);
			cart.setProductCount(cart.getProductCount()+1);
			 
		}
		 cartRepository.save(cart);
		return cart;
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
		 findGuestCart(cart.getId());
		
		return "Item Add To Cart";
	}
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public Cart findUserCart(Long userId) { 
		
		Cart cart=cartRepository.findByUserId(userId);
		int productCount=0;
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		double tax=0.0;
		double shipping=0.0;
		double totalwithcharges=0.0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice =totalPrice+cartItem.getPrice();
			totalDiscountedPrice =totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem =totalItem+cartItem.getQuantity();
			++productCount;
		}
		
		
		cart.setTotalDicountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		cart.setProductCount(productCount);
		
		tax=totalPrice*5/100;
		cart.setTax(tax);
		
		shipping=totalPrice*7/100;
		cart.setShipping(shipping);
		
		totalwithcharges=totalPrice+tax+shipping;
		cart.setTotalpricewithcharges(totalwithcharges);
		
		return cartRepository.save(cart);
	}
	
	
	@Override
	public Cart findGuestCart(Long cartId) { 
		
		Cart cart=cartRepository.findById(cartId).get();
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		double tax=0.0;
		double shipping=0.0;
		double totalwithcharges=0.0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice =totalPrice+cartItem.getPrice();
			totalDiscountedPrice =totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem =totalItem+cartItem.getQuantity();
		}
		
		
		cart.setTotalDicountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		tax=totalPrice*5/100;
		cart.setTax(tax);
		
		shipping=totalPrice*7/100;
		cart.setShipping(shipping);
		
		totalwithcharges=totalPrice+tax+shipping;
		cart.setTotalpricewithcharges(totalwithcharges);
		
		return cartRepository.save(cart);
	}
	
	
	
}
