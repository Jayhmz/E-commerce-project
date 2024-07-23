package com.plantationhub.wesesta.authentication.service.token;

import com.plantationhub.wesesta.authentication.model.AppUser;
import com.plantationhub.wesesta.authentication.model.Token;

import java.util.Map;

public interface TokenService {
    int generateToken();
    void savePhoneToken(int token, AppUser user);
    void saveMailToken(int token, AppUser user);
    void verifyMailToken(Token token);
    void verifyPhoneToken(Token token);
    void updateMailToken(int token, String email);

    Map<String, Object> findByUserMail(String email);
}
