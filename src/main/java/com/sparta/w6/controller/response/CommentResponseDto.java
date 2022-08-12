package com.sparta.w6.controller.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String author;
  private String content;
//  private Long heart;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
