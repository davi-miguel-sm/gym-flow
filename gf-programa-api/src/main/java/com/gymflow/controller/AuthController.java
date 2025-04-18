package com.gymflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gymflow.service.AuthService;
import com.gymflow.dto.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @RequestMapping("/register")
  public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
    authService.registerUser(request);

    return ResponseEntity.ok().build();
  }
}
