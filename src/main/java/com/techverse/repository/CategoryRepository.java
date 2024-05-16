package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techverse.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	 
	 
}
