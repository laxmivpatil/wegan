package com.techverse.service;

import com.techverse.exception.UserException;
import com.techverse.model.User;

public interface UserService {
	
	public User findUserById(Long userId)throws UserException;
	
	public User findUserProfileByJwt(String jwt)throws UserException;

}
