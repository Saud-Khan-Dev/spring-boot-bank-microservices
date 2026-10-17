package com.smways.accounts.mapper;

import com.smways.accounts.dto.CustomersDto;
import com.smways.accounts.entities.Customer;

public class CustomerMapper {
      
        public static CustomersDto toDto(
            Customer customers,
            CustomersDto customersDto) {
            if (customers == null) {
                return null;
            }

            if (customersDto == null) {
                customersDto = new CustomersDto();
            }

            customersDto.setCustomerId(customers.getCustomerId());
            customersDto.setName(customers.getName());
            customersDto.setEmail(customers.getEmail());
            customersDto.setMobileNumber(customers.getMobileNumber());

            return customersDto;
        }    


        public static Customer toEntity(
            CustomersDto customersDto,
            Customer customers) {
            if (customersDto == null) {
                return null;
            }

            if (customers == null) {
                customers = new Customer();
            }

            customers.setCustomerId(customersDto.getCustomerId());
            customers.setName(customersDto.getName());
            customers.setEmail(customersDto.getEmail());
            customers.setMobileNumber(customersDto.getMobileNumber());

            return customers;
        }
}
