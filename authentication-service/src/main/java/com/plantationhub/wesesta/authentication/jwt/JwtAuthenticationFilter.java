package com.plantationhub.wesesta.authentication.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestHeader = request.getHeader("Authorization");
        if (requestHeader != null ) {
            String token;

            if (requestHeader.startsWith("Bearer ")) {
                token = requestHeader.split(" ")[1].trim();
            } else {
                token = requestHeader.trim();
            }

            var authenticatedToken = jwtUtil.validateToken(token);
            if (authenticatedToken != null) {
                var authenticationToken = new UsernamePasswordAuthenticationToken(authenticatedToken.getFirstname(),
                        null, Collections.singleton(new SimpleGrantedAuthority(authenticatedToken.getRole())));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

/** Use this getRoles() if the model changes from (one to one ) to (one to many) relationship with the AppUser */
   /* private List<GrantedAuthority> getRoles(AppUser userDetails) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(userDetails.getRole().name());
        authorities.add(role);
        return authorities;
    }*/

}
