package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidPassword;
import lombok.Data;

@Data
public class ChangeOldPasswordDTO {
    @JsonProperty("password")
    @ValidPassword
    private String password;
    @JsonProperty("confirm-password")
    @ValidPassword
    private String confirmPassword;
}
