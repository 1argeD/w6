package com.sparta.w6.repository;



import com.sparta.mini_6team.domain.Comment;
import com.sparta.mini_6team.domain.Member;
import com.sparta.mini_6team.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findByMember(Member member);
}
