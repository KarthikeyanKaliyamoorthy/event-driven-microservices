package com.eazybytes.accounts.comand.event;

import lombok.Data;

@Data
public class AccountUpdatedEvent {

    private Long accountNumber;
    private String accountType;
    private String branchAddress;
    private String mobileNumber;
    private boolean activeSw;
}
