package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByEmail(String email);

}
