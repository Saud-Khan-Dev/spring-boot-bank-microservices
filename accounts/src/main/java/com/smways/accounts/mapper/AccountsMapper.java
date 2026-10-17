package com.smways.accounts.mapper;

import com.smways.accounts.dto.AccountsDto;
import com.smways.accounts.entities.Accounts;

public class AccountsMapper {
    
    public static AccountsDto toDto(Accounts accounts, AccountsDto accountsDto) {
        if (accounts == null) {
            return null;
        }

        if (accountsDto == null) {
            accountsDto = new AccountsDto();
        }

        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setCustomerId(accounts.getCustomerId());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());

        return accountsDto;
    }

    public static Accounts toEntity(AccountsDto accountsDto, Accounts accounts) {
        if (accountsDto == null) {
            return null;
        }

        if (accounts == null) {
            accounts = new Accounts();
        }

        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setCustomerId(accountsDto.getCustomerId());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());

        return accounts;
    }

}
