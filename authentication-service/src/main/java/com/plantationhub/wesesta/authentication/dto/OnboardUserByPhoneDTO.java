package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidPhone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OnboardUserByPhoneDTO {

    @JsonProperty("firstname")
    @NotNull(message = "Firstname field cannot be null")
    @Size(min = 3, message = "Firstname is too short")
    private String firstname;

    @JsonProperty("lastname")
    @NotNull(message = "Lastname field cannot be null")
    @Size(min = 3, message = "Lastname is too short")
    private String lastname;

    @JsonProperty("phone")
    @NotNull(message = "Phone field cannot be null")
    @ValidPhone
    private String phone;

    @JsonProperty("password")
    @NotNull(message = "Password field cannot be null")
    private String password;
}
