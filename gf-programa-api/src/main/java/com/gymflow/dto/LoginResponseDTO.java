package com.gymflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDto {
  private String accessToken;
  private String refreshToken;

}
