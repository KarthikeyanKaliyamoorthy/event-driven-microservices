package com.eazybytes.profiles.service;


import com.eazybytes.common.event.AccountDataChangedEvent;
import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.profiles.dto.ProfilesDto;

public interface IProfilesService {


    /**
     * @param customerDataChangedEvent - CustomerDataChangedEvent Object
     *
     */
    void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent);


    /**
     * @param accountDataChangedEvent - AccountDataChangedEvent Object
     *
     */
    void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent);
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    ProfilesDto fetchProfiles(String mobileNumber);
}
