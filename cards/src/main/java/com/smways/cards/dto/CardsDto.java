package com.smways.cards.dto;

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
    name = "Cards",
    description = "Schema to hold Card information"
)
public class CardsDto {

    @Schema(description = "10 digit mobile number of the customer", example = "4354437687")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "12 digit card number, used to find the card on update", example = "100107091026")
    @NotEmpty(message = "Card number cannot be empty")
    @Pattern(regexp = "^[0-9]{12}$", message = "CardNumber must be 12 digits")
    private String cardNumber;

    @Schema(description = "Type of the card", example = "Credit Card")
    @NotEmpty(message = "Card type cannot be empty")
    private String cardType;

    @Schema(description = "Total card limit", example = "100000")
    @Positive(message = "Total card limit should be greater than zero")
    private int totalLimit;

    @Schema(description = "Total amount used", example = "10000")
    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private int amountUsed;

    @Schema(description = "Total available amount on the card", example = "90000")
    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    private int availableAmount;
}
