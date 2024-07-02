package com.techverse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techverse.exception.UserException;
import com.techverse.model.User;
import com.techverse.model.UserPrincipal;
import com.techverse.repository.UserRepository;

@Service
public class CustomUserServiceImplementation implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	
	
	
	 




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   
		User user = userRepository.findByEmail(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with Id: " + username));
	        return UserPrincipal.create(user);
	}
	public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserPrincipal.create(user);
    }
	 
}
