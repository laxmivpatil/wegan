package com.techverse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.techverse.exception.ProductException;
import com.techverse.model.Rating;
import com.techverse.model.Review;
import com.techverse.model.User;
import com.techverse.repository.ReviewRepository;
import com.techverse.request.RatingRequest;
import com.techverse.request.ReviewRequest;

public interface ReviewService {
	
	
	
	public Review createReview(ReviewRequest req,User user) throws ProductException ;
	
	public List<Review> getProductsReview(Long productId);
	

}
