package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Order;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.request.CreateProductRequest;
import com.techverse.response.ApiResponse;
import com.techverse.service.ProductService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	 @PostMapping("/create")
	    public ResponseEntity<?> createProduct(
	    		@RequestHeader("Authorization") String jwt,
	            @RequestPart("categoryId") String categoryId,
	         //   @RequestPart("email") String email,
	            @RequestPart("productName") String title,
	            @RequestPart("productType") String site,
	            @RequestPart("quantity")String  quantity,
	            @RequestPart("description") String description,
	          @RequestPart("productTags") String productTags,
	            @RequestPart("policy") String policy,
	           // @RequestPart("no_of_days") String numberOfDays,
	            @RequestPart("productPrice") String productPrice,
	            @RequestPart(value="image1",required=false) MultipartFile image1,
	            @RequestPart(value="image2",required=false) MultipartFile image2,
	            @RequestPart(value="image3",required=false) MultipartFile image3,
	            @RequestPart(value="image4",required=false) MultipartFile image4,
	            @RequestPart(value="image5",required=false) MultipartFile image5,
	            @RequestPart(value="image6",required=false) MultipartFile image6)throws UserException {

		
		 //User user =userService.findUserProfileByJwt(jwt);
	        Product createdProduct = productService.createProduct(jwt,
	        		 Long.parseLong(categoryId),  title, site, Integer.parseInt( quantity), description,
	                productTags, policy, Integer.parseInt(productPrice) , image1,image2,image3,image4,image5,image6);

	        return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
	    }
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{
		
		 productService.deleteProduct(productId);
		 ApiResponse res=new ApiResponse();
		 res.setMessage("product deleted successfully");
		 res.setStatus(true);
		 
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct() {
		
		List<Product> product=productService.findAllProduct();
		return new ResponseEntity<List<Product>>(product,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/")
    public ResponseEntity<?> findbyuser(
    		@RequestHeader("Authorization") String jwt)throws UserException{
		
		Map<String,Object> response = new HashMap<>();
	     
    User user=userService.findUserProfileByJwt(jwt);
    
    List<Product> product=productService.findProductByUser(user);
  	response.put("product", product);
	response.put("status", true);
    response.put("message", "product retrived Successfully");
    return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

	
}

	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req,@PathVariable Long productId) throws  ProductException{
		
		Product product=productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
		
	}
	 
	
	

}
