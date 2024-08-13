package com.techverse.service;
import com.razorpay.Order;
import com.razorpay.Payment; 
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Transfer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {
	
	
	  private static final String BASE_URL = "https://api.razorpay.com/v1/";
	   
	public String apiKey="rzp_test_wIkA36LzCEN5sz";
	 
	public String apiSecret="q66erZ3vUMDufjJyZi3iA7Qy";
	 
	public RazorpayClient razorpayClient;
	
	 
	 public  RazorpayService()throws RazorpayException 
	 {
		 this.razorpayClient = new RazorpayClient(apiKey, apiSecret);
		// Initialize RazorpayX Client (if using a separate SDK)
		 
	      
	 }

    public String initiatePayment(double amount, String currency, String orderId) throws RazorpayException {
         

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Razorpay expects amount in paise
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", orderId);

        Order order = razorpayClient.orders.create(orderRequest);
        return order.get("id");
    }
    
    
    public Payment verifyPayment(String paymentId) {
        try {
        	  Payment payment = razorpayClient.payments.fetch(paymentId);
            return payment;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Generate Basic Authentication Header
    private String getBasicAuth() {
        String auth = apiKey + ":" + apiSecret;
        return Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    public String createContact() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Create the JSON request body
        JSONObject contactRequest = new JSONObject();
        contactRequest.put("name", "Gaurav Kumar");
        contactRequest.put("email", "gaurav.kumar@example.com");
        contactRequest.put("contact", "9000090000");
        contactRequest.put("type", "employee");
        contactRequest.put("reference_id", "Acme Contact ID 12345");

        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        notes.put("notes_key_2", "Tea, Earl Grey… decaf.");
        contactRequest.put("notes", notes);

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "contacts"))
                .header("Authorization", "Basic " + getBasicAuth())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(contactRequest.toString()))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        // Check if the response status code indicates success (201)
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            JSONObject jsonResponse = new JSONObject(response.body());
            String contactId = jsonResponse.getString("id");
            System.out.println("Contact created successfully with ID: " + contactId);
            return contactId;
        } else {
            throw new RuntimeException("Failed to create contact: " + response.body());
        }
    }
    public String createFundAccount(String contactId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Create the JSON request body for fund account
        JSONObject fundAccountRequest = new JSONObject();
        fundAccountRequest.put("contact_id", contactId);
        fundAccountRequest.put("account_type", "bank_account");

        JSONObject bankAccount = new JSONObject();
        bankAccount.put("name", "Gaurav Kumar");
        bankAccount.put("ifsc", "HDFC0000053");
        bankAccount.put("account_number", "765432123456789");
        fundAccountRequest.put("bank_account", bankAccount);

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "fund_accounts"))
                .header("Authorization", "Basic " + getBasicAuth())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(fundAccountRequest.toString()))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());

        // Check if the response status code indicates success (201)
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            JSONObject jsonResponse = new JSONObject(response.body());
            String fundAccountId = jsonResponse.getString("id");
            System.out.println("Fund account created successfully with ID: " + fundAccountId);
            return fundAccountId;
        } else {
            throw new RuntimeException("Failed to create fund account: " + response.body());
        }
    }
    
    
    public String createPayout(String fundAccountId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Create the JSON request body for payout
        JSONObject payoutRequest = new JSONObject();
        
        //2323230022432761
        payoutRequest.put( "account_number", "2323230022432761");
        payoutRequest.put("fund_account_id", fundAccountId);
        payoutRequest.put("amount", 1000); // Amount in the smallest currency unit (e.g., paise for INR)
        payoutRequest.put("currency", "INR");
        payoutRequest.put("mode", "IMPS");
        payoutRequest.put("purpose", "refund");
        payoutRequest.put("queue_if_low_balance", true);
        payoutRequest.put("reference_id", "Acme Transaction ID 12345");
        payoutRequest.put("narration", "Acme Corp Fund Transfer");

        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        notes.put("notes_key_2", "Tea, Earl Grey… decaf.");
        payoutRequest.put("notes", notes);

        // Build the HTTP request
        String uniqueIdempotencyKey = UUID.randomUUID().toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "payouts"))
                .header("Authorization", "Basic " + getBasicAuth())
                .header("Content-Type", "application/json")
                .header("X-Payout-Idempotency", uniqueIdempotencyKey) // Unique ID for idempotency
                .POST(HttpRequest.BodyPublishers.ofString(payoutRequest.toString()))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());

        // Check if the response status code indicates success (201)
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            JSONObject jsonResponse = new JSONObject(response.body());
            String payoutId = jsonResponse.getString("id");
            System.out.println("Payout created successfully with ID: " + payoutId);
            return payoutId;
        } else {
            throw new RuntimeException("Failed to create payout: " + response.body());
            
        }
    }
    
    
    
    public String refundPayment(String paymentId,int amount) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Create the JSON request body
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("amount", 100);
        refundRequest.put("speed", "optimum");
        refundRequest.put("receipt", "Receipt No. #31");

        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        notes.put("notes_key_2", "Tea, Earl Grey… decaf.");
        refundRequest.put("notes", notes);

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "payments/" + paymentId + "/refund"))
                .header("Authorization", "Basic " + getBasicAuth())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(refundRequest.toString()))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            JSONObject jsonResponse = new JSONObject(response.body());
            System.out.println("Refund successful: " + jsonResponse.toString());
            return jsonResponse.toString();
        } else {
            throw new RuntimeException("Failed to create refund: " + response.body());
        }
    }
    
    public Transfer transferPaymentToSeller(String sellerAccountNo, String ifscCode, String name, int amount) {
        try {
        	System.out.println("hi i am here");
            JSONObject transferRequest = new JSONObject();
            transferRequest.put("account_number", sellerAccountNo);
            transferRequest.put("amount", amount*100); // amount in paise
            transferRequest.put("currency", "INR");
            transferRequest.put("method", "bank_transfer");
            transferRequest.put("ifsc", ifscCode);
            transferRequest.put("name", name);
            System.out.println("hi i am here");
            Transfer transfer = razorpayClient.transfers.create(transferRequest);
            return transfer;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return null;
        }
    }
}
