package com.techverse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.model.OtpEntity;
import com.techverse.repository.OtpEntityRepository;

@Service
public class OtpEntityServiceImplementation implements OtpEntityService {

	@Autowired
	private OtpEntityRepository otpEntityRepository;
	
	@Override
	public String findOtpByemail(String email) {

		
		Optional<OtpEntity> otpEntity= otpEntityRepository.findByEmail(email);
		if(otpEntity.isPresent())
		{
			return otpEntity.get().getOtp();
		
		}
		return "";
	}
	@Override
	public Optional<OtpEntity> findByemail(String email) {

		
		Optional<OtpEntity> otpEntity= otpEntityRepository.findByEmail(email);
		 
			return otpEntity;
		
		 
	}

	@Override
	public void save(String email, String otp) {
		// TODO Auto-generated method stub
		

		Optional<OtpEntity> otpEntity= otpEntityRepository.findByEmail(email);
		if(otpEntity.isPresent())
		{
			otpEntity.get().setOtp(otp);
			otpEntityRepository.save(otpEntity.get());
		}
		else{
		OtpEntity otpE=new OtpEntity();
		otpE.setEmail(email);
		otpE.setOtp(otp);
		otpEntityRepository.save(otpE);
		}
		
	}

}
