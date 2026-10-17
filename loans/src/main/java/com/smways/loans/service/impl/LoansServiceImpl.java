package com.smways.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.smways.loans.constants.LoansConstants;
import com.smways.loans.dto.LoansDto;
import com.smways.loans.entity.Loans;
import com.smways.loans.exception.LoanAlreadyExistsException;
import com.smways.loans.exception.ResourceNotFoundException;
import com.smways.loans.mapper.LoansMapper;
import com.smways.loans.repository.LoansRepository;
import com.smways.loans.service.ILoansService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> existingLoan = loansRepository.findByMobileNumber(mobileNumber);
        if (existingLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
        );
        return LoansMapper.toDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "loan number", loansDto.getLoanNumber())
        );
        LoansMapper.toEntity(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

}
