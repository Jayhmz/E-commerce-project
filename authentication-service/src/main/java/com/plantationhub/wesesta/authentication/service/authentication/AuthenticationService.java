package com.plantationhub.wesesta.authentication.service.authentication;

import com.plantationhub.wesesta.authentication.dto.SignInDTO;

public interface AuthenticationService {
    String authenticate(SignInDTO signInDTO);
}
