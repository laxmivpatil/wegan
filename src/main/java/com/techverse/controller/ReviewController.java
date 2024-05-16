package com.techverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Rating;
import com.techverse.model.Review;
import com.techverse.model.User;
import com.techverse.request.RatingRequest;
import com.techverse.request.ReviewRequest;
import com.techverse.service.RatingService;
import com.techverse.service.ReviewService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {
	
	@Autowired
	private UserService userService;
	

	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);
		Review review=reviewService.createReview(req, user);
		
		 return new ResponseEntity<Review>(review,HttpStatus.CREATED);
		
		
	}
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId,@RequestHeader("Authorization") String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);
		List<Review> review=reviewService.getProductsReview(productId);
		
		 return new ResponseEntity<>(review,HttpStatus.OK);
		
		
	}
	

}
