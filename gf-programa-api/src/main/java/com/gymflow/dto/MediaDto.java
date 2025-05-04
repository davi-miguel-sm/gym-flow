package com.gymflow.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
  UUID idMedia;
  String fileUrl;
  boolean isVideo;
  int orderIndex;
  String exerciseNamePt;
  String exerciseNameEn;
  String muscleGroup;
}
