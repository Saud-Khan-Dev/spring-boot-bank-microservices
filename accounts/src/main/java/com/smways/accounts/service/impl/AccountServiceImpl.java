package com.smways.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.smways.accounts.constants.AccountsConstants;
import com.smways.accounts.dto.AccountsDto;
import com.smways.accounts.dto.CustomersDto;
import com.smways.accounts.entities.Accounts;
import com.smways.accounts.entities.Customer;
import com.smways.accounts.exception.CustomerAlreadyExistException;
import com.smways.accounts.exception.ResourceNotFoundException;
import com.smways.accounts.mapper.AccountsMapper;
import com.smways.accounts.mapper.CustomerMapper;
import com.smways.accounts.repository.AccountsRepository;
import com.smways.accounts.repository.CutomerRepository;
import com.smways.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CutomerRepository customerRepository;

    @Override
    public void createAccount(CustomersDto customersDto) {
        Customer customer = CustomerMapper.toEntity(customersDto, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer with mobile number " + customer.getMobileNumber() + " already exists.");
        }
        Customer savedCustomer = customerRepository.save(customer); 
        Accounts newAccount = createNewAccount(savedCustomer);
        accountsRepository.save(newAccount);
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(10000000000L+new Random().nextInt(900000000));
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomersDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(()->new ResourceNotFoundException("Customer with mobile number " + mobileNumber + " not found.")
    );

    Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(()->new ResourceNotFoundException("Account for customer with mobile number " + mobileNumber + " not found.")
    );

    CustomersDto customersDto = CustomerMapper.toDto(customer, new CustomersDto());
    AccountsDto accountDto = AccountsMapper.toDto(account, new AccountsDto());
    customersDto.setAccountsDto(accountDto);
    return customersDto;
    }

    @Override
    public boolean updateAccount(CustomersDto customersDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customersDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts account = accountsRepository.findById(accountsDto.getAccountNumber())
                .orElseThrow(()->new ResourceNotFoundException("Account with account number " + accountsDto.getAccountNumber() + " not found.")
            );

            // Keep the original owner; the mapper would overwrite it with whatever the request sends
            Long customerId = account.getCustomerId();
            AccountsMapper.toEntity(accountsDto, account);
            account.setCustomerId(customerId);
            accountsRepository.save(account);

            Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("Customer with customer id " + customerId + " not found.")
            );

            CustomerMapper.toEntity(customersDto, customer);
            customer.setCustomerId(customerId);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(()->new ResourceNotFoundException("Customer with mobile number " + mobileNumber + " not found.")
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
