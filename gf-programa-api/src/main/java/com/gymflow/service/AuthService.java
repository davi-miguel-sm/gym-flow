package com.gymflow.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gymflow.dto.LoginRequestDto;
import com.gymflow.dto.LoginResponseDto;
import com.gymflow.dto.RefreshRequestDto;
import com.gymflow.dto.RegisterRequestDto;
import com.gymflow.enums.Role;
import com.gymflow.exception.Errors;
import com.gymflow.model.User;
import com.gymflow.repository.UserRepository;

import jakarta.transaction.Transactional;

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

  @Transactional
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
    user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.USER);

    userRepository.save(user);
  }

  public LoginResponseDto login(LoginRequestDto request) {
    Optional<User> user = userRepository.findByEmail(request.getLogin());
    if (user.isEmpty()) {
      user = userRepository.findByUsername(request.getLogin());
      if (user.isEmpty()) {
        throw new Errors.ErrorUserNotFound();
      }
    }

    if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
      throw new Errors.ErrorIncorrectPassword();
    }

    String accessToken = jwtService.generateAccessToken(user.get());
    String refreshToken = jwtService.generateRefreshToken(user.get());
    return new LoginResponseDto(accessToken, refreshToken);
  }

  public LoginResponseDto refreshToken(RefreshRequestDto request) {

    String refreshToken = request.getRefreshToken();

    if (!jwtService.validateRefreshToken(refreshToken)) {
      throw new RuntimeException("Refresh token inválido");
    }

    String username = jwtService.extractUsernameFromRefreshToken(refreshToken);

    User user = userRepository.findByUsername(username)
        .orElseThrow();

    String newAccessToken = jwtService.generateAccessToken(user);
    String newRefreshToken = jwtService.generateRefreshToken(user);

    return new LoginResponseDto(newAccessToken, newRefreshToken);
  }

}
