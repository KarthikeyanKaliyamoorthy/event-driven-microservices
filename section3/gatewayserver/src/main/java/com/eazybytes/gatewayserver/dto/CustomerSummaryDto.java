package com.eazybytes.gatewayserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSummaryDto {
    private CustomerDto customerDto;
    private AccountsDto accountsDto;
    private CardsDto cardsDto;
    private LoansDto loansDto;
}
