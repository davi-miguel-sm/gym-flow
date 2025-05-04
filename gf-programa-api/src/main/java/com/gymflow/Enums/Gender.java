package com.gymflow.enums;

import com.gymflow.exception.Errors;

public enum Gender {
  MALE, FEMALE;

  public static Gender fromString(String value) {
    try {
      return Gender.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new Errors.InvalidGender(value);
    }
  }

}
