package com.example.blism.repository;

import com.example.blism.domain.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepliesRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllBySenderId(Long senderId, Pageable pageable);
    List<Reply> findAllByReceiverId(Long receiverId, Pageable pageable);
}

