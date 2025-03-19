package com.eazybytes.common.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MobileNumberUpdateDto {

    @NotEmpty(message = "Current mobile number is required")
    private String currentMobileNumber;
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    private String newMobileNumber;
}
