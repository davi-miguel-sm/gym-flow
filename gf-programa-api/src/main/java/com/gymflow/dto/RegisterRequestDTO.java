package com.gymflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
  private String username;
  private String email;
  private String password;
  private String bio;
  private String profilePic;
}
