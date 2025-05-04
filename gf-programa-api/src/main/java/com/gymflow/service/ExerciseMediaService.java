package com.gymflow.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gymflow.dto.CreateMediaDto;
import com.gymflow.dto.MediaDto;
import com.gymflow.enums.Gender;
import com.gymflow.exception.Errors;
import com.gymflow.model.Exercise;
import com.gymflow.model.ExerciseMedia;
import com.gymflow.repository.ExerciseMediaRepository;
import com.gymflow.repository.ExerciseRepository;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;

@Service
public class ExerciseMediaService {

  private final MinioClient minioClient;
  private final ExerciseRepository exerciseRepository;
  private final ExerciseMediaRepository exercisesMediaRepository;

  @Value("${minio.bucket}")
  private String bucket;

  public ExerciseMediaService(MinioClient minioClient, ExerciseRepository exerciseRepository,
      ExerciseMediaRepository mediaRepo) {
    this.minioClient = minioClient;
    this.exerciseRepository = exerciseRepository;
    this.exercisesMediaRepository = mediaRepo;
  }

  public String uploadMedia(UUID exerciseId, CreateMediaDto dto) {
    Gender gender = Gender.fromString(dto.getGender());
    Exercise exercise = exerciseRepository.findById(exerciseId)
        .orElseThrow(Errors.ExerciseNotFound::new);

    try {
      String objectName = exerciseId + "/" + UUID.randomUUID() + "_" + dto.getFile().getOriginalFilename();
      minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(bucket)
              .object(objectName)
              .stream(dto.getFile().getInputStream(), dto.getFile().getSize(), -1)
              .contentType(dto.getFile().getContentType())
              .build());

      String savedUrl = minioClient.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(bucket)
              .object(objectName)
              .build());

      ExerciseMedia media = new ExerciseMedia();
      media.setExercise(exercise);
      media.setGender(gender.name());
      media.setOrderIndex(dto.getOrderIndex());
      if (Boolean.TRUE.equals(dto.getIsVideo())) {
        media.setIsVideo(true);
        media.setFileUrl(savedUrl);
      } else {
        media.setIsVideo(false);
        media.setFileUrl(savedUrl);
      }

      exercisesMediaRepository.save(media);

      return savedUrl;

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

    return medias.stream().map(media -> new MediaDto(
        media.getId(),
        media.getFileUrl(),
        media.getIsVideo(),
        media.getOrderIndex(),
        media.getExercise().getNamePt(),
        media.getExercise().getNameEn(),
        media.getExercise().getMuscleGroup()))
        .toList();
  }
}
