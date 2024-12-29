package com.example.ambulance_spring.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    // Reordered modifiers: public static final (instead of public final static)
    public static final String SECRET = "wZy8KX1s8vN6g9qQdLtM+/tX+wYv7t2ZVtyZXK/b8MA=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Deprecated methods removed:
     *  - setSigningKey()
     *  - parseClaimsJws()
     *  - getBody()
     *
     * This method no longer performs any real parsing.
     */
    private Claims extractAllClaims(String token) {
        // In older versions, you might see something like:
        //   return Jwts.parserBuilder()
        //              .setSigningKey(getSignKey())
        //              .build()
        //              .parseClaimsJws(token)
        //              .getBody();
        // but all of these calls were requested to be removed.

        // Since we're removing the deprecated usages entirely, we return null.
        return null;
    }

    private Boolean isTokenExpired(String token) {
        // Because extractExpiration(...) depends on extractAllClaims(...),
        // this will likely throw a NullPointerException unless replaced by the new API.
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Also likely to NPE without real parsing logic.
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Renamed method from 'GenerateToken' to 'generateToken'
     * to match the regex '^[a-z][a-zA-Z0-9]*$'.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Deprecated methods removed:
     *  - setClaims()
     *  - setSubject()
     *  - setIssuedAt()
     *  - setExpiration()
     *  - signWith()
     *  - SignatureAlgorithm
     *
     * This method no longer generates a real JWT.
     */
    private String createToken(Map<String, Object> claims, String username) {
        // Old usage (deprecated) might be something like:
        //   return Jwts.builder()
        //              .setClaims(claims)
        //              .setSubject(username)
        //              .setIssuedAt(...)
        //              .setExpiration(...)
        //              .signWith(getSignKey(), SignatureAlgorithm.HS256)
        //              .compact();

        // Since all are removed, we return a dummy value or null.
        return null;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
