package ru.clevertec.user_service.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.user_service.exception.code.JwtExceptionCode;
import ru.clevertec.user_service.exception.message.JwtExceptionMessage;
import ru.clevertec.user_service.service.JwtService;

import java.security.Key;
import java.util.Date;

/**
 * Implementation of JWT service.
 */
@Service
public class JwtServiceImpl implements JwtService {

    /**
     * Secret key used to sign and verify tokens.
     */
    @Value("${spring.security.secret}")
    private String secret;

    /**
     * Issuer of the tokens.
     */
    @Value("${spring.security.issuer}")
    private String issuer;

    /**
     * Expiration time of the tokens, in milliseconds.
     */
    @Value("${spring.security.expiration}")
    private Long expiration;

    /**
     * Generates a JSON Web Token (JWT) for the specified username.
     *
     * @param username the username
     * @return the JWT
     */
    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Returns the username associated with the specified JWT.
     *
     * @param token the JWT
     * @return the username
     */
    @Override
    public String getUserName(String token) {
        validToken(token);
        return extractClaims(token).getSubject();
    }

    /**
     * Validates the specified JWT and throws an exception if it is invalid.
     *
     * @param token the JWT
     */
    private void validToken(String token) {
        if (isTokenExpired(token)) {
            throw new ApplicationException(JwtExceptionCode.INVALID_JWT, JwtExceptionMessage.INVALID_JWT);
        }
    }

    /**
     * Checks if the specified JWT has expired.
     *
     * @param token the JWT
     * @return true if the JWT has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extracts the claims from the specified JWT.
     *
     * @param token the JWT
     * @return the claims
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Returns the signing key used to sign and verify tokens.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] decode = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }
}