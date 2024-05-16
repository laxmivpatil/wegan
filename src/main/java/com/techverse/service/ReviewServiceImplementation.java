package com.techverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.ProductException;
import com.techverse.model.Product;
import com.techverse.model.Rating;
import com.techverse.model.Review;
import com.techverse.model.User;
import com.techverse.repository.ReviewRepository;
import com.techverse.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ProductService productService;




	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product=productService.findProductById(req.getProductId());

		Review review=new Review();

		review.setProduct(product);
		review.setUser(user);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());


		return reviewRepository.save(review);



	}

	@Override
	public List<Review> getProductsReview(Long productId) {

		return reviewRepository.getAllProductsReview(productId);
	}

}
