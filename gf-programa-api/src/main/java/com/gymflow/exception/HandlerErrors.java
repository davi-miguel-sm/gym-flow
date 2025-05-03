package com.gymflow.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.gymflow.dto.ErrorResponseDTO;
import com.gymflow.exception.Errors.ExerciseNotFound;
import com.gymflow.exception.Errors.InvalidUUIDFormat;
import com.gymflow.exception.Errors.MuscleGroupNotFound;
import com.gymflow.exception.Errors.NameEnAlreadyIncludes;
import com.gymflow.exception.Errors.NamePtAlreadyIncludes;

@RestControllerAdvice
public class HandlerErrors {

  @ExceptionHandler(MuscleGroupNotFound.class)
  public ResponseEntity<ErrorResponseDTO> handleMuscleGroupNotFound(MuscleGroupNotFound ex) {
    return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExerciseNotFound.class)
  public ResponseEntity<ErrorResponseDTO> handleExerciseNotFound(ExerciseNotFound ex) {
    return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidUUID(MethodArgumentTypeMismatchException ex) {
    InvalidUUIDFormat error = new InvalidUUIDFormat();
    return buildErrorResponse(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
    ErrorResponseDTO errorResponse = new ErrorResponseDTO("999", "Unexpected error occurred: " + ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";

    if (message.contains("unique_name_pt")) {
      NamePtAlreadyIncludes error = new NamePtAlreadyIncludes();
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new ErrorResponseDTO(error.getCode(), error.getMessage()));
    }

    if (message.contains("unique_name_en")) {
      NameEnAlreadyIncludes error = new NameEnAlreadyIncludes();
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new ErrorResponseDTO(error.getCode(), error.getMessage()));
    }

    Errors generic = new Errors("999", "Constraint violation: " + message);
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponseDTO(generic.getCode(), generic.getMessage()));
  }

  private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Errors ex, HttpStatus status) {
    ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getCode(), ex.getMessage());
    return ResponseEntity.status(status).body(errorResponse);
  }
}
