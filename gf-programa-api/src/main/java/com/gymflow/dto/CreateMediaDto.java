package com.gymflow.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateMediaDto {
  private String gender;
  private Boolean isVideo;
  private Integer orderIndex;
  private MultipartFile file;
}
