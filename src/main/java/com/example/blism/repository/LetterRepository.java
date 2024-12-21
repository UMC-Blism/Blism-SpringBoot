package com.example.blism.repository;

import com.example.blism.domain.Letter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    Optional<List<Letter>> findAllBySenderId(Long userId);

    Optional<List<Letter>> findAllByReceiverId(Long userId);
}
