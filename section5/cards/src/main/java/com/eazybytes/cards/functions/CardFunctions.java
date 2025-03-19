package com.eazybytes.cards.functions;

import com.eazybytes.cards.service.ICardsService;
import com.eazybytes.common.dto.MobileNumberUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CardFunctions {

    @Bean
    public Consumer<MobileNumberUpdateDto> updateCardsMobileNumber(ICardsService iCardsService) {
        return mobileNumberUpdateDto -> {
            log.info("Received message from input channel: update-cards-mobile-number with payload: {}", mobileNumberUpdateDto);
            iCardsService.updateMobileNumber(mobileNumberUpdateDto);
        };
    }

    @Bean
    public Consumer<MobileNumberUpdateDto> rollbackCardsMobileNumber(ICardsService iCardsService) {
        return mobileNumberUpdateDto -> {
            log.info("Received message from input channel: rollback-cards-mobile-number with payload: {}", mobileNumberUpdateDto);
            iCardsService.rollbackMobileNumber(mobileNumberUpdateDto);
        };
    }

}
