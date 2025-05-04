package com.gymflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gymflow.model.ExerciseMedia;

public interface ExerciseMediaRepository extends JpaRepository<ExerciseMedia, UUID> {
  List<ExerciseMedia> findByExerciseIdOrderByOrderIndexAsc(UUID id);
}
