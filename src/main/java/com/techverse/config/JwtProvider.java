package com.techverse.config;
   

import java.util.Date;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techverse.model.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtProvider {
	
	private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private final int jwtExpirationInMs = 604800000; // 7 days
	
	public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret, SignatureAlgorithm.HS512) // Using the key directly

                .compact();
    }

	public String getUserIdFromJWT(String token) {
		
	    Claims claims = Jwts.parser()
	            .setSigningKey(jwtSecret)
	            .parseClaimsJws(token)
	            .getBody();
	    System.out.println("jhfgdjfghjdf");
	    return claims.getSubject();
	}

    public boolean validateToken(String authToken) {
    	 
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            System.out.println("vali");
            
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
        	ex.printStackTrace();
            return false;
        }
    }
}