package com.techverse.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Category;
import com.techverse.model.Product;
import com.techverse.repository.CategoryRepository;
import com.techverse.repository.ProductRepository;
import com.techverse.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	 
	@Autowired
	private  UserService userService;
	
	@Autowired
	private StorageService storageService;
	@Autowired
	private CategoryRepository categoryRepository;
	
	 public Product createProduct(
	            Long categoryId, String email, String title, String site,
	            int quantity, String description, String productTags,
	            String policy, String numberOfDays, Integer productPrice,
	             MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4, MultipartFile image5, MultipartFile image6) throws UserException{
		 	
	        Product product = new Product();
	        product.setCategory(categoryRepository.findById(categoryId).get());
	        product.setEmail(email);
	        product.setTitle(title);
	        product.setSite(site);
	        product.setQuantity(quantity);
	        product.setDescription(description);
	        product.setProduct_tags(productTags);
	        product.setPolicy(policy);
	        product.setNo_of_days(numberOfDays);
	        product.setProduct_price(productPrice);
	        System.out.println(productPrice);
	        double serviceCharges=productPrice*2/100;
	        System.out.println(serviceCharges);
	        double sellerPrice=productPrice-serviceCharges;
	        System.out.println(sellerPrice);
	        product.setService_charges(serviceCharges);
	        product.setSeller_price(sellerPrice);
	        product.setCreatedAt(LocalDateTime.now());

	        // Save product and handle images
	        Product savedProduct = productRepository.save(product);
	        String path="";
	       if(image1!=null)
	       {
	    	   path=storageService.uploadFileOnAzure(image1);
	       }
	       
	       savedProduct.setImageUrl1(path);
	       path="";
	       if(image2!=null)
	       {
	    	   path=storageService.uploadFileOnAzure(image2);
	       }
	       savedProduct.setImageUrl2(path);
	       path="";
	       if(image3!=null)
	       {
	    	   path=storageService.uploadFileOnAzure(image3);
	       }
	       savedProduct.setImageUrl3(path);
	       path="";
	       if(image4!=null)
	       {
	    	   path=storageService.uploadFileOnAzure(image4);
	       }
	       savedProduct.setImageUrl4(path);
	       path="";
	       if(image5!=null)
	       {
	    	   path=storageService.uploadFileOnAzure(image5);
	       }
	       savedProduct.setImageUrl5(path);
	       path="";
	       if(image6!=null)
	       {
	    	   path=storageService.uploadFileOnAzure(image6);
	       }
	       savedProduct.setImageUrl6(path);
	       return productRepository.save(savedProduct);
	         
	    }

	 @Override
		public List<Product> getProductsByCategoryId(Long categoryId) {
	        return productRepository.findByCategoryId(categoryId);
	    }

	 @Override
	 public List<Product> getProductsByCategoryIdSortedByPriceDesc(Long categoryId) {
	        return productRepository.findByCategoryIdOrderByProductPriceDesc(categoryId);
	    }
	 
	 @Override
	 public List<Product> getProductsByCategoryIdSortedByPriceASC(Long categoryId) {
	        return productRepository.findByCategoryIdOrderByProductPriceASC(categoryId);
	    }
	 
	 @Override
	 public List<Product> getNewestProductsByCategory(long categoryId, int days) {
	        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
	        LocalDateTime endDateTime = LocalDateTime.now();
	        return productRepository.findByCategoryIdAndCreatedAtBetween(categoryId, startDateTime, endDateTime);
	    }
	 @Override
	 public List<Product> getNewestProducts( int days) {
	        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN);
	        LocalDateTime endDateTime = LocalDateTime.now();
	        return productRepository.findByCreatedAtBetween( startDateTime, endDateTime);
	    }
	 
	 @Override
		public Product findProductById(Long id) throws ProductException {
			// TODO Auto-generated method stub
			Optional<Product> product=productRepository.findById(id);
			if(product.isPresent())
			{
				return product.get();
			}
			throw new ProductException("Product Not Found with id "+id);
		}
	 
		@Override
		public List<Product> findAllProduct()  {

	//by me
			return productRepository.findAll();
		}

	 
		@Override
	    public List<Product> searchProductByTitle(String search) {
	        return productRepository.findByTitleContainingIgnoreCase(search);
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	@Override
	public String deleteProduct(Long productId) throws ProductException {
		 Product product=findProductById(productId);
		 
		 productRepository.delete(product);
		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		 Product product=findProductById(productId);

		 if(req.getQuantity()!=0)
		 {
			 product.setQuantity(req.getQuantity());
		 }
		return productRepository.save(product);
	}

	

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		
		 
		return null;
	}
	
	
	@Override
	public Page<Product> getAllProduct(String category , List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		 
		
		/*
		Pageable pageble=PageRequest.of(pageNumber, pageSize);
		
		List<Product> products=productRepository.filterProducts(category, minPrice, maxPrice,  sort);
		//System.out.println(products.get(0));
		
		 
		
		if(stock!=null) {
			if(stock.equals("in_stock")){
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock")){
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		int startIndex=(int) pageble.getOffset();
		int endIndex=Math.min(startIndex+pageble.getPageSize(),products.size());
		
		List<Product> pageContent=products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts=new PageImpl<>(pageContent,pageble,products.size());
		
		return filteredProducts;
		*/
		return null;
	}

	 


}
