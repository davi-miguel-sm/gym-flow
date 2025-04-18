package com.gymflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import com.gymflow.model.User;
import com.gymflow.service.UserService;

@RestController
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/{id}")
  @Operation(summary = "Get user by id")
  public Integer getUserById(@PathVariable String id) {
    return this.userService.getOne();
  }
}
