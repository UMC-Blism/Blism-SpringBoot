package com.example.blism.repository;

import com.example.blism.domain.Member;
import com.example.blism.dto.request.MemberRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMembersByNickname(String nickName);
    Member getMembersByNickname(String nickName);
    Member findByNickname(String nickName);

    Member findByNicknameAndCheckCode(String nickname, String checkCode);


    @Query("SELECT m FROM Member m WHERE m.nickname LIKE %:nickname%")
    List<Member> findByNicknameContaining(@Param("nickname") String nickname, Pageable pageable);


}
