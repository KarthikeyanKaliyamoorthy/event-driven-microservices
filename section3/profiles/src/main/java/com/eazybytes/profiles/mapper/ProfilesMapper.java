package com.eazybytes.profiles.mapper;


import com.eazybytes.profiles.dto.ProfilesDto;
import com.eazybytes.profiles.entity.Profile;

public class ProfilesMapper {


    public static ProfilesDto mapToProfileDto(Profile profile, ProfilesDto profileDto) {
        profileDto.setName(profile.getName());
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setAccountNumber(profile.getAccountNumber());
        profileDto.setLoanNumber(profile.getLoanNumber());
        profileDto.setCardNumber(profile.getCardNumber());
        return profileDto;
    }
}
