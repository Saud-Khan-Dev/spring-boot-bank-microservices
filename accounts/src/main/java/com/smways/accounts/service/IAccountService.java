package com.smways.accounts.service;

import com.smways.accounts.dto.CustomersDto;

public interface IAccountService {
 
    void createAccount(CustomersDto customersDto);

    CustomersDto fetchAccountDetails(String mobileNumber);

    boolean updateAccount(CustomersDto customersDto);

    boolean deleteAccount(String mobileNumber);
    
}