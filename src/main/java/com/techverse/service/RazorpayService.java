package com.techverse.service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
 import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {
	public String apiKey="rzp_test_wIkA36LzCEN5sz";
	 
	public String apiSecret="q66erZ3vUMDufjJyZi3iA7Qy";
	

    public String initiatePayment(double amount, String currency, String orderId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Razorpay expects amount in paise
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", orderId);

        Order order = razorpayClient.orders.create(orderRequest);
        return order.get("id");
    }
}
