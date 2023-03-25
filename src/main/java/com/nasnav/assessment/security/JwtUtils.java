package com.nasnav.assessment.security;

import com.nasnav.assessment.error.UnAuthenticatedException;
import com.nasnav.assessment.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final Logger theLogger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${serenityTraders.app.jwtSecret}")
    private String jwtSecret;

    @Value("${serenityTraders.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(Authentication theAuthentication) {

        UserDetailsImpl theVisitorPrincipal = (UserDetailsImpl) theAuthentication.getPrincipal();

        return Jwts.builder().setSubject(theVisitorPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String generateTempToken(String userName) {

        return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException excep) {
            theLogger.error("Invalid JWT signature: {}", excep.getMessage());
        } catch (MalformedJwtException e) {
            theLogger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            theLogger.error("JWT token is expired: {}", e.getMessage());
            throw new UnAuthenticatedException("Token Expired, Please login again");
        } catch (UnsupportedJwtException e) {
            theLogger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            theLogger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}