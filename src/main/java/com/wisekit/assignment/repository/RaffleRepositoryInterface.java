package com.wisekit.assignment.repository;

import com.wisekit.assignment.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public interface RaffleRepositoryInterface extends JpaRepository<Member, Long> {
}
