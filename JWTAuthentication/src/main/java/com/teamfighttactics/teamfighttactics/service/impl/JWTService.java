package com.teamfighttactics.teamfighttactics.service.impl;

import com.teamfighttactics.teamfighttactics.dto.AuthRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${jwt.key}")
    private String secret;
    private static final int validity = 1000 * 60 * 5 ;

    public String genereteToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        claims.put("firat","something");
        return createToken(claims,userName);

    }
    private String createToken( Map<String,Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+validity))
                .signWith(getSingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean tokenValidate(String token , UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsernameToken(token))
                &&  extractExpired(token) ;
    }
    public String extractUsernameToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    public boolean extractExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().
                setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token).
                getBody();
    }

    private Key getSingKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
