package com.smways.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "Accounts",
    description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(description = "Account number, used to find the account on update", example = "10141627439")
    @NotEmpty (message = "Account number cannot be empty")
    @Pattern (regexp = "^[0-9]{10}$", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Id of the customer who owns this account", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY)
    private Long customerId;

    @Schema(description = "Type of the account", example = "Savings")
    @NotEmpty (message = "Account type cannot be empty")
    private String accountType;

    @Schema(description = "Address of the bank branch", example = "123 Main Street, New York")
    @NotEmpty (message = "Branch name cannot be empty")
    private String branchAddress;
}
