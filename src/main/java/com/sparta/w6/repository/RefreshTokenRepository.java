package com.sparta.w6.repository;



import com.sparta.mini_6team.domain.Member;
import com.sparta.mini_6team.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}
