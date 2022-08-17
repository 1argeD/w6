package com.sparta.w6.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseDto {
  private Long id;
  private String url;
  private String title;
  private String text;
  private String author;
  private List<CommentResponseDto> comments;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
