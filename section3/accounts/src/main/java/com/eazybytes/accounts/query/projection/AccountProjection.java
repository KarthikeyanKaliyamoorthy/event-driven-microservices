package com.eazybytes.accounts.query.projection;

import com.eazybytes.accounts.comand.event.AccountCreatedEvent;
import com.eazybytes.accounts.comand.event.AccountDeletedEvent;
import com.eazybytes.accounts.comand.event.AccountUpdatedEvent;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("account-group")
public class AccountProjection {
    private final IAccountsService iAccountsService;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Accounts accounts = new Accounts();
        BeanUtils.copyProperties(accountCreatedEvent, accounts);
        iAccountsService.createAccount(accounts);
    }

    @EventHandler
    public void on(AccountUpdatedEvent event) {
        iAccountsService.updateAccount(event);
    }

    @EventHandler
    public void on(AccountDeletedEvent event) {
        iAccountsService.deleteAccount(event.getAccountNumber());
    }
}
