package com.example.blism.repository;

import java.util.Optional;

import com.example.blism.domain.Letter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    Optional<List<Letter>> findAllBySenderId(Long userId);

    Optional<List<Letter>> findAllByReceiverId(Long userId);

    @Query("SELECT COUNT(l) FROM Letter l WHERE l.mailbox.id = :mailboxId")
    Integer countByMailboxId(@Param("mailboxId") Long mailboxId);
}