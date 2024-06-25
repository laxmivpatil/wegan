package com.techverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
	
	@Query("SELECT ci from CartItem ci Where ci.cart=:cart And ci.product=:product  And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart")Cart cart,@Param("product")Product product ,
			@Param("userId")Long userId);
	
	
	@Query("SELECT ci from CartItem ci Where ci.product=:product  And ci.userId=:userId")
	public CartItem isCartItemExist1(@Param("product")Product product ,
			@Param("userId")Long userId);
	
	@Query("SELECT ci from CartItem ci Where ci.cart=:cart And ci.product=:product")
	public CartItem isGuestCartItemExist(@Param("cart")Cart cart,@Param("product")Product product );
	
	
	List<CartItem> findByUserId(Long userId);
	
	List<CartItem> findByCartId(Long cartId);

}
