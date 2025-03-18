package com.eazybytes.profiles.repository;

import com.eazybytes.profiles.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByMobileNumberAndActiveSw(String mobileNumber, boolean active);
}
