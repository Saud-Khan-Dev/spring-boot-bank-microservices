package com.smways.loans.mapper;

import com.smways.loans.dto.LoansDto;
import com.smways.loans.entity.Loans;

public class LoansMapper {

    public static LoansDto toDto(Loans loans, LoansDto loansDto) {
        if (loans == null) {
            return null;
        }

        if (loansDto == null) {
            loansDto = new LoansDto();
        }

        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());

        return loansDto;
    }

    public static Loans toEntity(LoansDto loansDto, Loans loans) {
        if (loansDto == null) {
            return null;
        }

        if (loans == null) {
            loans = new Loans();
        }

        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());

        return loans;
    }

}
