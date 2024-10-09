package com.techverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techverse.model.Order;
import com.techverse.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	 @Query("SELECT oi FROM OrderItem oi WHERE oi.product.user.id = :sellerId")
	    List<OrderItem> findAllBySellerId(Long sellerId);
	
	   @Query("SELECT oi FROM OrderItem oi WHERE oi.product.user.id = :sellerId AND oi.orderItemStatus = :orderStatus")
	    List<OrderItem> findAllBySellerIdAndOrderStatus(Long sellerId, String orderStatus);
	   
	   @Query("SELECT oi FROM OrderItem oi WHERE oi.userId = :buyerId AND oi.orderItemStatus = :orderStatus")
	    List<OrderItem> findAllByBuyerIdAndOrderStatus(Long buyerId, String orderStatus);
	   
	   @Query("SELECT oi FROM OrderItem oi WHERE oi.userId = :buyerId" )
	    List<OrderItem> findAllByBuyerId(Long buyerId );

	   List<OrderItem> findByOrder(Order order);


}
