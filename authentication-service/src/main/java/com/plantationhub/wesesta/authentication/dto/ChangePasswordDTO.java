package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidEmail;
import com.plantationhub.wesesta.authentication.validator.ValidPassword;
import com.plantationhub.wesesta.authentication.validator.ValidToken;
import lombok.Data;

@Data
public class ChangePasswordDTO {

    @JsonProperty("token")
    @ValidToken
    private String token;

    @JsonProperty("email")
    @ValidEmail
    private String email;

    @JsonProperty("password")
    @ValidPassword
    private String password; 

}
