package com.eazybytes.accounts.function;

import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.common.dto.MobileNumberUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class AccountFunctions {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateAccountMobileNumber(IAccountsService iAccountsService) {
        return mobileNumberUpdateDto -> {
            log.info("Received message from input channel: update-account-mobile-number with payload: {}", mobileNumberUpdateDto);
            iAccountsService.updateMobileNumber(mobileNumberUpdateDto);
        };
    }

    @Bean
    public Consumer<MobileNumberUpdateDto> rollbackAccountMobileNumber(IAccountsService iAccountsService) {
        return mobileNumberUpdateDto -> {
            log.info("Received message from input channel: rollback-account-mobile-number with payload: {}", mobileNumberUpdateDto);
            iAccountsService.rollbackMobileNumber(mobileNumberUpdateDto);
        };
    }
}
