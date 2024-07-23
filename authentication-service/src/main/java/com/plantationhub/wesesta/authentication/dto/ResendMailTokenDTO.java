package com.plantationhub.wesesta.authentication.dto;

import com.plantationhub.wesesta.authentication.validator.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendMailTokenDTO {
    @NotNull(message = "Email field cannot be null")
    @ValidEmail
    private String email;
}
