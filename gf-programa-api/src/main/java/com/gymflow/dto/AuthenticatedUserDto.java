package com.gymflow.dto;

import java.util.UUID;

import com.gymflow.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserDto {
  private UUID id;
  private String username;
  private String email;
  private Role role;
}
