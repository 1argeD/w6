package com.sparta.w6.repository;


import com.sparta.w6.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // 엔티티 테이블명, PK의 타입 ...

  Optional<Member> findById(Long id);
  Optional<Member> findByLoginId(String loginId);

  Member findByKakaoId(Long kakaoId);
}
