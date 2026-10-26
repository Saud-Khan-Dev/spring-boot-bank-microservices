package com.smways.accounts.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "accounts") 
public record AccountsContactDto (

    String message,
    Map<String,String>contactDetails,
    List<String>onCallSupport

){

}