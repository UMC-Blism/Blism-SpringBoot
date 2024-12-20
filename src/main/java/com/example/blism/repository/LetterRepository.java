package com.example.blism.repository;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
