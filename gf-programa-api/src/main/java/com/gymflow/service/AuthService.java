package com.gymflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gymflow.model.User;
import com.gymflow.dto.RegisterRequest;
import com.gymflow.dto.LoginResponse;
import com.gymflow.repository.UserRepository;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Autowired
  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public void register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("E-mail already exists.");
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setBio(request.getBio());
    user.setProfilePic(request.getProfilePic());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);
  }

  public LoginResponse login(RegisterRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found."));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Incorrect Password");
    }

    String token = jwtService.generateToken(user);
    return new LoginResponse(token);
  }

}
