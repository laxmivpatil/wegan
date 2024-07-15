package com.techverse.service;

import com.techverse.exception.UserException;
import com.techverse.model.SellerDetails;
import com.techverse.model.User;

public interface SellerDetailsService {
	
	 public User updateSellerDetails(String jwt, SellerDetails sellerDetails) throws UserException;

}
