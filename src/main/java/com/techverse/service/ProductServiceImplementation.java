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
import com.techverse.model.User;
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
	 @Override
	 public Product createProduct(String jwt,
	            Long categoryId,   String title, String site,
	            int quantity, String description, String productTags,
	            String policy, Integer productPrice,
	             MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4, MultipartFile image5, MultipartFile image6) throws UserException{
		 	
		 
			User user =userService.findUserProfileByJwt(jwt);
			 
	        Product product = new Product();
	        product.setCategory(categoryRepository.findById(categoryId).get());
	     //   product.setEmail(email);
	        product.setTitle(title);
	        product.setSite(site);
	        product.setQuantity(quantity);
	        product.setDescription(description);
	        product.setProduct_tags(productTags);
	        product.setPolicy(policy);
	     //   product.setNo_of_days(numberOfDays);
	        product.setProduct_price(productPrice);
	        System.out.println(productPrice);
	        double serviceCharges=productPrice*2/100;
	        System.out.println(serviceCharges);
	        double sellerPrice=productPrice-serviceCharges;
	        System.out.println(sellerPrice);
	        product.setService_charges(serviceCharges);
	        product.setSeller_price(sellerPrice);
	        product.setCreatedAt(LocalDateTime.now());
	         product.setUser(user);
	         user.getProducts().add(product);
	        // Save product and handle images
	        Product savedProduct = productRepository.save(product);
	        
	        String path="";
	       if(image1!=null && !image1.isEmpty())
	       {
	    	   path=storageService.uploadFileOnAzure(image1);
	    	   System.out.println("hi their"+path);
	       }
	       
	       savedProduct.setImageUrl1(path);
	       path="";
	       if(image2!=null && !image2.isEmpty())
	       {
	    	   System.out.println("hi their"+path);
	    	   path=storageService.uploadFileOnAzure(image2);
	       }
	       savedProduct.setImageUrl2(path);
	       path="";
	       if(image3!=null && !image3.isEmpty())
	       {
	    	   path=storageService.uploadFileOnAzure(image3);
	       }
	       savedProduct.setImageUrl3(path);
	       path="";
	       if(image4!=null && !image4.isEmpty())
	       {
	    	   path=storageService.uploadFileOnAzure(image4);
	       }
	       savedProduct.setImageUrl4(path);
	       path="";
	       if(image5!=null && !image5.isEmpty())
	       {
	    	   path=storageService.uploadFileOnAzure(image5);
	       }
	       savedProduct.setImageUrl5(path);
	       path="";
	       if(image6!=null && !image6.isEmpty())
	       {
	    	   path=storageService.uploadFileOnAzure(image6);
	       }
	       savedProduct.setImageUrl6(path);
	       return productRepository.save(savedProduct);
	         
	    }
	 @Override
	 public Product createProduct1(String jwt, long categoryId, String title, String site, int quantity,
             String description, String productTags, String policy, int productPrice,
             MultipartFile image1, MultipartFile image2, MultipartFile image3,
             MultipartFile image4, MultipartFile image5, MultipartFile image6,
             long sgst, long igst, double weight, Integer base_price, long discount_per,
             boolean discount, String discount_type, double discount_price,
             double igst_price, double sgst_price, double final_price) throws UserException {


	    User user = userService.findUserProfileByJwt(jwt);

	    Product product = new Product();
	    product.setSgst(sgst);
	    product.setIgst(igst);
	    product.setWeight(weight);
	    product.setBase_price(base_price);
	    product.setDiscount(discount);
	    if(discount) {
	     product.setDiscount_per(discount_per);
		  product.setDiscount_type(discount_type);
	    product.setDiscount_price(discount_price);
	    }
	    else
	    {
	    	product.setDiscount_per(Long.parseLong("0"));
			  product.setDiscount_type("");
		    product.setDiscount_price(Double.parseDouble("0"));
	    }
	    product.setIgst_price(igst_price);
	    product.setSgst_price(sgst_price);
	    product.setFinal_price(final_price);
	    
	    
	    
	    product.setCategory(categoryRepository.findById(categoryId).orElseThrow(() -> new UserException("Category not found")));
	    product.setTitle(title);
	    product.setSite(site);
	    product.setQuantity(quantity);
	    product.setDescription(description);
	    product.setProduct_tags(productTags);
	    product.setPolicy(policy);
	    product.setProduct_price(productPrice);

	    double serviceCharges = productPrice * 2 / 100;
	    double sellerPrice = productPrice - serviceCharges;
	    product.setService_charges(serviceCharges);
	    product.setSeller_price(sellerPrice);
	    product.setCreatedAt(LocalDateTime.now());
	    product.setUser(user);
	   

	    // Save product to assign ID
	    Product savedProduct = productRepository.save(product);

	    // Handle image uploads
	    if (image1 != null && !image1.isEmpty()) {
	        savedProduct.setImageUrl1(storageService.uploadFileOnAzure(image1));
	    }
	    if (image2 != null && !image2.isEmpty()) {
	        savedProduct.setImageUrl2(storageService.uploadFileOnAzure(image2));
	    }
	    if (image3 != null && !image3.isEmpty()) {
	        savedProduct.setImageUrl3(storageService.uploadFileOnAzure(image3));
	    }
	    if (image4 != null && !image4.isEmpty()) {
	        savedProduct.setImageUrl4(storageService.uploadFileOnAzure(image4));
	    }
	    if (image5 != null && !image5.isEmpty()) {
	        savedProduct.setImageUrl5(storageService.uploadFileOnAzure(image5));
	    }
	    if (image6 != null && !image6.isEmpty()) {
	        savedProduct.setImageUrl6(storageService.uploadFileOnAzure(image6));
	    }

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
	 public List<Product> getProductsBySortedByPriceDesc() {
	        return productRepository.findByOrderByProductPriceDesc();
	    }
	 
	 
	 @Override
	 public List<Product> getProductsBySortedByPriceASC() {
	        return productRepository.findByOrderByProductPriceASC();
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

	@Override
	public List<Product> findProductByUser(User user) {
		// TODO Auto-generated method stub
		
	
		return productRepository.findByUser(user);
	}

	
	 


}
