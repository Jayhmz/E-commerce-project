package com.plantationhub.wesesta.authentication.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String jwtSecretKey;
    @Autowired
    private ObjectMapper mapper;

    public String generateToken(JwtUserDetails jwtUserDetails, String jwtSecretKey){
        this.jwtSecretKey = jwtSecretKey;
        //1. set the claims you want to set for the issuer (phone, firstname, role)
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstname", jwtUserDetails.getFirstname());
        claims.put("role", jwtUserDetails.getRole());
        return createToken(claims);
    }
    public String generatePhoneJwtToken(JwtUserDetails jwtDetails, String jwtSecretKey){
        this.jwtSecretKey = jwtSecretKey;
        //1. set the claims you want to set for the issuer (phone, firstname, role)
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstname", jwtDetails.getFirstname());
        claims.put("role", jwtDetails.getRole());
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject((String) claims.get("firstname"))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignWithKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignWithKey() {
        byte[] keyByte = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public JwtUserDetails validateToken(String token){
        if (!isTokenExpired(token)) {
            HashMap<String, Object> tokenDetails = getTokenDetails(token);
            return getBasicDetails(tokenDetails);
        } else {
            throw new TokenExpiredException("The token has expired", Instant.now());
        }
    }

    public HashMap<String, Object> getTokenDetails(String token) {
        return new HashMap<>(extractAllClaims(token));
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignWithKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public JwtUserDetails getBasicDetails(HashMap<String, Object> details) {
        return mapper.convertValue(details, JwtUserDetails.class);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
