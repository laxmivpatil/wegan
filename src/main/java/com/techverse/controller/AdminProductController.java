package com.techverse.controller;

import java.util.List;

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
@RequestMapping("/api/admin/products")
public class AdminProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	 @PostMapping("/create")
	    public ResponseEntity<?> createProduct(
	    		@RequestHeader("Authorization") String jwt,
	            @RequestPart("categoryId") String categoryId,
	            @RequestPart("email") String email,
	            @RequestPart("title") String title,
	            @RequestPart("site") String site,
	            @RequestPart("quantity")String  quantity,
	            @RequestPart("description") String description,
	            @RequestPart("product_tags") String productTags,
	            @RequestPart("policy") String policy,
	            @RequestPart("no_of_days") String numberOfDays,
	            @RequestPart("product_price") String productPrice,
	            @RequestPart(value="image1",required=false) MultipartFile image1,
	            @RequestPart(value="image2",required=false) MultipartFile image2,
	            @RequestPart(value="image3",required=false) MultipartFile image3,
	            @RequestPart(value="image4",required=false) MultipartFile image4,
	            @RequestPart(value="image5",required=false) MultipartFile image5,
	            @RequestPart(value="image6",required=false) MultipartFile image6)throws UserException {

		
		 User user =userService.findUserProfileByJwt(jwt);
	        Product createdProduct = productService.createProduct(
	        		 Long.parseLong(categoryId), email, title, site, Integer.parseInt( quantity), description,
	                productTags, policy, numberOfDays,Integer.parseInt(productPrice) , image1,image2,image3,image4,image5,image6);

	        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
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
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req,@PathVariable Long productId) throws  ProductException{
		
		Product product=productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
		
	}
	 
	
	

}
