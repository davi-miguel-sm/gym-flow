package com.gymflow.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
  private String token;

  public LoginResponseDTO(String token) {
    this.token = token;
  }
}
