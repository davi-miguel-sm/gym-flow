package com.gymflow.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymflow.dto.CreateMediaDto;
import com.gymflow.service.ExerciseMediaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/exercise-media")
public class ExerciseMediaController {

  private final ExerciseMediaService exerciseMediaService;

  @Autowired
  public ExerciseMediaController(ExerciseMediaService exerciseMediaService) {
    this.exerciseMediaService = exerciseMediaService;
  }

  @GetMapping("/{id}")
  @Operation(summary = "{exercises.getMediaOfAnExercise.summary}", description = "{exercises.getMediaOfAnExercise.description}")
  public ResponseEntity<List<String>> getMediasOfAnExercise(@PathVariable UUID id) {

    List<String> mediaUrls = exerciseMediaService.listMediaOrdered(id);
    return ResponseEntity.ok(mediaUrls);

  }

  @PostMapping("/{id}")
  @Operation(summary = "{exercises.addMediaToExercise.summary}", description = "{exercises.addMediaToExercise.description}")
  public ResponseEntity<String> addMediaToAnExercise(@PathVariable UUID id, @ModelAttribute CreateMediaDto dto) {

    String url = exerciseMediaService.uploadMedia(id, dto);
    return ResponseEntity.ok(url);

  }
}
