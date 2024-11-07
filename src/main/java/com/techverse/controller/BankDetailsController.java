package com.techverse.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.UserException;
import com.techverse.model.BankDetails;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.service.BankDetailsService;
import com.techverse.service.UserService;



@RestController
@RequestMapping("/admin/bankdetails")
public class BankDetailsController {
	 @Autowired
	    private BankDetailsService bankDetailsService;
	 @Autowired
	    private UserRepository userRepository;
	 
		@Autowired
		private UserService userService;
		
	    @PostMapping("")
	    public ResponseEntity<?> addOrUpdateBankDetails(

		        @RequestHeader("Authorization") String jwt,
	            @RequestBody BankDetails bankDetails) throws UserException { 
	    	
	    	User user=userService.findUserProfileByJwt(jwt);
	    	Map<String,Object> response = new HashMap<>();
			 
			

	         
	        user.setBankDetails(bankDetailsService.saveBankDetails(bankDetails));
	        userRepository.save(user);
	        
	        
	        response.put("bankDetails", bankDetails);
			response.put("status", true);
		    response.put("message", "bankdetails saved Successfully");
		    return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	         

	         
	    }
	    
	    
	    @GetMapping("")
	    public ResponseEntity<?> getBankDetails( @RequestHeader("Authorization") String jwt)throws UserException {
	    	User user=userService.findUserProfileByJwt(jwt);
       BankDetails bankDetails = user.getBankDetails();
         
	        Map<String,Object> response = new HashMap<>();
			 
			response.put("bankDetails", bankDetails);
			response.put("status", true);
		    response.put("message", "bankdetails get Successfully");
		    return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	    }
}
