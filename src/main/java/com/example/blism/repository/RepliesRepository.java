package com.example.blism.repository;

import com.example.blism.domain.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepliesRepository extends JpaRepository<Reply,Long> {


    List<Reply> findAllBySenderIdContaning(Long senderId, Pageable pageable);
}
