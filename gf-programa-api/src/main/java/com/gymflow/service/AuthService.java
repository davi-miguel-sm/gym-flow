package com.gymflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gymflow.dto.LoginResponseDto;
import com.gymflow.dto.RegisterRequestDto;
import com.gymflow.exception.Errors;
import com.gymflow.model.User;
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

  public void register(RegisterRequestDto request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new Errors.ErrorEmailAlreadyExists();
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setBio(request.getBio());
    user.setProfilePic(request.getProfilePic());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);
  }

  public LoginResponseDto login(RegisterRequestDto request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(Errors.ErrorUserNotFound::new);

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new Errors.ErrorIncorrectPassword();
    }

    String token = jwtService.generateToken(user);
    return new LoginResponseDto(token);
  }

}
