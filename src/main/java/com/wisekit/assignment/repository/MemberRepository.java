package com.wisekit.assignment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.wisekit.assignment.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager em;
	JPQLQueryFactory queryFactory;

//------------<memberJoin() / 멤버정보 저장>------------------------------------------------------------------------------------		
	public void memberJoin(Member member) {
		em.persist(member);
	}

} // MemberRepository class
