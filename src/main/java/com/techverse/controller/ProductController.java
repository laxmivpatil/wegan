package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.model.Product;
import com.techverse.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

	
	
	@GetMapping("/category/")
    public Map<String, Object>  getProductsByCategoryId(@RequestParam Long categoryId) {
		Map<String,Object> response = new HashMap<>();
    	List<Product> product=productService.getProductsByCategoryId(categoryId);
    	response.put("product", product);
		response.put("status", true);
        response.put("message", "product retrived Successfully");
        
        return response;
    }
	
	 @GetMapping("/category/price-high-low")
	    public Map<String,Object> getProductsByCategoryIdSortedByPriceDesc(@RequestParam Long categoryId) {
		 
		 
		 Map<String,Object> response = new HashMap<>();
	    	List<Product> product=productService.getProductsByCategoryIdSortedByPriceDesc(categoryId);
	    	response.put("product", product);
			response.put("status", true);
	        response.put("message", "product retrived Successfully");
	        
	        return response;
	         
	    }

	 @GetMapping("/category/price-low-high")
	    public  Map<String,Object> getProductsByCategoryIdSortedByPriceASC(@RequestParam Long categoryId) {
		 Map<String,Object> response = new HashMap<>();
		   
		 List<Product> product=productService.getProductsByCategoryIdSortedByPriceASC(categoryId);
			    	response.put("product", product);
					response.put("status", true);
			        response.put("message", "product retrived Successfully");
			        
			        return response;
		 
		 
	         
	    }
	 
	 @GetMapping("/category/newest")
	    public Map<String,Object> getNewestProductsByCategory(@RequestParam Long categoryId) {
	        int days = 4; // You can change this to any number of days you want to consider
	    
	        
	        
	        
	        Map<String,Object> response = new HashMap<>();
			   
			 List<Product> product= productService.getNewestProductsByCategory(categoryId, days);
				    	response.put("product", product);
						response.put("status", true);
				        response.put("message", "product retrived Successfully");
				        
				        return response;
	    }
}

