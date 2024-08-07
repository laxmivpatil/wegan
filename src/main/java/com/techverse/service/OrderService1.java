package com.techverse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.*;
import com.techverse.model.User;
import com.techverse.response.PaymentLinkResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class OrderService1 {

     
    private String keyId="rzp_test_wIkA36LzCEN5sz";
 
    private String keySecret="q66erZ3vUMDufjJyZi3iA7Qy";

    public Map<String, String> createOrder(double amount, String currency, String receipt, User user) throws RazorpayException {
    	 Map<String, String> result=new HashMap<>();
    	
        RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
        
        // Create the payment link request
    /*    JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount * 100);
        paymentLinkRequest.put("currency", "INR");

        // Add customer details
        JSONObject customer = new JSONObject();
        customer.put("name", user.getName());
        customer.put("email", user.getEmail());
        paymentLinkRequest.put("customer", customer);

        // Add notification preferences
        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);

        // Add callback details (if necessary)
        paymentLinkRequest.put("callback_url", "http://www.google.com/");
        paymentLinkRequest.put("callback_method", "get");

        // Create the payment link
        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        // Print payment link details
        String paymentLinkId = payment.get("id");
        String paymentLinkUrl = payment.get("short_url");

        PaymentLinkResponse res = new PaymentLinkResponse();
        res.setPayment_link_id(paymentLinkId);
        res.setPayment_link_url(paymentLinkUrl);
*/
        // Create the order request
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Razorpay expects amount in paisa, so multiply by 100 for rupees
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);

        // Create the order
        Order order = razorpayClient.orders.create(orderRequest);
String paymentLinkUrl="abc";
        System.out.println(paymentLinkUrl);
        result.put("paymentLink", paymentLinkUrl);
        result.put("orderId", order.get("id").toString());
        return result;
    }
}
