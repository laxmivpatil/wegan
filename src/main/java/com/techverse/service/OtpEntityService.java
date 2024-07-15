package com.techverse.service;

import java.util.Optional;

import com.techverse.model.OtpEntity;

public interface OtpEntityService {
	
	
	
	public String findOtpByemail(String email);
	
	public void save(String email,String otp);
	
	public Optional<OtpEntity> findByemail(String email);

}
