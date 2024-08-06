package com.techverse.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.techverse.config.OrderRequest;
import com.techverse.exception.OrderException;
 
import com.techverse.repository.OrderRepository;
import com.techverse.response.ApiResponse;
import com.techverse.response.PaymentLinkResponse;
import com.techverse.service.OrderService;
import com.techverse.service.RazorpayService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("")
public class PaymentController {
	 
	
	
	 
	
	
	
	/* 
	 @Autowired
	    private RazorpayService razorpayService;

	    @PostMapping("/payment/initiate")
	    public ResponseEntity<String> initiatePayment() {
	        try {
	            String orderId = razorpayService.initiatePayment(1000, "INR", "abcdefghijklmnopqrstuv");
	            return ResponseEntity.ok(orderId);
	        } catch (RazorpayException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Payment initiation failed");
	        }
	    }
	
	    */
	    
	    
	    
	    @Autowired
	    private RazorpayClient razorpayClient;

	    @PostMapping("/createtestorder")
	 
	    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
	    	  try {
	              // Create order
	              JSONObject orderRequestJson = new JSONObject();
	              orderRequestJson.put("amount", orderRequest.getAmount());
	              orderRequestJson.put("currency", orderRequest.getCurrency());
	              orderRequestJson.put("receipt", orderRequest.getReceipt());

	              JSONObject notes = new JSONObject();
	              notes.put("notes_key_1", orderRequest.getNotes());
	              orderRequestJson.put("notes", notes);

	              Order order = razorpayClient.orders.create(orderRequestJson);
	              String orderId = order.get("id").toString();
	              System.out.println("Created Order ID: " + orderId);

	              // Create payment link
	              JSONObject paymentLinkRequestJson = new JSONObject();
	              paymentLinkRequestJson.put("amount", orderRequest.getAmount());
	              paymentLinkRequestJson.put("currency", orderRequest.getCurrency());
	              paymentLinkRequestJson.put("description", "Payment for order: " + orderRequest.getReceipt());
	              paymentLinkRequestJson.put("reference_id", orderId);
	              paymentLinkRequestJson.put("notify", "false"); // Optional: Disable email notifications if needed
	             // paymentLinkRequestJson.put("order_id", orderId); // Directly associate the order ID

	              JSONObject customer = new JSONObject();
	              customer.put("name", "Customer Name"); // Update as necessary
	              customer.put("email", "customer@example.com"); // Update as necessary
	              paymentLinkRequestJson.put("customer", customer);

	              PaymentLink paymentLink1 = razorpayClient.paymentLink.create(paymentLinkRequestJson);
	             
	              String paymentLinkUrl = paymentLink1.get("short_url");
	              System.out.println("Payment Link URL: " + paymentLinkUrl);

	              // Return payment link
	              return ResponseEntity.ok(paymentLinkUrl);

	          } catch (Exception e) {
	              e.printStackTrace();
	              return ResponseEntity.status(500).body("Error creating order or payment link: " + e.getMessage());
	          }
	      
	    }
	    
	    
	    
	    
	    
	    
	/*
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException, RazorpayException{
		
		
		Order order=orderService.findOrderById(orderId);
		System.out.println("order"+order.getId());
		
		try {
			
			RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecret);
			 
			
			JSONObject paymentLinkRequest=new JSONObject();	 
			paymentLinkRequest.put("amount", order.getToatalPrice()*100);
			paymentLinkRequest.put("currency","INR");
			
			
			JSONObject customer=new JSONObject();		
 
			customer.put("name", order.getUser().getName());
			customer.put("email", order.getUser().getEmail());
			paymentLinkRequest.put("customer",customer);
			
			
			JSONObject notify=new JSONObject();
 
			notify.put("sms",true);
			notify.put("email",true);			
			paymentLinkRequest.put("notify",notify);
			
			
			 paymentLinkRequest.put("callback_url","http://localhost:3000/payment/"+orderId);
			 paymentLinkRequest.put("callback_method","get");
			

			 
			
			PaymentLink payment =razorpayClient.paymentLink.create(paymentLinkRequest);

			System.out.println("6dsgfhgsdfgfh"+razorpayClient.toString());
			String paymentLinkId=payment.get("id");
			String paymentLinkUrl=payment.get("short_url");
			
			PaymentLinkResponse res=new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			
			System.out.println("dsgfhgsdfgfh"+payment.get("id"));
			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
			
		}catch (Exception e) {
			
			throw new RazorpayException(e.getMessage());
			 
		}
	}
	@GetMapping("/")
		public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id")String paymentId,
				@RequestParam(name="order_id")Long orderId) throws OrderException,RazorpayException
		{
			Order order=orderService.findOrderById(orderId);
			
			RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecret);
			
			
			try {
				Payment payment=razorpayClient.payments.fetch(paymentId);
				if(payment.get("status").equals("captured")) {
					order.getPaymentDetails().setPaymentId(paymentId);
					order.getPaymentDetails().setStatus("COMPLETED");
					order.setOrderStatus("PLACED");
					orderRepository.save(order);
				}
				ApiResponse res=new ApiResponse();
				 res.setMessage("order placed successfully");
				 res.setStatus(true);
				 
				return new ResponseEntity<>(res,HttpStatus.OK);
				
			}catch (Exception e) {
				throw new RazorpayException(e.getMessage());
			}
			
			
			
		}
		
		 */
		
	} 
	


