package com.sparta.w6.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

   @NotBlank
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long KakaoId;

   @NotBlank
    private String username;

   public KakaoUserInfoDto(Long id, String username){
       this.Id = getId();
       this.username = getUsername();
   }
}
