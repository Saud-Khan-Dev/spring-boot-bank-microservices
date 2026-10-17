package com.smways.accounts.dto;

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
        example = "Customer with mobile number 9876543210 not found.")
    private String errorMessage;

    @Schema(description = "Time when the error happened")
    private LocalDateTime errorTime;
}
