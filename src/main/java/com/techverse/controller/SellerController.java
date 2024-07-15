package com.techverse.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.techverse.exception.UserException;
import com.techverse.model.SellerDetails;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.service.SellerDetailsService;
import com.techverse.service.UserService;



@RestController
@RequestMapping("/api/seller")
public class SellerController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository  userRepository;
	
	
	 @Autowired
	    private SellerDetailsService sellerDetailsService;

	    @PutMapping("/update")
	    public ResponseEntity<User> updateSellerDetailsHandler(@RequestHeader("Authorization") String jwt, @RequestBody SellerDetails sellerDetails) throws UserException {
	        User updatedUser = sellerDetailsService.updateSellerDetails(jwt, sellerDetails);
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    }
	

}
