package com.gymflow.model;

import java.util.UUID;

import com.gymflow.Enums.MuscleGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercises", schema = "gym")
public class Exercise {

  @Id
  private UUID id;

  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "muscle_group")
  private MuscleGroup muscleGroup;

  private String description;
}
