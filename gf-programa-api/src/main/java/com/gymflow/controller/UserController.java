package com.gymflow.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymflow.model.User;
import com.gymflow.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @Operation(summary = "Get user by id")
  public List<User> getUserById() {
    logger.info("Get all users");
    return this.userService.getAllUsers();
  }
}
