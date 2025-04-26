package com.gymflow.exception;

public class Errors extends RuntimeException {
  private final String code;

  /**
   * Create an error with a code and message.
   *
   * @param code    unique idenfier of error (ex: "001")
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
}
