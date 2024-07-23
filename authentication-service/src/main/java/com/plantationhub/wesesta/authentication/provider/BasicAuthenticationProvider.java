package com.plantationhub.wesesta.authentication.provider;

import com.plantationhub.wesesta.authentication.service.registration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getPrincipal().toString();
        var password = authentication.getCredentials().toString();

        var userDetails = userService.findByPhoneOrEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Incorrect username/password"));
        if (passwordEncoder.matches(password, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.singleton(new SimpleGrantedAuthority(userDetails.getRole().name())));
        }else{
            throw new BadCredentialsException("Incorrect username/password");
        }
    }

/** Use this getRoles() if the model changes from (one to one ) to (one to many) relationship with the AppUser */
   /* private List<GrantedAuthority> getRoles(AppUser userDetails) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(userDetails.getRole().name());
        authorities.add(role);
        return authorities;
    }*/

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
