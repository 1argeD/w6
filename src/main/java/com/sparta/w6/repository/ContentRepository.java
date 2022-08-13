package com.sparta.w6.repository;


import com.sparta.w6.domain.Member;
import com.sparta.w6.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
  List<Content> findAllByOrderByModifiedAtDesc();
  List<Content> findByMember(Member member);
}
