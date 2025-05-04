package com.gymflow.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymflow.dto.CreateNewExerciseDto;
import com.gymflow.dto.ExerciseDto;
import com.gymflow.repository.ExerciseRepository;
import com.gymflow.service.ExercisesService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/exercises")
public class ExercisesController {

  private final ExercisesService exercisesService;

  @Autowired
  public ExercisesController(ExercisesService exercisesService, ExerciseRepository exerciseRepository) {
    this.exercisesService = exercisesService;
  }

  // Endpoints for exercises

  @GetMapping("/")
  @Operation(summary = "{exercises.all.summary}", description = "{exercises.all.description}")
  public ResponseEntity<List<ExerciseDto>> returnAllExercises() {
    return ResponseEntity.ok(exercisesService.getAllExercises());
  }

  @GetMapping("/{id}")
  @Operation(summary = "{exercises.byId.summary}", description = "{exercises.byId.description}")
  public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable UUID id) {
    return ResponseEntity.ok(exercisesService.getExerciseById(id));
  }

  @GetMapping("musclegroup/{muscleGroup}")
  @Operation(summary = "{exercises.muscleGroup.summary}", description = "{exercises.muscleGroup.description}")
  public ResponseEntity<List<ExerciseDto>> getExercisesByMuscleGroup(@PathVariable String muscleGroup) {
    return ResponseEntity.ok(exercisesService.getByMuscleGroup(muscleGroup.toUpperCase()));

  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/add")
  @Operation(summary = "{exercises.addExercise.summary}", description = "{exercises.addExercise.description}")
  public ResponseEntity<ExerciseDto> addExercise(@RequestBody @Valid CreateNewExerciseDto exercise) {
    return ResponseEntity.ok(exercisesService.createExercise(exercise));
  }

}
