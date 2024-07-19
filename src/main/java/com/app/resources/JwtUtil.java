package com.app.resources;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Component
public class JwtUtil {

  @Value("${security.jwt.key.private}")
  private String privateKey;

  @Value("${security.jwt.user.generator}")
  private String userGenerator;

  public String generateToken (Authentication authentication){
    Algorithm algorithm = Algorithm.HMAC256(privateKey);

    String username = authentication.getPrincipal().toString();

    String authorities = authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

    String token = JWT.create()
                      .withIssuer(this.userGenerator)
                      .withSubject(username)
                      .withClaim("authorities", authorities)
                      .withIssuedAt(new Date())
                      .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                      .withJWTId(UUID.randomUUID().toString())
                      .withNotBefore(new Date(System.currentTimeMillis()))
                      .sign(algorithm);
    return token;
  }

}
