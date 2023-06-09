package com.wisekit.assignment.repository;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.domain.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaffleRepositoryInterface extends JpaRepository<Winner, Long> {
}
