package com.wisekit.assignment.repository;

import com.wisekit.assignment.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryInterface extends JpaRepository<Member, Long> {
}
