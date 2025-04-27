package com.gymflow.model;

import java.util.UUID;

import com.gymflow.enums.MuscleGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercises", schema = "gym")
@Data
@NoArgsConstructor
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "muscle_group", nullable = false, columnDefinition = "gym.muscle_group")
  private MuscleGroup muscleGroup;

  @Column(name = "name_en", nullable = false)
  private String nameEn;

  @Column(name = "name_pt", nullable = false)
  private String namePt;

  @Column(name = "description_en", nullable = false)
  private String descriptionEn;

  @Column(name = "description_pt", nullable = false)
  private String descriptionPt;
}
