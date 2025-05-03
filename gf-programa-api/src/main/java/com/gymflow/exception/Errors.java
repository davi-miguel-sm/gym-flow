package com.gymflow.exception;

public class Errors extends RuntimeException {
  private final String code;
  private static final long serialVersionUID = 1L;

  /**
   * Create an error with a code and message.
   *
   * @param code    unique idenfier of error (ex: "000")
   * @param message description of error (ex: "User not found")
   */

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

}
