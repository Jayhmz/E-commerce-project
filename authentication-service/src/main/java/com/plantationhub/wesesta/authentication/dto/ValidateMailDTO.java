package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidEmail;
import com.plantationhub.wesesta.authentication.validator.ValidToken;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidateMailDTO {
    @JsonProperty("email")
    @ValidEmail
    @NotNull
    private String email;

    @JsonProperty("token")
    @NotNull
    @ValidToken
    private String token;
}
