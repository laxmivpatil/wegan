package com.techverse.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.request.LoginRequest;
import com.techverse.request.UserRequest;
import com.techverse.response.AuthResponse;
import com.techverse.service.CartService;
import com.techverse.service.CustomUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserRepository userRepository;
	
	private JwtProvider jwtProvider;
	
	private PasswordEncoder passwordEncoder;
	
	private CustomUserServiceImplementation customUserService;
	
	private CartService cartService; 
	
	
	
	public AuthController(UserRepository userRepository,CustomUserServiceImplementation customUserService,
			PasswordEncoder passwordEncoder,JwtProvider jwtProvider, CartService cartService) {
		 
		this.userRepository = userRepository;
		this.passwordEncoder=passwordEncoder;
		this.customUserService=customUserService;
		this.jwtProvider=jwtProvider;
		this.cartService=cartService;
		
	}



	@PostMapping("/signup")
	public ResponseEntity<Map<String,Object>>  createUserHandler(@RequestBody UserRequest user)throws UserException
	{
		Map<String,Object> response = new HashMap<>();
		String email=user.getEmail();
		String password=user.getPassword();
		String name=user.getName();
		System.out.println(password);
		User isEmailExist=userRepository.findByEmail(email);
		 
		if(isEmailExist!=null) {
			 
			throw new UserException("Email is Allready Used with Another Account");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setName(name);
		createdUser.setGender("");
		createdUser.setMobile("");
		createdUser.setCreatedAt(LocalDateTime.now());
		User savedUser=userRepository.save(createdUser);
		 Cart cart= cartService.createCart(savedUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token= jwtProvider.generateToken(authentication);
		
	 
		
		
		
		response.put("jwt", token);
		response.put("status", true);
        response.put("message", "Signup Success");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

		
		 
		
		
		
		
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<Map<String,Object>> loginUserHandler(@RequestBody LoginRequest loginRequest)throws UserException
	{
		 Map<String,Object> response = new HashMap<>();
		   
		String username= loginRequest.getEmail();
		String password=loginRequest.getPassword(); 
		 
		
		Authentication authentication=authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token= jwtProvider.generateToken(authentication);
		
		 
		
		response.put("jwt", token);
		response.put("status", true);
        response.put("message", "login success");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

		
		 
		
		
		
		
	}



	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails=customUserService.loadUserByUsername(username);
		
		if(userDetails==null)
		{
			throw new BadCredentialsException("Invalid Username....");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invalid Password....");
		}
		 
		return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	}

}
