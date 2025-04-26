package com.gymflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
  private String login; // email or username
  private String password;
}
