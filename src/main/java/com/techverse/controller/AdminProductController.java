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
import org.springframework.web.bind.annotation.RequestParam;
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
	        @RequestPart("title") String title,
	        @RequestPart("site") String site,
	        @RequestPart("quantity") String quantity,
	        @RequestPart("description") String description,
	        @RequestPart("product_tags") String productTags,
	        @RequestPart("policy") String policy,
	        @RequestPart("sgst") String sgst,                // long
	        @RequestPart("igst") String igst,                // long
	        @RequestPart("weight") String weight,            // double
	        @RequestPart("base_price") String basePrice,     // Integer
	        @RequestPart(value="discount_per", required = false) String discountPer, // int
	        @RequestPart("discount") String discount,        // boolean
	        @RequestPart(value="discount_type", required = false) String discountType,
	        @RequestPart(value="discount_price", required = false) String discountPrice,   // double
	        @RequestPart("igst_price") String igstPrice,           // double
	        @RequestPart("sgst_price") String sgstPrice,           // double
	        @RequestPart("final_price") String finalPrice,         // double
	        @RequestPart(value = "imageUrl1", required = false) MultipartFile image1,
	        @RequestPart(value = "imageUrl2", required = false) MultipartFile image2,
	        @RequestPart(value = "imageUrl3", required = false) MultipartFile image3,
	        @RequestPart(value = "imageUrl4", required = false) MultipartFile image4,
	        @RequestPart(value = "imageUrl5", required = false) MultipartFile image5,
	        @RequestPart(value = "imageUrl6", required = false) MultipartFile image6
	) throws UserException {

		
		/*
		
		  System.out.println("Authorization JWT: " + jwt);
		    System.out.println("Category ID: " + categoryId);
		    System.out.println("Title: " + title);
		    System.out.println("Site: " + site);
		    System.out.println("Quantity: " + quantity);
		    System.out.println("Description: " + description);
		    System.out.println("Product Tags: " + productTags);
		    System.out.println("Policy: " + policy);
		    System.out.println("SGST: " + sgst);
		    System.out.println("IGST: " + igst);
		    System.out.println("Weight: " + weight);
		    System.out.println("Base Price: " + basePrice);
		    System.out.println("Discount Per: " + discountPer);
		    System.out.println("Discount: " + discount);
		    System.out.println("Discount Type: " + discountType);
		    System.out.println("Discount Price: " + discountPrice);
		    System.out.println("IGST Price: " + igstPrice);
		    System.out.println("SGST Price: " + sgstPrice);
		    System.out.println("Final Price: " + finalPrice);
		    System.out.println("Image1: " + (image1 != null ? image1.getOriginalFilename() : "No image"));
		    System.out.println("Image2: " + (image2 != null ? image2.getOriginalFilename() : "No image"));
		    System.out.println("Image3: " + (image3 != null ? image3.getOriginalFilename() : "No image"));
		    System.out.println("Image4: " + (image4 != null ? image4.getOriginalFilename() : "No image"));
		    System.out.println("Image5: " + (image5 != null ? image5.getOriginalFilename() : "No image"));
		    System.out.println("Image6: " + (image6 != null ? image6.getOriginalFilename() : "No image"));*/
		   // Calling the service method to create the product
	 Product createdProduct = productService.createProduct1(
	            jwt,
	            Long.parseLong(categoryId),
	            title,
	            site,
	            Integer.parseInt(quantity),
	            description,
	            productTags,
	            policy,
	            Integer.parseInt("0"),  
	            image1,
	            image2,
	            image3,
	            image4,
	            image5,
	            image6,
	            Long.parseLong(sgst),
	            Long.parseLong(igst),
	            Double.parseDouble(weight),
	            Integer.parseInt(basePrice),
	            Long.parseLong(discountPer),
	            Boolean.parseBoolean(discount),
	            discountType,
	            Double.parseDouble(discountPrice),
	            Double.parseDouble(igstPrice),
	            Double.parseDouble(sgstPrice),
	            Double.parseDouble(finalPrice)
	    );
 
	 createdProduct.setStatus("Active");
	    // Returning the created product with an OK status
	    return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
	}

	
	@PutMapping("/changeStatus")
	public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long productId,@RequestParam String status) throws ProductException{
		
		  productService.changeProductStatus(productId, status);
		 
		 
		 ApiResponse res=new ApiResponse();
		 res.setMessage("product status changed successfully");
		 res.setStatus(true);
		 
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> changeProductstatus(@RequestParam Long productId) throws ProductException{
		
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

	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody Product req,@RequestParam Long productId) throws  ProductException{
		Map<String,Object> response = new HashMap<>();
		Product product=productService.updateProduct(productId, req);
		response.put("product", product);
		response.put("status", true);
	    response.put("message", "product updated Successfully");
	    return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		 
		
	}
	 
	
	

}
