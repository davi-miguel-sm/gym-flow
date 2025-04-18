package com.gymflow.model;

import java.util.UUID;

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
@Table(name = "workout_sets", schema = "gym")
public class WorkoutSet {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "log_id", referencedColumnName = "id")
  private WorkoutLog log;

  private Integer setNumber;
  private Integer reps;
  private Double weight;

}
