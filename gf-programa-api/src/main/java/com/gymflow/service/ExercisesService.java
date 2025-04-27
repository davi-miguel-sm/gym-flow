package com.gymflow.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gymflow.dto.ExerciseDTO;
import com.gymflow.enums.MuscleGroup;
import com.gymflow.exception.Errors;
import com.gymflow.exception.Errors.MuscleGroupNotFound;
import com.gymflow.model.Exercise;
import com.gymflow.repository.ExerciseRepository;

@Service
public class ExercisesService {

  private final ExerciseRepository exerciseRepository;

  @Autowired
  public ExercisesService(ExerciseRepository exerciseRepository) {
    this.exerciseRepository = exerciseRepository;
  }

  public List<ExerciseDTO> getAllExercises() {
    List<Exercise> exercises = exerciseRepository.findAll();

    return exercises.stream()
        .map(this::exerciseMapper)
        .toList();
  }

  public List<ExerciseDTO> getByMuscleGroup(String muscleGroup) {

    String parsedMuscleGroup = parseMuscleGroup(muscleGroup);
    List<Exercise> exercises = exerciseRepository.findByMuscleGroup(parsedMuscleGroup);

    if (exercises.isEmpty()) {
      throw new Errors.ExerciseNotFound();
    }

    return exercises.stream()
        .map(this::exerciseMapper)
        .toList();
  }

  public ExerciseDTO getExerciseById(UUID id) {
    Exercise exercise = exerciseRepository.findById(id)
        .orElseThrow(Errors.ExerciseNotFound::new);

    return exerciseMapper(exercise);
  }

  public ExerciseDTO exerciseMapper(Exercise exercise) {
    return new ExerciseDTO(
        exercise.getId().toString(),
        exercise.getMuscleGroup().name(),
        exercise.getNameEn(),
        exercise.getNamePt(),
        exercise.getDescriptionEn(),
        exercise.getDescriptionPt());
  }

  private String parseMuscleGroup(String muscleGroup) {
    try {
      return MuscleGroup.valueOf(muscleGroup.toUpperCase()).name();
    } catch (Exception e) {
      throw new MuscleGroupNotFound(muscleGroup);
    }
  }
}
