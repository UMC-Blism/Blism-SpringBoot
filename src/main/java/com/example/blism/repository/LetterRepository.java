package com.example.blism.repository;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LetterRepository extends JpaRepository<Letter, Long> {




	@Query("SELECT COUNT(l) FROM Letter l WHERE l.mailbox.id = :mailboxId")
	Integer countByMailboxId(@Param("mailboxId") Long mailboxId);
}
