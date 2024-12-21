package com.example.blism.repository;

import com.example.blism.domain.Letter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    Optional<Letter> findAllBySenderId(Long userId);

    Optional<Letter> findAllByReceiverId(Long userId);
}
