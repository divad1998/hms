package com.ingryd.hms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    //@Value("${hms.jwt.secret_key}")
    private final static String SECRET_KEY = "5367566B59703373357638792F423F4528482B4D6251655468576D5A71347437";
    private Set<String> tokenBlackList = new HashSet<>(); //ToDo: refactor to store in the db or cache

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(UserDetails userDetails){
        return createFreshToken(new HashMap<>(), userDetails);
    }

    private String createFreshToken(Map<String, Object> mapOfClaims, UserDetails userDetails) {
        return Jwts.builder()
                .addClaims(mapOfClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .setIssuer("Ingryd's Hospital Management System")
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractClaims(token, Claims::getSubject);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);

    }

    public void invalidateToken(String token) {
        tokenBlackList.add(token);
    }

    /**
     * Checks storage whether token is blacklisted.
     * @param token
     * @return
     */
    public boolean isTokenBlacklisted(String token) {
        return tokenBlackList.contains(token);
    }
}
