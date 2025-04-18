package com.gymflow.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workout_logs", schema = "gym")
public class WorkoutLog {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;

  private LocalDate workoutDate;
  private String notes;
  private String workoutPhoto;

  @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkoutSet> sets;

}
