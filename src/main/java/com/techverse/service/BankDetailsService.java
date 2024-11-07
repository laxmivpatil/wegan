package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.model.BankDetails; 
import com.techverse.repository.BankDetailsRepository;
 

@Service
public class BankDetailsService {
    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    public BankDetails saveBankDetails(BankDetails bankDetails) {
        return bankDetailsRepository.save(bankDetails);
    }
}