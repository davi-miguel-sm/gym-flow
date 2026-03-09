package com.gymflow.service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gymflow.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.accessSecret}")
  private String accessSecret;

  @Value("${jwt.refreshSecret}")
  private String refreshSecret;

  protected Key getAccessKey() {
    byte[] keyBytes = Decoders.BASE64.decode(accessSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  protected Key getRefreshKey() {
    byte[] keyBytes = Decoders.BASE64.decode(refreshSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId());
    claims.put("email", user.getEmail());
    claims.put("role", user.getRole().name());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(Date.from(Instant.now().plusSeconds(900)))
        .signWith(getAccessKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(User user) {

    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(Date.from(Instant.now().plusSeconds(86400)))
        .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public Claims extractAllClaims(String token) {

    try {
      return Jwts.parserBuilder()
          .setSigningKey(getAccessKey())
          .build()
          .parseClaimsJws(token)
          .getBody();

    } catch (JwtException e) {
      throw new JwtException("Token inválido");
    }
  }

  public boolean validateRefreshToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getRefreshKey())
          .build()
          .parseClaimsJws(token);

      return true;

    } catch (JwtException e) {
      return false;
    }
  }

  public String extractUsernameFromRefreshToken(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(getRefreshKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

}
