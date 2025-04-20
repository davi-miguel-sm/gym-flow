package com.gymflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymflow.dto.LoginResponse;
import com.gymflow.dto.RegisterRequest;
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

  @RequestMapping("/register")
  @Operation(summary = "{auth.register.summary}", description = "{auth.register.description}")
  public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
    authService.register(request);

    return ResponseEntity.ok().build();
  }

  @RequestMapping("/login")
  @Operation(summary = "{auth.login.summary}", description = "{auth.login.description}")
  public ResponseEntity<LoginResponse> login(@RequestBody RegisterRequest request) {
    LoginResponse token = authService.login(request);

    return ResponseEntity.ok(token);
  }

  @RequestMapping("/me")
  @Operation(summary = "{auth.me.summary}", description = "{auth.me.description}")
  public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(user);
  }

}
