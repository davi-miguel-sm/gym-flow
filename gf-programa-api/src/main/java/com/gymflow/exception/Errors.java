package com.gymflow.exception;

import java.util.UUID;

public class Errors extends RuntimeException {
  private final String code;
  private static final long serialVersionUID = 1L;

  public Errors(String code, String message) {
    super(message);
    this.code = code;
  }

  public Errors(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  // --- Common App Errors ---

  public static class ErrorEmailAlreadyExists extends Errors {
    public ErrorEmailAlreadyExists() {
      super("001", "This email already exists!");
    }
  }

  public static class ErrorUserNotFound extends Errors {
    public ErrorUserNotFound() {
      super("002", "User not found.");
    }
  }

  public static class ErrorIncorrectPassword extends Errors {
    public ErrorIncorrectPassword() {
      super("003", "Incorrect password.");
    }
  }

  // --- Exercise-Related Errors ---

  public static class ExerciseNotFound extends Errors {
    public ExerciseNotFound() {
      super("004", "Exercise not found.");
    }
  }

  public static class MuscleGroupNotFound extends Errors {
    public MuscleGroupNotFound(String muscleGroup) {
      super("005", "Muscle Group " + muscleGroup + " not found.");
    }
  }

  public static class InvalidUUIDFormat extends Errors {
    public InvalidUUIDFormat() {
      super("006", "Invalid UUID format!");
    }
  }

  public static class NamePtAlreadyIncludes extends Errors {
    public NamePtAlreadyIncludes() {
      super("007", "A record with the same 'name_pt' already exists.");
    }
  }

  public static class NameEnAlreadyIncludes extends Errors {
    public NameEnAlreadyIncludes() {
      super("008", "A record with the same 'name_en' already exists.");
    }
  }

  public static class MediaUploadFailed extends Errors {
    public MediaUploadFailed(String reason) {
      super("009", "Failed to upload media: " + reason);
    }
  }

  public static class MediaNotFound extends Errors {
    public MediaNotFound(UUID exerciseId) {
      super("010", "No media found for exercise ID: " + exerciseId);
    }
  }

  public static class InvalidGender extends Errors {
    public InvalidGender(String gender) {
      super("011", "Invalid gender value: " + gender);
    }
  }

  public static class UnexpectedError extends Errors {
    public UnexpectedError(String message) {
      super("999", "Unexpected error occurred: " + message);
    }
  }
}
