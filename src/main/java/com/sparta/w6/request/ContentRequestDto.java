package com.sparta.w6.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequestDto {
  private String url;
  private String title;
  private String text;
}
