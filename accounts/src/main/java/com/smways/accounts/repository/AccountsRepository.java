package com.smways.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.smways.accounts.entities.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    public Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional
    @Modifying 
    public void deleteByCustomerId(Long customerId);
}
