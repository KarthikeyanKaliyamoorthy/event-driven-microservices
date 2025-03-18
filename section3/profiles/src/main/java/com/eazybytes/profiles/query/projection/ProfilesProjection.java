package com.eazybytes.profiles.query.projection;

import com.eazybytes.common.event.AccountDataChangedEvent;
import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.profiles.service.IProfilesService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("customer-group")
public class ProfilesProjection {

    private final IProfilesService iProfilesService;

    @EventHandler
    public void on(CustomerDataChangedEvent customerDataChangedEvent) {
        iProfilesService.handleCustomerDataChangedEvent(customerDataChangedEvent);
    }

    @EventHandler
    public void on(AccountDataChangedEvent accountDataChangedEvent) {
        iProfilesService.handleAccountDataChangedEvent(accountDataChangedEvent);
    }

}
