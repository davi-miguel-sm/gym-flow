package com.gymflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gymflow.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {

  List<Exercise> findByMuscleGroup(@Param("muscleGroup") String muscleGroup);

}