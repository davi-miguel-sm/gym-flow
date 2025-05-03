package com.gymflow.security;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gymflow.model.User;
import com.gymflow.repository.UserRepository;
import com.gymflow.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Autowired
  public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    LOGGER.debug("Checking authentication for request: {}", request.getRequestURI());
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      LOGGER.debug("No Bearer token found in request.");
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);
    Claims claims = jwtService.extractAllClaims(token);

    if (claims == null) {
      sendUnauthorizedResponse(response, "Invalid or expired token");
      return;
    }

    if (SecurityContextHolder.getContext().getAuthentication() == null) {

      String email = claims.get("email", String.class);
      Optional<User> userOpt = userRepository.findByEmail(email);

      if (userOpt.isEmpty()) {
        sendUnauthorizedResponse(response, "User not found");
        return;
      }

      String role = Optional.ofNullable(claims.get("role", String.class))
          .map(String::toUpperCase)
          .orElse("USER");

      User user = userOpt.get();
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          user, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)) // Add roles if needed
      );
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);

    }

    filterChain.doFilter(request, response);
  }

  private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
  }
}
