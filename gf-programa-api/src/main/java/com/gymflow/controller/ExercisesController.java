package com.gymflow.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymflow.dto.RegisterRequestDTO;
import com.gymflow.service.ExercisesService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/exercises")
public class ExercisesController {

  private final ExercisesService exercisesService;

  @Autowired
  public ExercisesController(ExercisesService exercisesService) {
    this.exercisesService = exercisesService;
  }

  @GetMapping("/")
  @Operation(summary = "{exercises.register.summary}", description = "{exercises.register.description}")
  public ResponseEntity<String> getAllExercises() {

    return ResponseEntity.ok("getAllExercises");
  }

  @GetMapping("/{id}")
  @Operation(summary = "{exercises.register.summary}", description = "{exercises.register.description}")
  public ResponseEntity<String> getExerciseById(@PathVariable UUID id) {

    return ResponseEntity.ok("getExerciseById " + id);
  }

  @PostMapping("/")
  @Operation(summary = "{exercises.register.summary}", description = "{exercises.register.description}")
  public ResponseEntity<String> addExercise(@RequestBody RegisterRequestDTO request) {

    return ResponseEntity.ok("addExercise");
  }

  @PostMapping("/{id}")
  @Operation(summary = "{exercises.register.summary}", description = "{exercises.register.description}")
  public ResponseEntity<String> addMediaToAnExercise(@PathVariable UUID id) {

    return ResponseEntity.ok("addMediaToAnExercise " + id);
  }
}
