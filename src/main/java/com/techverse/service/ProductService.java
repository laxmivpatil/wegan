package com.techverse.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
 
import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Product;
import com.techverse.request.CreateProductRequest;

public interface ProductService {

	public Product createProduct(
            Long categoryId, String email, String title, String site,
            int quantity, String description, String productTags,
            String policy, String numberOfDays, Integer productPrice,
             MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4, MultipartFile image5, MultipartFile image6)throws  UserException;  

	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId,Product req) throws ProductException;
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public Page<Product> getAllProduct(String category,List<String>sizes,Integer minPrice,Integer maxPrice,
			Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
	
	
	public List<Product> searchProduct(String searchparameter)throws ProductException;
	
	public List<Product> findAllProduct() ;
	
	
	
}
