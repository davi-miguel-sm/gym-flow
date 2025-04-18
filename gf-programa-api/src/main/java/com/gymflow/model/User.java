package com.gymflow.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "gym")
public class User {

  @Id
  @GeneratedValue
  private UUID id;

  private String username;
  private String password;
  private String email;
  private String bio;
  private String profilePic;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

}
