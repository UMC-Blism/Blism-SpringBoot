package com.example.blism.repository;

import com.example.blism.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Member, Long> {
}