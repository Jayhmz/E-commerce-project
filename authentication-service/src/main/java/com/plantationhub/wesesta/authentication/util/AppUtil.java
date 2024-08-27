package com.plantationhub.wesesta.authentication.util;

import com.plantationhub.wesesta.authentication.model.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AppUtil {
    public static AppUser getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AppUser) authentication.getPrincipal();
    }
}
