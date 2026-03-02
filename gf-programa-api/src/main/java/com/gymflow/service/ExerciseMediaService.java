package com.gymflow.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gymflow.dto.CreateMediaDto;
import com.gymflow.dto.MediaDto;
import com.gymflow.enums.Gender;
import com.gymflow.exception.Errors;
import com.gymflow.model.Exercise;
import com.gymflow.model.ExerciseMedia;
import com.gymflow.repository.ExerciseMediaRepository;
import com.gymflow.repository.ExerciseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseMediaService {

  private final StorageService storage;
  private final ExerciseRepository exerciseRepository;
  private final ExerciseMediaRepository exercisesMediaRepository;

  public ExerciseMediaService(
      StorageService storage,
      ExerciseRepository exerciseRepository,
      ExerciseMediaRepository mediaRepo) {
    this.storage = storage;
    this.exerciseRepository = exerciseRepository;
    this.exercisesMediaRepository = mediaRepo;
  }

  @Transactional
  public String uploadMedia(UUID exerciseId, CreateMediaDto dto) {
    Gender gender = Gender.fromString(dto.getGender());

    Exercise exercise = exerciseRepository.findById(exerciseId)
        .orElseThrow(Errors.ExerciseNotFound::new);

    try {
      storage.ensureExercisePaths(exercise.getMuscleGroup(), exerciseId);

      String objectName = storage.uploadExerciseMedia(
          exercise.getMuscleGroup(),
          exerciseId,
          gender.name().toLowerCase(),
          dto.getFile().getInputStream(),
          dto.getFile().getSize(),
          dto.getFile().getContentType());

      ExerciseMedia media = new ExerciseMedia();
      media.setExercise(exercise);
      media.setGender(gender.toString());
      media.setOrderIndex(dto.getOrderIndex());
      media.setIsVideo(Boolean.TRUE.equals(dto.getIsVideo()));
      media.setFileUrl(objectName);

      exercisesMediaRepository.save(media);

      return storage.getPresignedGetUrl(objectName);

    } catch (IOException e) {
      throw new Errors.MediaUploadFailed("I/O error: " + e.getMessage());
    } catch (Exception e) {
      throw new Errors.MediaUploadFailed(e.getMessage());
    }
  }

  public List<MediaDto> listMediaOrdered(UUID exerciseId) {
    List<ExerciseMedia> medias = exercisesMediaRepository.findByExerciseIdOrderByOrderIndexAsc(exerciseId);

    if (medias.isEmpty()) {
      throw new Errors.MediaNotFound(exerciseId);
    }

    return medias.stream().map(media -> {
      String url = storage.getPresignedGetUrl(media.getFileUrl());

      return new MediaDto(
          media.getId(),
          url,
          media.getIsVideo(),
          media.getOrderIndex(),
          media.getExercise().getNamePt(),
          media.getExercise().getNameEn(),
          media.getExercise().getMuscleGroup());
    }).toList();
  }
}
