package com.example.blism.repository;

import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.net.ssl.SSLSession;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
    Mailbox findByOwner_Id(Long ownerId); // user -> owner로 변경
}

