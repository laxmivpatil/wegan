package com.techverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techverse.model.Order;
import com.techverse.model.User;

public interface OrderRepository extends JpaRepository<Order,Long>{
	
	
	@Query("SELECT o FROM Order o WHERE o.user.id=:userId AND(o.orderStatus='PLACED' OR o.orderStatus='CONFIRMED' OR o.orderStatus='SHIPPED' OR o.orderStatus='DELIVERED' )")
	public List<Order> getUsersOreders(@Param("userId") Long userId);
	
	@Query("SELECT o FROM Order o WHERE o.user.id=:userId AND(o.orderStatus='PLACED' OR o.orderStatus='CONFIRMED' OR o.orderStatus='SHIPPED' OR o.orderStatus='DELIVERED' OR o.orderStatus='PENDING')")
	public List<Order> getUsersAllOreders(@Param("userId") Long userId);
	
	Optional<Order> findByOrderId(String orderId);
	
	
	 // Method to find orders by user and order status, sorted by createdAt in ascending order
    List<Order> findByOrderStatusOrderByCreatedAtAsc(String orderStatus);
    
    List<Order> findByUserOrderByCreatedAtDesc(User user);

}
