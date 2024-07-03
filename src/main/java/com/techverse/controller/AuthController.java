package com.techverse.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.techverse.model.Role;
import com.techverse.model.User;
import com.techverse.repository.RoleRepository;
import com.techverse.repository.UserRepository;
import com.techverse.request.LoginRequest;
import com.techverse.request.UserRequest;
import com.techverse.response.AuthResponse;
import com.techverse.service.CartService;
import com.techverse.service.CustomUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserServiceImplementation customUserService;
	
	@Autowired
	private CartService cartService; 
	
	@Autowired
	private RoleRepository  roleRepository;
	
	
	 @Autowired
	    private AuthenticationManager authenticationManager;

	
	 

	  @PostMapping("/signup")
	    public ResponseEntity<Map<String, Object>> createUserHandler(@RequestBody UserRequest userRequest) throws UserException {
	        Map<String, Object> response = new HashMap<>();
	        String email = userRequest.getEmail();
	        String password = userRequest.getPassword();
	        String name = userRequest.getName();
	        String mobile = userRequest.getMobileNo();
	        String role=userRequest.getRole();
	        Optional<User> existingUserOpt = userRepository.findByEmail(email);

	        User user;
	        if (existingUserOpt.isPresent()) {
	            // User exists, check if they already have the buyer role
	            user = existingUserOpt.get();
	            
	            
	            //request for buyer and allready registered as a buyer
	            if (user.getRole().equals(role)) {
	                response.put("status", false);
	                response.put("message", "Your email is already register as a " + userRequest.getRole()+ " please login.");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            else {
	            	 response.put("status", false);
		                response.put("message", "Your email is already register as a " + user.getRole()+ " please login as a "+user.getRole()+ " or use another email to registered as a "+role+".");
		                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            // Add the new role to the existing user
	             
	        } else {
	            // New user
	            user = new User();
	            user.setEmail(email);
	            user.setPassword(passwordEncoder.encode(password));
	            user.setName(name);
	            user.setGender("");
	            user.setMobile(mobile);
	            user.setCreatedAt(LocalDateTime.now());
	            user.setRole(role);
	           /* Role userRole = roleRepository.findByName(userRequest.getRole())
	                    .orElseThrow(() -> new RuntimeException("User Role not set."));
	            user.setRoles(Collections.singleton(userRole));
	            */
	        }

	        User savedUser = userRepository.save(user);

	        if (userRequest.getRole().equals("buyer") && !existingUserOpt.isPresent()) {
	            Cart cart = cartService.createCart(savedUser);
	        }

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        user.getEmail(),
	                        userRequest.getPassword()
	                )
	        );

	        String jwt = jwtProvider.generateToken(authentication);

	        response.put("jwt", jwt);
	        response.put("status", true);
	        response.put("message", "Signup Success");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	  @PostMapping("/signin")
	    public ResponseEntity<Map<String, Object>> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException {
	        Map<String, Object> response = new HashMap<>();

	        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
	        if (!userOpt.isPresent()) {
	            throw new UserException("Your email is not registered as a "+loginRequest.getRole()+". please Sign Up first.");
	        }
	      
	        
	        User user = userOpt.get();
	        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	        if(!user.getRole().equals(loginRequest.getRole())) {
	        	 throw new UserException("Your email is not registered as a "+loginRequest.getRole()+", please login as a "+user.getRole()+" or Register as a "+loginRequest.getRole()+" using different email.");
	        }
	        }
	        if(!user.getRole().equals(loginRequest.getRole())) {
	        	 throw new UserException("Your email is not registered as a "+loginRequest.getRole()+", please login as a "+user.getRole()+" or Register as a "+loginRequest.getRole()+" using different email.");
	        }
	        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	            throw new UserException("Invalid password.");
	        }

	       /* Role role = roleRepository.findByName(loginRequest.getRole())
	                .orElseThrow(() -> new UserException("Role not found"));*/
	         

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                )
	        );

	        String jwt = jwtProvider.generateToken(authentication);

	        response.put("jwt", jwt);
	        response.put("status", true);
	        response.put("message", "Login success");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 

}
