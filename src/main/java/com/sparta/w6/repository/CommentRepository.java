package com.sparta.w6.repository;


import com.sparta.w6.domain.Comment;
import com.sparta.w6.domain.Member;
import com.sparta.w6.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findByMember(Member member);
}
