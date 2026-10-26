package com.example.UniWork.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "C7m#4qW!vL9@tH2^pF8&sJ3*zK1%yN5(";

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String  userName){
        Map<String, String> claims = new HashMap<>();
        return createToken(claims,userName);
    }
    public String createToken(Map<String,String> claims,String userName){
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1440))
                .signWith(getSignKey())
                .compact();

    }

    public String extractUserName(String token){
        return extractAllClaims(token).getSubject();
    }


    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    public Date getExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public Boolean isExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token){
        return !isExpired(token);
    }

}
