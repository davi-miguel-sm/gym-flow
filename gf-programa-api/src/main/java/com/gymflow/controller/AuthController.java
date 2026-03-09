package com.gymflow.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymflow.dto.AuthenticatedUserDto;
import com.gymflow.dto.LoginRequestDto;
import com.gymflow.dto.LoginResponseDto;
import com.gymflow.dto.RefreshRequestDto;
import com.gymflow.dto.RegisterRequestDto;
import com.gymflow.model.User;
import com.gymflow.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  @Operation(summary = "{auth.register.summary}", description = "{auth.register.description}")
  public ResponseEntity<Void> register(
      @RequestBody RegisterRequestDto request) {

    authService.register(request);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  @Operation(summary = "{auth.login.summary}", description = "{auth.login.description}")
  public ResponseEntity<LoginResponseDto> login(
      @RequestBody LoginRequestDto request) {

    LoginResponseDto response = authService.login(request);

    ResponseCookie cookie = ResponseCookie.from("refreshToken", response.getRefreshToken())
        .httpOnly(true)
        .secure(true)
        .path("/auth/refresh")
        .maxAge(Duration.ofDays(1))
        .sameSite("Strict")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new LoginResponseDto(response.getAccessToken(), response.getRefreshToken()));
  }

  @GetMapping("/me")
  @Operation(summary = "{auth.me.summary}", description = "{auth.me.description}")
  public AuthenticatedUserDto me(Authentication authentication) {

    User user = (User) authentication.getPrincipal();

    return new AuthenticatedUserDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getRole());
  }

  @PostMapping("/refresh")
  @Operation(summary = "{auth.refresh.summary}", description = "{auth.refresh.description}")
  public ResponseEntity<LoginResponseDto> refresh(
      @RequestBody RefreshRequestDto request) {

    return ResponseEntity.ok(authService.refreshToken(request));
  }

}
