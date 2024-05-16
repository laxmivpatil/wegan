package com.techverse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
			return user.get();
		throw new UserException("User Not Found with id  "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
	 
		String email=jwtProvider.getEmailfromToken(jwt);
		
		User user=userRepository.findByEmail(email);
		if(user==null)
		{
			throw new UserException("User Not Found with email  "+email);
		}
		return user;
	}

}
