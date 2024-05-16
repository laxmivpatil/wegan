package com.techverse.service;

import java.util.List;

import com.techverse.exception.ProductException;
import com.techverse.model.Rating;
import com.techverse.model.User;
import com.techverse.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException ;
	
	public List<Rating> getProductsRating(Long productId);
	
	
	

}
