package com.example.blism.repository;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Long> {

}
