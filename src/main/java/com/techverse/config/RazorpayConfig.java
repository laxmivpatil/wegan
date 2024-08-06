package com.techverse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;

@Configuration
public class RazorpayConfig {

	
	
	public String apiKey="rzp_test_wIkA36LzCEN5sz";
	 
	public String apiSecret="q66erZ3vUMDufjJyZi3iA7Qy";
	
	
 //   @Value("${razorpay.key.id}")
    private String keyId="rzp_test_wIkA36LzCEN5sz";

  //  @Value("${razorpay.key.secret}")
    private String keySecret="q66erZ3vUMDufjJyZi3iA7Qy";

    @Bean
    public RazorpayClient razorpayClient() throws Exception {
        return new RazorpayClient(keyId, keySecret);
    }
}
