package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.UserException;
import com.techverse.model.SellerDetails;
import com.techverse.model.User;
import com.techverse.repository.SellerDetailsRepository;
import com.techverse.repository.UserRepository;

@Service
public class SellerServiceImpplementation implements SellerDetailsService{
	
	 

	    @Autowired
	    private UserService userService;

	    @Autowired
	    private UserRepository userRepo;
	    @Autowired
	    private SellerDetailsRepository sellerDetailsRepository;

	    @Override
	    public User updateSellerDetails(String jwt, SellerDetails sellerDetails) throws UserException {
	        // Find the user using JWT (implement the findUserProfileByJwt method in UserService)
	        User user = userService.findUserProfileByJwt(jwt);

	        if (user == null) {
	            throw new UserException("User not found");
	        }

	        // Set the user in sellerDetails
	        sellerDetails.setUser(user);

	        // Save the seller details
	        sellerDetailsRepository.save(sellerDetails);

	        
	        // Return the updated user
	        return userRepo.save(user);
	    }
	}

