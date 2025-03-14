package com.eazybytes.gatewayserver.handler;

import com.eazybytes.gatewayserver.dto.*;
import com.eazybytes.gatewayserver.service.clinet.CustomerSummaryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerCompositeHandler {

    private final CustomerSummaryClient customerSummaryClient;

    public Mono<ServerResponse> fetchCustomerSumary(ServerRequest serverRequest) {
        String mobileNumber = serverRequest.queryParam("mobileNumber").get();

        Mono<ResponseEntity<CustomerDto>> customerDetails = customerSummaryClient.fetchCustomerDetails(mobileNumber);
        Mono<ResponseEntity<AccountsDto>> accountDetails = customerSummaryClient.fetchAccountDetails(mobileNumber);
        Mono<ResponseEntity<CardsDto>> cardDetails = customerSummaryClient.fetchCardDetails(mobileNumber);
        Mono<ResponseEntity<LoansDto>> loanDetails = customerSummaryClient.fetchLoanDetails(mobileNumber);

        return Mono.zip(customerDetails, accountDetails, cardDetails, loanDetails)
                .flatMap(tuple -> {
                    CustomerDto customerDto = tuple.getT1().getBody();
                    AccountsDto accountsDto = tuple.getT2().getBody();
                    CardsDto cardsDto = tuple.getT3().getBody();
                    LoansDto loansDto = tuple.getT4().getBody();

                    CustomerSummaryDto customerSummaryDto = new CustomerSummaryDto(customerDto, accountsDto, cardsDto, loansDto);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(customerSummaryDto));
                });
    }
}
