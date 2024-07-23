package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantationhub.wesesta.authentication.validator.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailDTO {

    @JsonProperty("email")
    @NotNull(message = "Email field cannot be null")
    @ValidEmail
    private String email;
}
