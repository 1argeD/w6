package com.sparta.w6.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;
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


  @JsonIgnore
  @OneToMany(mappedBy = "content", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;



  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
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
