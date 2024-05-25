package com.techverse.service;

import java.util.List;

import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Product;
import com.techverse.model.User;

public interface UserService {
	
	public User findUserById(Long userId)throws UserException;
	
	public User findUserProfileByJwt(String jwt)throws UserException;
	
	 public User addFavoriteProduct(Long userId, Long productId)throws ProductException;
	 
	 public User deleteFavoriteProduct(Long userId, Long productId) throws UserException, ProductException;

	public  List<Product> getFavoriteProducts(Long userId) throws UserException;
	

}
