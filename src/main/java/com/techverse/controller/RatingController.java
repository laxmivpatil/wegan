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
import com.techverse.model.Address;
import com.techverse.model.Order;
import com.techverse.model.Rating;
import com.techverse.model.User;
import com.techverse.request.RatingRequest;
import com.techverse.service.RatingService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	
	@Autowired
	private UserService userService;
	

	@Autowired
	private RatingService ratingService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,@RequestHeader("Authorization") String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);
		Rating rating=ratingService.createRating(req, user);
		
		 return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
		
		
	}
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,@RequestHeader("Authorization") String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);
		List<Rating> rating=ratingService.getProductsRating(productId);
		
		 return new ResponseEntity<>(rating,HttpStatus.OK);
		
		
	}
	

}
