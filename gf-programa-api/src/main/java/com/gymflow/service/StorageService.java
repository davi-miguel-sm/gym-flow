package com.gymflow.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gymflow.exception.Errors;
import com.gymflow.exception.Errors.UnexpectedError;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;

@Service
public class StorageService {
  private final MinioClient minio;
  private final String bucket;

  public StorageService(MinioClient minio, @Value("${minio.bucket}") String bucket) {
    this.minio = minio;
    this.bucket = bucket;
  }

  public void ensureBucketExists() {
    try {
      boolean exists = minio.bucketExists(
          BucketExistsArgs.builder().bucket(bucket).build());
      if (!exists) {
        minio.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
      }
    } catch (Exception e) {
      throw new UnexpectedError("Failed to ensure bucket exists: " + bucket, e);
    }
  }

  private String normalizePrefix(String prefix) {
    if (prefix == null || prefix.isBlank())
      return "";
    return prefix.endsWith("/") ? prefix : prefix + "/";
  }

  public void ensurePrefix(String prefix) {
    String normalized = normalizePrefix(prefix);

    try {
      minio.statObject(
          StatObjectArgs.builder()
              .bucket(bucket)
              .object(normalized)
              .build());
      return;

    } catch (ErrorResponseException e) {
      String code = e.errorResponse() != null ? e.errorResponse().code() : null;
      if (!"NoSuchKey".equals(code) && !"NoSuchObject".equals(code)) {
        throw new UnexpectedError("Failed to stat prefix: " + normalized, e);
      }
    } catch (Exception e) {
      throw new UnexpectedError("Failed to stat prefix: " + normalized, e);
    }

    try {
      byte[] empty = new byte[0];
      minio.putObject(
          PutObjectArgs.builder()
              .bucket(bucket)
              .object(normalized)
              .stream(new ByteArrayInputStream(empty), 0, -1)
              .contentType("application/x-directory")
              .build());
    } catch (Exception e) {
      throw new UnexpectedError("Failed to create prefix: " + normalized, e);
    }
  }

  public void ensureExercisePaths(String muscleGroup, UUID exerciseId) {
    ensureBucketExists();
    String base = muscleGroup + "/" + exerciseId;

    ensurePrefix(base + "/male/");
    ensurePrefix(base + "/female/");
  }

  public String uploadExerciseMedia(String muscleGroup, UUID exerciseId, String genderLower,
      InputStream inputStream, long size, String contentType) {

    ensureBucketExists();

    String objectName = muscleGroup + "/" + exerciseId + "/" + genderLower + "/" + UUID.randomUUID();

    try {
      minio.putObject(
          PutObjectArgs.builder()
              .bucket(bucket)
              .object(objectName)
              .stream(inputStream, size, -1)
              .contentType(contentType != null ? contentType : "application/octet-stream")
              .build());
      return objectName;
    } catch (Exception e) {
      throw new Errors.UnexpectedError("Failed to upload object to MinIO: " + e.getMessage(), e);
    }
  }

  public String getPresignedGetUrl(String objectName) {
    try {
      return minio.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(bucket)
              .object(objectName)
              .build());
    } catch (Exception e) {
      throw new UnexpectedError("Failed to generate presigned URL: " + e.getMessage(), e);
    }
  }
}