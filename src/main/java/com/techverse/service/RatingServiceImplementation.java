package com.techverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.ProductException;
import com.techverse.model.Product;
import com.techverse.model.Rating;
import com.techverse.model.User;
import com.techverse.repository.RatingRepository;
import com.techverse.request.RatingRequest;


@Service
public class RatingServiceImplementation implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		 Product product=productService.findProductById(req.getProductId());
		 
		 Rating rating=new Rating();
		 
		 rating.setProduct(product);
		 rating.setUser(user);
		 rating.setRating(req.getRating());
		 rating.setCreatedAt(LocalDateTime.now());
		
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		 
		return ratingRepository.getAllProductsRatings(productId);
	}

}
