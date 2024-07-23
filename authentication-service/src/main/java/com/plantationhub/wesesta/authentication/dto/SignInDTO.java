package com.plantationhub.wesesta.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInDTO {

    @JsonProperty("username")
    @NotNull(message = "Username field cannot be null")
    private String username;

    @JsonProperty("password")
    @NotNull(message = "Password field cannot be null")
    private String password;
}
