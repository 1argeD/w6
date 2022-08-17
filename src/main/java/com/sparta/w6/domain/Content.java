package com.sparta.w6.domain;



import com.fasterxml.jackson.annotation.JsonManagedReference;


import com.sparta.w6.request.ContentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Content extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String text;

  @Column
  private String url;


  @JsonManagedReference /// 무한루프 매니저 점
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "content")
  private List<Comment> comments;



  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  public void update(ContentRequestDto contentRequestDto) {
    this.title = contentRequestDto.getTitle();
    this.text = contentRequestDto.getText();
  }

//  public void updateImage(String imageUrl){
//    this.imageUrl = imageUrl;
//  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

}
