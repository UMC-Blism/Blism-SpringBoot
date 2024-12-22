package com.example.blism.repository;

import java.util.List;

import com.example.blism.domain.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;


import javax.net.ssl.SSLSession;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
  
	Mailbox findByOwner_Id(Long ownerId); // user -> owner로 변경

	@Query("SELECT m FROM Mailbox m WHERE m.owner.id = :memberId AND YEAR(m.createdAt)= :year")
	Mailbox findByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") String year);

	@Query("SELECT m FROM Mailbox m WHERE m.owner.id = :memberId AND YEAR(m.createdAt) < :year")
	List<Mailbox> findByMemberIdAndPastYear(@Param("memberId") Long memberId, @Param("year") String year);

	@Query("SELECT COUNT(m) FROM Mailbox m WHERE m.owner.id = :memberId AND YEAR(m.createdAt) < YEAR(CURRENT_DATE)")
	Integer countByMemberId(@Param("memberId") Long memberId);

}
