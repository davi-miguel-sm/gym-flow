package com.gymflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gymflow.model.User;
import com.gymflow.dto.RegisterRequest;
import com.gymflow.repository.UserRepository;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void registerUser(RegisterRequest request) {
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

}
