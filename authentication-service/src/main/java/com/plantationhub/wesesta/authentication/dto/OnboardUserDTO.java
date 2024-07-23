package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidEmail;
import com.plantationhub.wesesta.authentication.validator.ValidPassword;
import com.plantationhub.wesesta.authentication.validator.ValidPhone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OnboardUserDTO {

    @JsonProperty("firstname")
    @NotNull(message = "Firstname field cannot be null")
    @Size(min = 3, message = "Firstname is too short")
    private String firstname;

    @JsonProperty("phone")
    @NotNull(message = "Phone field cannot be null")
    @ValidPhone
    private String Phone;

    @JsonProperty("email")
    @NotNull(message = "Email field cannot be null")
    @ValidEmail
    private String email;

    @JsonProperty("password")
    @NotNull(message = "Password field cannot be null")
    @ValidPassword
    private String password;
}
