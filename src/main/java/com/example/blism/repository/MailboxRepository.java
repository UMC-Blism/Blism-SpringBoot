package com.example.blism.repository;

import java.util.List;

import com.example.blism.domain.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
	Mailbox findByOwner_Id(Long ownerId); // user -> owner로 변경

	@Query("SELECT m FROM Mailbox m WHERE m.owner.id = :memberId AND YEAR(m.createdAt)= :year")
	Mailbox findByMemberId(@Param("memberId") Long memberId, @Param("year") String year);

	@Query("SELECT m FROM Mailbox m WHERE m.owner.id = :memberId")
	List<Mailbox> findByMemberId(@Param("memberId") Long memberId);

	@Query("SELECT COUNT(m) FROM Mailbox m WHERE m.id = :memberId")
	Integer countByMemberId(@Param("memberId") Long memberId);
}
