package com.example.blism.repository;

import com.example.blism.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMembersByNickname(String nickName);
    Member getMembersByNickname(String nickName);
}
