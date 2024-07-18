package com.techverse.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techverse.model.Product;
import com.techverse.model.User;

public interface ProductRepository extends JpaRepository<Product, Long> {
	/*
	@Query("SELECT p FROM Product p " + // Add space after p and before "
	        "WHERE (p.category.name = :category OR :category = '') " + // Add spaces around OR and before "
	        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " + // Add spaces around AND, OR, and BETWEEN
	        "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " + // Add spaces around OR and >=
	        "ORDER BY " +
	        "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " + // Add spaces around =
	        "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
	public List<Product> filterProducts(@Param("category") String category,
	                                    @Param("minPrice") Integer minPrice,
	                                    @Param("maxPrice") Integer maxPrice,
	                                    @Param("minDiscount") Integer minDiscount,
	                                    @Param("sort") String sort);

*/
	
	/*
	@Query("SELECT p FROM Product p" +
	"WHERE(p.category.name=:category OR :category='')"+
	"AND((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "+		
	"AND(:minDiscount IS NULL OR p.discountPercent>=:minDiscount)"+
	"ORDER BY "+
	"CASE WHEN :sort='price_low' THEN p.discountedPrice END ASC,"+
	"CASE WHEN :sort='price_high' THEN p.discountedPrice END DESC,"
	)
	public List<Product> filterProducts(@Param("category")String category,
			@Param("minPrice")Integer minPrice,
			@Param("maxPrice")Integer maxPrice,
			@Param("minDiscount")Integer minDiscount,
			@Param("sort")String sort);
			
			*/
	
	
	//01:15  2nd video
	
	
	
	 List<Product> findByCategoryId(Long categoryId);  

	 @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p.product_price DESC")
	    List<Product> findByCategoryIdOrderByProductPriceDesc(Long categoryId);
	 
	 @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p.product_price ASC")
	    List<Product> findByCategoryIdOrderByProductPriceASC(Long categoryId);
	 
	 
	 

	 @Query("SELECT p FROM Product p  ORDER BY p.product_price DESC")
	    List<Product> findByOrderByProductPriceDesc();
	 
	 @Query("SELECT p FROM Product p  ORDER BY p.product_price ASC")
	    List<Product> findByOrderByProductPriceASC( );
	 
	  List<Product> findByCategoryIdAndCreatedAtBetween(long categoryId, LocalDateTime startDateTime, LocalDateTime endDateTime);
	  
	  List<Product> findByCreatedAtBetween( LocalDateTime startDateTime, LocalDateTime endDateTime);
	  
	  
	  List<Product> findByTitleContainingIgnoreCase(String search);

	  
	  //seller

	  List<Product> findByUser(User user);

	  
}
