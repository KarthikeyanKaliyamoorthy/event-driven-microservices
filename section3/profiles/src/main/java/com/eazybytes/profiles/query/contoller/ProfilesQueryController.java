package com.eazybytes.profiles.query.contoller;


import com.eazybytes.profiles.dto.ProfilesDto;
import com.eazybytes.profiles.query.FindProfilesQuery;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class ProfilesQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/profiles")
    public ResponseEntity<ProfilesDto> fetchProfilesDetails(@RequestParam("mobileNumber")
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        FindProfilesQuery findProfilesQuery = new FindProfilesQuery(mobileNumber);
        ProfilesDto profilesDto = queryGateway.query(findProfilesQuery, ResponseTypes.instanceOf(ProfilesDto.class)).join();
        return ResponseEntity.status(HttpStatus.OK).body(profilesDto);
    }
}
