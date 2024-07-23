package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidPhone;
import com.plantationhub.wesesta.authentication.validator.ValidToken;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidatePhoneDTO {
    @JsonProperty("phone")
    @ValidPhone
    @NotNull
    private String phone;

    @JsonProperty("token")
    @NotNull
    @ValidToken
    private String token;
}
