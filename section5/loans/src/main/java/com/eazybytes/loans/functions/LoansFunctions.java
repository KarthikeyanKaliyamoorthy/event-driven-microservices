package com.eazybytes.loans.functions;

import com.eazybytes.common.dto.MobileNumberUpdateDto;
import com.eazybytes.loans.service.ILoansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class LoansFunctions {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateLoansMobileNumber(ILoansService iLoansService) {
        return mobileNumberUpdateDto -> {
            log.info("Received message from input channel: update-loans-mobile-number with payload: {}", mobileNumberUpdateDto);
            iLoansService.updateMobileNumber(mobileNumberUpdateDto);
        };
    }
}
