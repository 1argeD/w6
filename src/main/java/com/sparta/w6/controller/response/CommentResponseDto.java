package com.sparta.w6.controller.response;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
  private String  content;
  private Long id;
  private String author;
  private String commentText;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
