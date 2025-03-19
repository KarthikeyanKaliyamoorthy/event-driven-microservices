package com.eazybytes.customer.functions;

import com.eazybytes.common.dto.MobileNumberUpdateDto;
import com.eazybytes.customer.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CustomerFunctions {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateMobileNumberStatus(){
        return mobileNumberUpdateDto ->
            log.info("Received message from input channel: update-mobile-number-status with payload: {}", mobileNumberUpdateDto);

    }

    @Bean
    public Consumer<MobileNumberUpdateDto> rollBackCustomerMobileNumber(ICustomerService iCustomerService){
        return mobileNumberUpdateDto ->
        {
            log.info("Received message from input channel: rollback-customer-mobile-number with payload: {}", mobileNumberUpdateDto);
            iCustomerService.rollbackMobileNumber(mobileNumberUpdateDto);
        };
    }
}
