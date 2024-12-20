package com.example.blism.repository;

import com.example.blism.domain.Member;
import com.example.blism.dto.request.MemberRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMembersByNickname(String nickName);
    Member getMembersByNickname(String nickName);
    Member findByNickname(String nickName);

    Member findByNicknameAndCheckCode(String nickname, Integer checkCode);
}
