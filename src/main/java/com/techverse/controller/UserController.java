package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Product;
import com.techverse.model.Review;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository  userRepository;
	
	 @GetMapping("/buyer/home")
	    public ResponseEntity<String> buyerHomePage() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String role = authentication.getAuthorities().iterator().next().getAuthority();

	        if (!role.equals("BUYER")) {
	            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
	        }

	        String welcomeMessage = "Welcome Buyer!";
	        return ResponseEntity.ok(welcomeMessage);
	    }

	    @GetMapping("/seller/home")
	    public ResponseEntity<String> sellerHomePage() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String role = authentication.getAuthorities().iterator().next().getAuthority();

	        if (!role.equals("SELLER")) {
	            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
	        }

	        String welcomeMessage = "Welcome Seller!";
	        return ResponseEntity.ok(welcomeMessage);
	    }
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt);
		 
		
		 return new ResponseEntity<User>(user,HttpStatus.OK);
		
		
	}
	@PutMapping("/update")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt,@RequestBody Map<String,String> map)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt);
		String name= map.get("name");
		 String gender=map.get("gender");
		String mobile= map.get("mobile");
		
		user.setName(name);
		user.setGender(gender);
		user.setMobile(mobile);
		userRepository.save(user);
		
		 return new ResponseEntity<User>(user,HttpStatus.OK);
		
		
	}
	
	@PostMapping("/favorite-products")
    public User addFavoriteProduct(@RequestHeader("Authorization") String jwt, @RequestParam Long productId)throws UserException,ProductException {
		User user =userService.findUserProfileByJwt(jwt);
        return userService.addFavoriteProduct(user.getId(), productId);
    }
	
	@DeleteMapping("/favorite-products")
    public User deleteFavoriteProduct(@RequestHeader("Authorization") String jwt,  @RequestParam Long productId) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        return userService.deleteFavoriteProduct(user.getId(), productId);
    }

    @GetMapping("/favorite-products")
    public Map<String,Object> getFavoriteProducts(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        
        Map<String,Object> response = new HashMap<>();
        
        List<Product> product=userService.getFavoriteProducts(user.getId());
    	response.put("product", product);
		response.put("status", true);
        response.put("message", "product retrived Successfully");
        
        return response;

         
    }

}
