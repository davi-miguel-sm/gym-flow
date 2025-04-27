package com.gymflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
  private String id;
  private String muscleGroup;
  private String nameEn;
  private String namePt;
  private String descriptionEn;
  private String descriptionPt;
}
