package com.techverse.repository;

import com.techverse.model.ShippingAddress;
import com.techverse.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
	  ShippingAddress findByUserAndSetDefaultAddress(User user, boolean setDefaultAddress);

}

