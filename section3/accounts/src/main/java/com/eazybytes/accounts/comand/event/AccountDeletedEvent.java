package com.eazybytes.accounts.comand.event;

import lombok.Data;

@Data
public class AccountDeletedEvent {
    private Long accountNumber;
    private boolean activeSw;
}
