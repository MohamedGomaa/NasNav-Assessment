package com.nasnav.assessment.security;

import static com.nasnav.assessment.strings.ExceptionMessages.EMPTY_TOKEN_CLAIMS;
import static com.nasnav.assessment.strings.ExceptionMessages.EMPTY_TOKEN_CLAIMS_EXCEPTION;
import static com.nasnav.assessment.strings.ExceptionMessages.EXPIRED_TOKEN;
import static com.nasnav.assessment.strings.ExceptionMessages.EXPIRED_TOKEN_EXCEPTION;
import static com.nasnav.assessment.strings.ExceptionMessages.INVALID_TOKEN;
import static com.nasnav.assessment.strings.ExceptionMessages.INVALID_TOKEN_EXCEPTION;
import static com.nasnav.assessment.strings.ExceptionMessages.INVALID_TOKEN_SIGNATURE;
import static com.nasnav.assessment.strings.ExceptionMessages.INVALID_TOKEN_SIGNATURE_EXCEPTION;
import static com.nasnav.assessment.strings.ExceptionMessages.UNSUPPORTED_TOKEN;
import static com.nasnav.assessment.strings.ExceptionMessages.UNSUPPORTED_TOKEN_EXCEPTION;

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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {

  @Value("${nasnav.app.jwtSecret}")
  private String jwtSecret;

  @Value("${nasnav.app.jwtExpirationMs}")
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
    String errorMessage = "";
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error(INVALID_TOKEN_SIGNATURE, e.getMessage());
      errorMessage = INVALID_TOKEN_SIGNATURE_EXCEPTION;
    } catch (MalformedJwtException e) {
      log.error(INVALID_TOKEN, e.getMessage());
      errorMessage = INVALID_TOKEN_EXCEPTION;
    } catch (ExpiredJwtException e) {
      log.error(EXPIRED_TOKEN, e.getMessage());
      errorMessage = EXPIRED_TOKEN_EXCEPTION;
    } catch (UnsupportedJwtException e) {
      log.error(UNSUPPORTED_TOKEN, e.getMessage());
      errorMessage = UNSUPPORTED_TOKEN_EXCEPTION;
    } catch (IllegalArgumentException e) {
      log.error(EMPTY_TOKEN_CLAIMS, e.getMessage());
      errorMessage=EMPTY_TOKEN_CLAIMS_EXCEPTION;
    }
    throw new UnAuthenticatedException(errorMessage);
  }
}