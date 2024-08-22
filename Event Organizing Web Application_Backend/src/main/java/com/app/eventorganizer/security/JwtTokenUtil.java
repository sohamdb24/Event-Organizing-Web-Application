package com.app.eventorganizer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.eventorganizer.entity.User;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration;
    
    @Value("${jwt.secret}")
    private String secret;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        if (secret == null || secret.length() < 32) {
            logger.warn("Provided secret is null or too short. Generating a secure key.");
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        } else {
            logger.info("Using provided secret key for JWT.");
            this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        }
    }

  
    public String generateToken(User user) {
        try {
            logger.info("Generating token for user: {}", user.getEmail());
            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("roleId", user.getRole().getRoleId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(secretKey, SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating token: {}", e.getMessage(), e);
            return null;
        }
    }

    
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    
    public List<String> getRolesFromToken(String token) {
//        return getClaimFromToken(token, claims -> claims.get("roles", List.class));
    	return getClaimFromToken(token, claims -> claims.get("roles", List.class));
    }


   
    public boolean validateToken(String token, UserDetails user) {
        String userName = getUsernameFromToken(token);
        return userName.equals(user.getUsername()) && !isTokenExpired(token);
    }
   
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


   
    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

  
    private Date getExpirationDateFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}

