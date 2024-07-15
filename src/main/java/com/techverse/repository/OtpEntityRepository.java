package com.techverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.OtpEntity;

public interface OtpEntityRepository extends JpaRepository<OtpEntity, Long> {

	
	public Optional<OtpEntity> findByEmail(String email);
}
