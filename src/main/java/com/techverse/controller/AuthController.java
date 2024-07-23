package com.techverse.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.OtpEntity;
import com.techverse.model.Role;
import com.techverse.model.SellerDetails;
import com.techverse.model.User;
import com.techverse.repository.RoleRepository;
import com.techverse.repository.SellerDetailsRepository;
import com.techverse.repository.UserRepository;
import com.techverse.request.LoginRequest;
import com.techverse.request.SellerRequest;
import com.techverse.request.UserRequest;
import com.techverse.response.AuthResponse;
import com.techverse.service.CartService;
import com.techverse.service.CustomUserServiceImplementation;
import com.techverse.service.EmailService;
import com.techverse.service.OtpEntityService;
import com.techverse.service.StorageService;

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
	private StorageService storageService; 
	
	@Autowired
	private EmailService emailService; 
	
	
	@Autowired
	private OtpEntityService otpEntityService; 
	
	
	
	
	@Autowired
	private RoleRepository  roleRepository;
	

	@Autowired
	private SellerDetailsRepository sellerDetailsRepository;
	 @Autowired
	    private AuthenticationManager authenticationManager;

	 
	 
	 
	  @PostMapping("/test")
	    public ResponseEntity<String> registerUser(
	    ) {
		  
		//  String response="'0:[[dhgjdsgjs]] 1:{ 'status':true}'";
		  String response = "0:[\"$@1\",[\"1NE4H-JynNJPO7xdZ8eOw\",null]] 1:{\"status\":200,\"message\":\"success\",\"data\":{\"type\":\"Image\",\"title\":\"\",\"pin\":{\"type\":\"Image\",\"url\":\"https://i.pinimg.com/564x/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"thumbnails\":[\"https://i.pinimg.com/564x/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/736x/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/600x315/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/originals/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/474x/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/236x/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/236x/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/136x136/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\",\"https://i.pinimg.com/60x60/e2/fc/fd/e2fcfdd6677649267eb047b28e3a96c6.jpg\"],\"duration\":\"$undefined\"}}}";

		  Pattern pattern0 = Pattern.compile("0:\\[(.*?)\\] ");
	        Pattern pattern1 = Pattern.compile("1:\\{(.*)\\}");

	        // Find matches for 0:[...]
	        Matcher matcher0 = pattern0.matcher(response);
	        String part0 = "";
	        if (matcher0.find()) {
	            part0 = matcher0.group(0);
	        }

	        // Find matches for 1:{...}
	        Matcher matcher1 = pattern1.matcher(response);
	        String part1 = "";
	        if (matcher1.find()) {
	            part1 = matcher1.group(0);
	        }
	        	part1=part1.substring(2);
	        // Output the two parts
	        System.out.println("Part 0: " + part0);
	        System.out.println("Part 1: " + part1);

		  return new ResponseEntity<>(part1, HttpStatus.OK);
	  }	 
	 
	 @GetMapping("/findemail")
	    public ResponseEntity<Map<String, Object>> getUser(@RequestParam String email,@RequestParam String role) throws UserException {
		  Map<String, Object> response = new HashMap<>();
		  Optional<User> existingUserOpt = userRepository.findByEmail(email);

	        User user;
		  if (existingUserOpt.isPresent()) {
	            // User exists, check if they already have the buyer role
	            user = existingUserOpt.get();
	            
	            
	            //request for buyer and allready registered as a buyer
	            if (user.getRole().equals(role)) {
	                response.put("status", false);
	                response.put("message", "Email already registered as " + role+ ". Please Login.");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            else {
	            	
	            	//Email already registered as seller. Log in as seller or use another email to register as buyer.
	            	    response.put("status", false);
		                response.put("message", "Email already registered as " + user.getRole()+ " Log in as "+user.getRole()+ " or use another email to register as "+role+".");
		                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            // Add the new role to the existing user
	             
	        }
		  String otp=emailService.generateOTP(6);
		  boolean flag=emailService.sendEmail(email, "Wegan Verification", "Your Veerification Otp is "+otp);
		  if(!flag) {
		  response.put("status", false);
	        response.put("message", "email send error");
	        return new ResponseEntity<>(response, HttpStatus.OK);
		  }
		  otpEntityService.save(email, otp);
		  response.put("status", true);
	        response.put("message", "new user and otp send on email ");
	        response.put("otp", otp);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	      
	 }
	 
	 @PostMapping("/verifyEmail")
	    public ResponseEntity<Map<String, Object>> verifyEmail(@RequestParam String email,@RequestParam String otp)   {
		  Map<String, Object> response = new HashMap<>();
		  
		  String otpdb=otpEntityService.findOtpByemail(email);
		  if(otpdb.equals(otp)) {
			  otpEntityService.save(email, "####");
			  
		  response.put("status", true);
	        response.put("message", "verified");
	         
	        return new ResponseEntity<>(response, HttpStatus.OK);
		  }
		  response.put("status", false);
	        response.put("message", "Invalid otp");
	         
	        return new ResponseEntity<>(response, HttpStatus.OK);
		   
	      
	 }
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
	                response.put("message", "Email already registered as " + userRequest.getRole()+ ". Please Login.");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            else {
	            	
	            	//Email already registered as seller. Log in as seller or use another email to register as buyer.
	            	 response.put("status", false);
		                response.put("message", "Email already registered as " + user.getRole()+ " Log in as "+user.getRole()+ " or use another email to register as "+userRequest.getRole()+".");
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
	                        email,
	                        password
	                )
	        );

	        String jwt = jwtProvider.generateToken(authentication);

	        response.put("jwt", jwt);
	        response.put("status", true);
	        response.put("message", "Sign Up Success");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	  
	  @PostMapping("/sellersignup")
	    public ResponseEntity<Map<String, Object>> createSellerHandler(
	    	    @RequestPart String name,
	    	    @RequestPart String email,
	    	    @RequestPart String password,
	    	    @RequestPart String mobileNo,
	    	    @RequestPart String role,
	    	    @RequestPart String companyName,
	    	    @RequestPart String companyType,
	    		
	    	    @RequestPart String description,
	    		
	    	    @RequestPart String businessMobile,
	    		
	    	    @RequestPart String gstNo,
	    		
	    	    @RequestPart String businessPanCard,
	    		
	    	    @RequestPart String pickupAddress,
	    		
	    	    @RequestPart String state,
	    	    @RequestPart String pincode,
	    		
	    	    @RequestPart String city,
	    		 @RequestPart(value="sign",required=false) MultipartFile sign
	    		) throws UserException {
		  
		  
		  
	        Map<String, Object> response = new HashMap<>();
	        /*String email = userRequest.getEmail();
	        String password = userRequest.getPassword();
	        String name = userRequest.getName();
	        String mobile = userRequest.getMobileNo();
	        String role=userRequest.getRole();
	        
	        
	          String companyType=userRequest.getCompanyType();
	    	
	    	  String description=userRequest.getDescription();
	    	
	    	  String businessMobile=userRequest.getBusinessMobile();
	    	
	    	  String gstNo=userRequest.getGstNo();
	    	
	    	  String businessPanCard=userRequest.getBusinessPanCard();
	    	
	    	  String pickupAddress=userRequest.getPickupAddress();
	    	
	    	  String state =userRequest.getState();
	    	
	    	  String pincode=userRequest.getPincode();
	    	
	    	  String city=userRequest.getCity();
	    	
	        */
	        
	        Optional<User> existingUserOpt = userRepository.findByEmail(email);

	        User user;
	        if (existingUserOpt.isPresent()) {
	            // User exists, check if they already have the buyer role
	            user = existingUserOpt.get();
	            
	            
	            //request for buyer and allready registered as a buyer
	            if (user.getRole().equals(role)) {
	                response.put("status", false);
	                response.put("message", "Email already registered as " + role+ ". Please Login.");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            else {
	            	
	            	//Email already registered as seller. Log in as seller or use another email to register as buyer.
	            	 response.put("status", false);
		                response.put("message", "Email already registered as " + user.getRole()+ " Log in as "+user.getRole()+ " or use another email to register as "+role+".");
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
	            user.setMobile(mobileNo);
	            user.setCreatedAt(LocalDateTime.now());
	            user.setRole(role);
	            
	            String path="";
	           // remove comment after final
	 	  /*     if(sign!=null && !sign.isEmpty())
	 	       {
	 	    	   path=storageService.uploadFileOnAzure(sign);
	 	    	    
	 	       }
	 	       
	 	       */
	            path="https://satyaprofilestorage.blob.core.windows.net/wegan/1721047101993_c182e234-e66c-47c1-ac20-11fe0bda3165.jpg?sv=2020-10-02&st=2024-07-15T12%3A33%3A23Z&se=2099-01-01T00%3A00%3A00Z&sr=b&sp=r&sig=oYbtKiU9v059%2FGtce4qiy3rw%2FMxtaBWLXVdfILUINEU%3D";
	            user.setSignUrl(path);
	            User savedUser = userRepository.save(user);
	            SellerDetails s=new SellerDetails();
	            s.setBusinessMobile(businessMobile);
	            s.setBusinessPanCard(businessPanCard);
	            s.setCity(city);
	            s.setCompanyType(companyType);
	            s.setCompanyName(companyName);
	            s.setDescription(description);
	            s.setGstNo(gstNo);
	            s.setPickupAddress(pickupAddress);
	            s.setPincode(pincode);
	            s.setState(state );
	            
	            s.setUser(savedUser);
	             
	            sellerDetailsRepository.save(s);
            
	            
	            
	           /* Role userRole = roleRepository.findByName(userRequest.getRole())
	                    .orElseThrow(() -> new RuntimeException("User Role not set."));
	            user.setRoles(Collections.singleton(userRole));
	            */
	        }

	        
 
	        System.out.println(password);
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        email,
	                        password
	                )
	        );

	        String jwt = jwtProvider.generateToken(authentication);

	        response.put("jwt", jwt);
	        response.put("status", true);
	        response.put("message", "Sign Up Success");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	  
	  
	  
	  
	  @PostMapping("/signin")
	    public ResponseEntity<Map<String, Object>> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException {
	        Map<String, Object> response = new HashMap<>();

	        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
	        if (!userOpt.isPresent()) {
	            throw new UserException("Email not registered. Please sign up as "+loginRequest.getRole()+".");
	        }
	      
	        
	        User user = userOpt.get();
	        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	        if(!user.getRole().equals(loginRequest.getRole())) {
	        	
	        	
	        	//Email not registered as buyer. Log in as seller or register with a different email.
	        	 throw new UserException("Email not registered as  "+loginRequest.getRole()+ ", Log in as "+user.getRole()+" or register with a different email.");
	        }
	        }
	        if(!user.getRole().equals(loginRequest.getRole())) {
	       	 throw new UserException("Email not registered as  "+loginRequest.getRole()+", Log in as "+user.getRole()+" or register with a different email.");
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
	        System.out.println(authentication.toString());
	        String jwt = jwtProvider.generateToken(authentication);

	        response.put("jwt", jwt);
	        response.put("status", true);
	        response.put("message", "Login success");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 

	  
	  
	  //Email not registered. Please sign up as a buyer.
	  //Email already registered as seller. Log in as seller or use another email to register as buyer.
	  //Email not registered as buyer. Log in as seller or register with a different email.
}
