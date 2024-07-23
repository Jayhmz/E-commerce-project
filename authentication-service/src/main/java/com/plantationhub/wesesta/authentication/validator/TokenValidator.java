package com.plantationhub.wesesta.authentication.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TokenValidator implements ConstraintValidator<ValidToken, String> {

    private static final String TOKEN = "\\d{6}$";
    private Pattern pattern = Pattern.compile(TOKEN);

    @Override
    public void initialize(ValidToken constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return pattern.matcher(value).matches();
    }
}
