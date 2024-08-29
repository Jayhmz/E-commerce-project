package com.plantationhub.wesesta.authentication.service.token;

public interface ValidateTokenService {
    String validateToken(String token, String identifier);
}
