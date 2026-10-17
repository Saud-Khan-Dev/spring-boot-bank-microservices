package com.smways.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "Loans",
    description = "Schema to hold Loan information"
)
public class LoansDto {

    @Schema(description = "10 digit mobile number of the customer", example = "4354437687")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "12 digit loan number, used to find the loan on update", example = "100714697991")
    @NotEmpty(message = "Loan number cannot be empty")
    @Pattern(regexp = "^[0-9]{12}$", message = "LoanNumber must be 12 digits")
    private String loanNumber;

    @Schema(description = "Type of the loan", example = "Home Loan")
    @NotEmpty(message = "Loan type cannot be empty")
    private String loanType;

    @Schema(description = "Total loan amount", example = "100000")
    @Positive(message = "Total loan amount should be greater than zero")
    private int totalLoan;

    @Schema(description = "Total loan amount paid", example = "1000")
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private int amountPaid;

    @Schema(description = "Total outstanding amount against the loan", example = "99000")
    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    private int outstandingAmount;
}
