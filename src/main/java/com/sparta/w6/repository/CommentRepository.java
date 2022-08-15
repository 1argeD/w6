package com.sparta.w6.repository;


import com.sparta.w6.domain.Comment;
import com.sparta.w6.domain.Member;
import com.sparta.w6.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByContent(Content content);
//  List<Comment> findAllByComment(Comment comment);
  List<Comment> findByMember(Member member);
}
