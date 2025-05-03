package com.gymflow.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Column(name = "muscle_group", nullable = false)
  private String muscleGroup;

  @Column(name = "name_en", nullable = false)
  private String nameEn;

  @Column(name = "name_pt", nullable = false)
  private String namePt;

  @Column(name = "description_en", nullable = false)
  private String descriptionEn;

  @Column(name = "description_pt", nullable = false)
  private String descriptionPt;
}
