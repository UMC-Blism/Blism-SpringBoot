package com.example.blism.repository;

import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
}
