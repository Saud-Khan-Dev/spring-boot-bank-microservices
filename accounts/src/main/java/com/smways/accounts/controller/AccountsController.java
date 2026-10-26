package com.smways.accounts.controller;

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

import com.smways.accounts.constants.AccountsConstants;
import com.smways.accounts.dto.AccountsContactDto;
import com.smways.accounts.dto.CustomersDto;
import com.smways.accounts.dto.ErrorResponseDto;
import com.smways.accounts.dto.ResponseDto;
import com.smways.accounts.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;


@Tag(
    name = "Accounts REST API",
    description = "CRUD REST APIs to CREATE, FETCH, UPDATE and DELETE account details"
)
@RestController
@RequestMapping(path="/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class AccountsController {

    private IAccountService iAccountService;


    public AccountsController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    @Value("${build.info.version}") 
    private String buildVersion;

    @Autowired
    private Environment environment;


    @Autowired
    private AccountsContactDto accountsContactDto;


    @Operation(
        summary = "Create Account REST API",
        description = "Creates a new customer and a savings account for them"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input, e.g. a mobile number that is not 10 digits",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "A customer with this mobile number already exists",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create") 
    public ResponseEntity<ResponseDto> createAccount(@Valid  @RequestBody CustomersDto customersDto) {
        
        iAccountService.createAccount(customersDto);  
      
        return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(
                    new ResponseDto(
                    AccountsConstants.STATUS_201,
                    AccountsConstants.MESSAGE_201
                )
            );
    }
 
    @Operation(
        summary = "Fetch Account Details REST API",
        description = "Fetches the customer and account details for a mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account details found"),
        @ApiResponse(responseCode = "400", description = "Mobile number is missing or not 10 digits",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "No customer or account found for this mobile number",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping ("/fetch")
    public ResponseEntity<CustomersDto> fetchAccountDetails(@RequestParam
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobileNumber
    ) {
        
        CustomersDto customersDto = iAccountService.fetchAccountDetails(mobileNumber);  
      
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(customersDto);
    }

    @Operation(
        summary = "Update Account Details REST API",
        description = "Updates the customer and account details using the account number in accountsDto"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "No account or customer found to update",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "417", description = "Update failed, e.g. accountsDto was not sent"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid  @RequestBody CustomersDto customersDto) {

        boolean isUpdated = iAccountService.updateAccount(customersDto);

        if (isUpdated) {
            return ResponseEntity
                      .status(HttpStatus.OK)
                      .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                      .status(HttpStatus.EXPECTATION_FAILED)
                      .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete Account REST API",
        description = "Deletes the customer and their account for a mobile number"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Mobile number is missing or not 10 digits",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "No customer found for this mobile number",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(responseCode = "417", description = "Delete failed"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam 
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobileNumber
    ) {

        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);

        if (isDeleted) {
            return ResponseEntity
                      .status(HttpStatus.OK)
                      .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                      .status(HttpStatus.EXPECTATION_FAILED)
                      .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping ("/build-info")
    public ResponseEntity<ResponseDto> getBuildInfo() {
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(new ResponseDto(AccountsConstants.STATUS_200, buildVersion));
    }



    @GetMapping("/java-version")   
    public ResponseEntity<ResponseDto> getBuildInfoFromEnv() {
        String buildVersionFromEnv = environment.getProperty("JAVA_HOME");
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(new ResponseDto(AccountsConstants.STATUS_200, buildVersionFromEnv));
    }


    @GetMapping ("/contact-details")
    public ResponseEntity<AccountsContactDto> getContactDetails(){
       
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(accountsContactDto);
    }

}
