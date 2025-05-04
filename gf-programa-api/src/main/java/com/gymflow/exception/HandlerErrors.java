package com.gymflow.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.gymflow.dto.ErrorResponseDto;
import com.gymflow.exception.Errors.ExerciseNotFound;
import com.gymflow.exception.Errors.InvalidUUIDFormat;
import com.gymflow.exception.Errors.MediaNotFound;
import com.gymflow.exception.Errors.MediaUploadFailed;
import com.gymflow.exception.Errors.MuscleGroupNotFound;
import com.gymflow.exception.Errors.NameEnAlreadyIncludes;
import com.gymflow.exception.Errors.NamePtAlreadyIncludes;
import com.gymflow.exception.Errors.UnexpectedError;

@RestControllerAdvice
public class HandlerErrors {
  private ResponseEntity<ErrorResponseDto> buildError(String code, String message, HttpStatus status) {
    return ResponseEntity.status(status).body(new ErrorResponseDto(code, message));
  }

  private ResponseEntity<ErrorResponseDto> buildError(Errors error, HttpStatus status) {
    return buildError(error.getCode(), error.getMessage(), status);
  }

  @ExceptionHandler({
      MuscleGroupNotFound.class,
      ExerciseNotFound.class,
      NamePtAlreadyIncludes.class,
      NameEnAlreadyIncludes.class,
      InvalidUUIDFormat.class,
      MediaUploadFailed.class,
      MediaNotFound.class
  })
  public ResponseEntity<ErrorResponseDto> handleCustomErrors(Errors error) {
    return buildError(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    Throwable rootCause = ex.getRootCause();
    String message = (rootCause != null) ? rootCause.getMessage() : "";

    if (message.contains("unique_name_pt")) {
      return buildError(new NamePtAlreadyIncludes(), HttpStatus.CONFLICT);
    }
    if (message.contains("unique_name_en")) {
      return buildError(new NameEnAlreadyIncludes(), HttpStatus.CONFLICT);
    }
    return buildError(new UnexpectedError(message), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    return buildError(new InvalidUUIDFormat(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleUnexpected(Exception ex) {
    return buildError(new UnexpectedError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
