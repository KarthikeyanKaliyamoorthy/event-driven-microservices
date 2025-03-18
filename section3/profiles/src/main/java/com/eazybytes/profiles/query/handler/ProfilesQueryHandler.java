package com.eazybytes.profiles.query.handler;


import com.eazybytes.profiles.dto.ProfilesDto;
import com.eazybytes.profiles.query.FindProfilesQuery;
import com.eazybytes.profiles.service.IProfilesService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfilesQueryHandler {

    private final IProfilesService iProfilesService;

    @QueryHandler
    public ProfilesDto findCustomer(FindProfilesQuery findProfilesQuery) {
        return iProfilesService.fetchProfiles(findProfilesQuery.getMobileNumber());
    }
}
