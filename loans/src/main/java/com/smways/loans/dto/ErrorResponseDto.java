package com.smways.loans.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
    name = "ErrorResponse",
    description = "Schema to hold an error response"
)
public class ErrorResponseDto {

    @Schema(description = "API path the client called", example = "uri=/api/fetch")
    private String apiPath;

    @Schema(description = "HTTP status of the error", example = "NOT_FOUND")
    private HttpStatus errorCode;

    @Schema(description = "Message explaining what went wrong",
        example = "Loan not found with mobile number 4354437687")
    private String errorMessage;

    @Schema(description = "Time when the error happened")
    private LocalDateTime errorTime;
}
