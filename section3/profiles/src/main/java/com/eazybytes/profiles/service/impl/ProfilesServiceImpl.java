package com.eazybytes.profiles.service.impl;

import com.eazybytes.common.event.AccountDataChangedEvent;
import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.profiles.constants.ProfilesConstants;
import com.eazybytes.profiles.dto.ProfilesDto;
import com.eazybytes.profiles.entity.Profile;
import com.eazybytes.profiles.exception.ResourceNotFoundException;
import com.eazybytes.profiles.mapper.ProfilesMapper;
import com.eazybytes.profiles.repository.ProfilesRepository;
import com.eazybytes.profiles.service.IProfilesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfilesServiceImpl implements IProfilesService {

    private ProfilesRepository profilesRepository;

    @Override
    public void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent) {
        Profile profile = profilesRepository.findByMobileNumberAndActiveSw(customerDataChangedEvent.getMobileNumber(),
                ProfilesConstants.ACTIVE_SW).orElseGet(Profile::new);

        profile.setMobileNumber(customerDataChangedEvent.getMobileNumber());
        if (customerDataChangedEvent.getName() != null) {
            profile.setName(customerDataChangedEvent.getName());
        }
        profile.setActiveSw(customerDataChangedEvent.isActiveSw());
        profilesRepository.save(profile);
    }

    /**
     * @param accountDataChangedEvent - AccountDataChangedEvent Object
     */
    @Override
    public void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent) {
        Profile profile = profilesRepository.findByMobileNumberAndActiveSw(accountDataChangedEvent.getMobileNumber(),
                ProfilesConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Accounts", "mobileNumber", accountDataChangedEvent.getMobileNumber())
        );

        profile.setAccountNumber(accountDataChangedEvent.getAccountNumber());
        profilesRepository.save(profile);
    }

    public ProfilesDto fetchProfiles(String mobileNumber) {
        Profile profile = profilesRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Profile", "mobileNumber", mobileNumber)
        );
        return ProfilesMapper.mapToProfileDto(profile, new ProfilesDto());

    }

}
