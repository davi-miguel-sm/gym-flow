package com.gymflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gymflow.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
  @Query(value = "SELECT * FROM gym.exercises WHERE muscle_group = CAST(:muscleGroup AS gym.muscle_group)", nativeQuery = true)
  List<Exercise> findByMuscleGroup(@Param("muscleGroup") String muscleGroup);
}