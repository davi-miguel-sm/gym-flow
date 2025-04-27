package com.gymflow.model;

import java.util.UUID;

import com.gymflow.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercise_media", schema = "gym")
public class ExercisesMedia {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String imageUrl;
  private String videoUrl;
  private Boolean isVideo;
  private Integer orderIndex;
}
