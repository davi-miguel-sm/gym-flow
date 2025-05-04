package com.gymflow.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class ExerciseMedia {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private String imageUrl;

  private String videoUrl;

  @Column(nullable = false)
  private Boolean isVideo;

  @Column(nullable = false)
  private Integer orderIndex;
}
