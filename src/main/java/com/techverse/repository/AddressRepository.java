package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	

}
