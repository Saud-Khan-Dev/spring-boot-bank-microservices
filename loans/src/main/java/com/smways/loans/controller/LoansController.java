package com.smways.loans.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smways.loans.constants.LoansConstants;
import com.smways.loans.dto.LoansContactDto;
import com.smways.loans.dto.ErrorResponseDto;
import com.smways.loans.dto.LoansDto;
import com.smways.loans.dto.ResponseDto;
import com.smways.loans.service.ILoansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;


@Tag(
    name = "Loans REST API",
    description = "CRUD REST APIs to CREATE, FETCH, UPDATE and DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class LoansController {

    private ILoansService iLoansService;


    public LoansController(ILoansService iLoansService) {
        this.iLoansService = iLoansService;
    }

    @Value("${build.info.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;


    @Autowired
    private LoansContactDto loansContactDto;


    @Operation(
        summary = "Create Loan REST API",
        description = "Creates a new Home Loan for the given mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Loan created successfully"),
        @ApiResponse(responseCode = "400", description = "Mobile number is missing or not 10 digits",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "A loan already exists for this mobile number",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobileNumber
    ) {

        iLoansService.createLoan(mobileNumber);

        return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "Fetches the loan details for a mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan details found"),
        @ApiResponse(responseCode = "400", description = "Mobile number is missing or not 10 digits",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "No loan found for this mobile number",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobileNumber
    ) {

        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);

        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(loansDto);
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "Updates the loan details using the loan number in the request body"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "No loan found for this loan number",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "417", description = "Update failed"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {

        boolean isUpdated = iLoansService.updateLoan(loansDto);

        if (isUpdated) {
            return ResponseEntity
                      .status(HttpStatus.OK)
                      .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                      .status(HttpStatus.EXPECTATION_FAILED)
                      .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "Deletes the loan for a mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Mobile number is missing or not 10 digits",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "No loan found for this mobile number",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "417", description = "Delete failed"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobileNumber
    ) {

        boolean isDeleted = iLoansService.deleteLoan(mobileNumber);

        if (isDeleted) {
            return ResponseEntity
                      .status(HttpStatus.OK)
                      .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                      .status(HttpStatus.EXPECTATION_FAILED)
                      .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping ("/build-info")
    public ResponseEntity<ResponseDto> getBuildInfo() {
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(new ResponseDto(LoansConstants.STATUS_200, buildVersion));
    }


    @GetMapping("/java-version")
    public ResponseEntity<ResponseDto> getBuildInfoFromEnv() {
        String buildVersionFromEnv = environment.getProperty("JAVA_HOME");
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(new ResponseDto(LoansConstants.STATUS_200, buildVersionFromEnv));
    }


    @GetMapping ("/contact-details")
    public ResponseEntity<LoansContactDto> getContactDetails(){

        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(loansContactDto);
    }

}
