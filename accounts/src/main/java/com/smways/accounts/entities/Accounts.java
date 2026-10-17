package com.smways.accounts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter 
@Setter
@ToString
@AllArgsConstructor 
@NoArgsConstructor 
public class Accounts extends BaseEntity {
    
    @Id
    private Long accountNumber;

    @Column 
    private Long customerId;
    
    @Column 
    private String accountType;

    @Column 
    private String branchAddress;

}
