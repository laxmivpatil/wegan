package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import com.razorpay.Transfer;
import com.techverse.exception.OrderException;
import com.techverse.exception.UserException;
import com.techverse.model.Address;
import com.techverse.model.Cart;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;
import com.techverse.model.ShippingAddress;
import com.techverse.model.User;
import com.techverse.repository.ShippingAddressRepository;
import com.techverse.repository.UserRepository;
import com.techverse.response.ApiResponse;
import com.techverse.service.OrderItemService;
import com.techverse.service.OrderService;
import com.techverse.service.RazorpayService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	 
	
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private RazorpayService razorpayService;
	
	
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	
	
	  
	@PostMapping("/addshippingaddress")
    public ResponseEntity<Map<String, Object>> addShippingAddress(@RequestBody ShippingAddress shippingAddress,@RequestHeader("Authorization") String jwt)throws UserException {
        // Retrieve the user by ID (you may adjust this based on your authentication mechanism)
    	User user =userService.findUserProfileByJwt(jwt);
         

        // Set the user for the shipping address
        shippingAddress.setUser(user);
        
        ShippingAddress shippingAddress1=shippingAddressRepository.save(shippingAddress);
        // Add the shipping address to the user's list of shipping addresses
        user.getShippingAddresses().forEach(address ->address.setSetDefaultAddress(false));	
        
        //userRepository.save(user);
        shippingAddress.setSetDefaultAddress(true);
        System.out.println(shippingAddress);
        user.getShippingAddresses().add(shippingAddress1);
        userRepository.save(user);
        
        Map<String,Object> response = new HashMap<>();
        response.put("ShippingAddress", shippingAddress1);

        response.put("status", true);
        response.put("message", "shipping Address added successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
    @GetMapping("/setshippingaddress")
    public ResponseEntity<Map<String, Object>> setShippingAddress(@RequestParam Long addressId,@RequestHeader("Authorization") String jwt)throws UserException {
        // Retrieve the user by ID (you may adjust this based on your authentication mechanism)
    	User user =userService.findUserProfileByJwt(jwt);
         

System.out.println("fkdgjkhdfkjghkdfjhg");
        // Fetch the shipping address by ID
        Optional<ShippingAddress> optionalShippingAddress = shippingAddressRepository.findById(addressId);
        if (optionalShippingAddress.isEmpty()) {
            // Handle case where address ID is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Shipping address not found for ID: " + addressId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ShippingAddress shippingAddress = optionalShippingAddress.get();

        // Set the user for the shipping address
       // shippingAddress.setUser(user);

        // Set default address for the selected address
        shippingAddress.setSetDefaultAddress(true);

        // Set all other addresses of the user to non-default
        user.getShippingAddresses().forEach(address -> {
            if (!address.getId().equals(addressId)) {
                address.setSetDefaultAddress(false);
            }
        });

        // Save the updated shipping address and user
        shippingAddressRepository.save(shippingAddress);
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("ShippingAddress", shippingAddress);
        response.put("status", true);
        response.put("message", "Default shipping address set successfully");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/getshippingaddresses")
    public ResponseEntity<Map<String, Object>> getShippingAddressesByUser(@RequestHeader("Authorization") String jwt)throws UserException {
    	User user =userService.findUserProfileByJwt(jwt) ;
        
 
        List<ShippingAddress> shippingAddresses = user.getShippingAddresses();

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Shipping addresses retrieved successfully");
        response.put("shippingAddresses", shippingAddresses);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/getshippingaddress/default")
    public ResponseEntity<Map<String, Object>> getDefaultShippingAddressesByUser(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt) ;

        // Filter shipping addresses where setDefaultAddress is true
        List<ShippingAddress> shippingAddresses = user.getShippingAddresses().stream()
                .filter(ShippingAddress::isSetDefaultAddress)
                .collect(Collectors.toList());

        if (shippingAddresses.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Default shipping address not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Default shipping address retrieved successfully");
        response.put("shippingAddresses", shippingAddresses);

        return ResponseEntity.ok(response);
    }
     @PutMapping("/editshippingaddress/{addressId}")
    public ResponseEntity<Map<String, Object>> editShippingAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody ShippingAddress updatedAddress,
            @RequestHeader("Authorization") String jwt)throws UserException {
    	User user =userService.findUserProfileByJwt(jwt) ;
        

        
        Optional<ShippingAddress> optionalShippingAddress = shippingAddressRepository.findById(addressId);
        if (optionalShippingAddress.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Shipping address not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ShippingAddress existingAddress = optionalShippingAddress.get();
        // Check if the existing address belongs to the user
        if (!existingAddress.getUser().equals(user)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Shipping address does not belong to the user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // Update the existing address with the new data
     //   existingAddress.setStreet1(updatedAddress.getStreet1());
    //    existingAddress.setStreet2(updatedAddress.getStreet2());
        existingAddress.setFullName(updatedAddress.getFullName());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setPincode(updatedAddress.getPincode());
        existingAddress.setMobile(updatedAddress.getMobile());
        existingAddress.setAlternateMobile(updatedAddress.getAlternateMobile());
        existingAddress.setLandmark(updatedAddress.getLandmark());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setCountry(updatedAddress.getCountry());
        existingAddress.setAddressType(updatedAddress.getAddressType());
        existingAddress.setSetDefaultAddress(updatedAddress.isSetDefaultAddress());

        shippingAddressRepository.save(existingAddress);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Shipping address updated successfully");
        response.put("shippingAddress", existingAddress);

        return ResponseEntity.ok(response);
    }
   
    
    @DeleteMapping("/deleteshippingaddress/{addressId}")
    public ResponseEntity<Map<String, Object>> deleteShippingAddress(
            @PathVariable("addressId") Long addressId,
            @RequestHeader("Authorization") String jwt) throws UserException {
    	User user =userService.findUserProfileByJwt(jwt) ;
        

        
        Optional<ShippingAddress> optionalShippingAddress = shippingAddressRepository.findById(addressId);
        if (optionalShippingAddress.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Shipping address not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ShippingAddress shippingAddress = optionalShippingAddress.get();
        // Check if the shipping address belongs to the user
        if (!shippingAddress.getUser().equals(user)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Shipping address does not belong to the user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        shippingAddressRepository.delete(shippingAddress);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Shipping address deleted successfully");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/getshippingaddress/{addressId}")
    public ResponseEntity<Map<String, Object>> getShippingAddressById(
            @PathVariable("addressId") Long addressId,
            @RequestHeader("Authorization") String jwt) {
        Optional<ShippingAddress> optionalShippingAddress = shippingAddressRepository.findById(addressId);
        if (optionalShippingAddress.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Shipping address not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ShippingAddress shippingAddress = optionalShippingAddress.get();

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Shipping address retrieved successfully");
        response.put("shippingAddress", shippingAddress);

        return ResponseEntity.ok(response);
    }
	 
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/")
	public  ResponseEntity<Map<String, Object>>  createOrder(@RequestHeader("Authorization") String jwt)throws UserException,RazorpayException{
		
		User user =userService.findUserProfileByJwt(jwt);
		 
		ShippingAddress shippingAddress=userService.getDefaultShippingAddress(user);
		
		Order order=orderService.createOrder(user, shippingAddress);
		
		

		Map<String,Object> response = new HashMap<>();
        response.put("Order", order);
        response.put("status", true);
        response.put("message", "order created successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
		 
		
		
	}
	
	
	@GetMapping("/verifypayment/{Id}")
	public  ResponseEntity<Map<String, Object>>  verifyPayment(@RequestHeader("Authorization") String jwt,@PathVariable("Id") String paymentId)throws UserException,RazorpayException{
		
		 
		
		Payment payment= razorpayService.verifyPayment(paymentId);

		Map<String,Object> response = new HashMap<>();
        response.put("Payment",payment.get("status"));
         response.put("status", true);
        response.put("message", "payment get successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
        

		
		
	}
	@PostMapping("/verify/{Id}")
    public ResponseEntity<String> verifyAndTransferPayment(@RequestHeader("Authorization") String jwt,@PathVariable("Id") String paymentId) throws Exception {
        // Verify payment
        Payment payment = razorpayService.verifyPayment(paymentId);
        if (payment == null || !payment.get("status").equals("captured")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed");
        }

        
     // String contactId=razorpayService.createContact();
        
        String contactId="cont_OiOOevhEYe9iq7";
       //String fundAccountId =razorpayService.createFundAccount(contactId);
       String fundAccountId=  "fa_OiPQsXwviMy0gv";
       String payoutId = razorpayService.createPayout(fundAccountId);
        return ResponseEntity.ok("Payment transferred successfully"+payoutId);
    }
	
	 @PostMapping("/refund/{paymentId}")
	    public String refundPayment(@PathVariable String paymentId, @RequestParam int amount) {
	        try {
	            return razorpayService.refundPayment(paymentId, amount);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to process refund", e);
	        }
	    }
	
	
	
	@GetMapping("/user")
	public ResponseEntity<Map<String, Object>> userOrderHistory(@RequestHeader("Authorization") String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt);
		List<Order> order=orderService.usersOrderHistorydesc(user);
		
		Map<String,Object> response = new HashMap<>();
        response.put("Order", order);
         response.put("status", true);
        response.put("message", "order get successfully");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
		 
		
	}
	
	 @GetMapping("/user/orderItems")
	    public ResponseEntity<Map<String, Object>> getOrderItemsByBuyer(
	    		@RequestHeader("Authorization")String jwt
	             
	            )throws UserException {
		  
		  List<OrderItem> orderItems=orderItemService.getOrderItemsByBuyer(jwt);
		   
		  Map<String,Object> response = new HashMap<>();
	        response.put("OrderItems", orderItems);
	         response.put("status", true);
	        response.put("message", "order Items get successfully");
	        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	    }
	 
	 
	  
	@GetMapping("/{Id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt)throws UserException,OrderException{
		
		User user =userService.findUserProfileByJwt(jwt);
		
		Order order=orderService.findOrderById(orderId);
		
		 return new ResponseEntity<>(order,HttpStatus.OK);
		
		
	}
	@DeleteMapping("/{Id}")
	public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt)throws UserException,OrderException{
		
		User user =userService.findUserProfileByJwt(jwt);
		
		 orderService.deleteOrder(orderId);
		
		 ApiResponse res=new ApiResponse();
		 res.setMessage("order deleted successfully");
		 res.setStatus(true);
		 return new ResponseEntity<>(res,HttpStatus.OK);
		
		
	}
	

}


//for perfume
//IGst 18 
//sgst 9

//