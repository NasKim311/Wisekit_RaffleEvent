package com.wisekit.assignment.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wisekit.assignment.domain.Member;
import static com.wisekit.assignment.domain.QMember.*;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager em;
	JPQLQueryFactory queryFactory;

//------------<memberJoin() / 멤버정보 저장하는 메소드>------------------------------------------------------------------------------------		
	public void memberJoin(Member member) {
		em.persist(member);
	}

//------------<findAllMemberList() /해당 이름과 번호로 데이터 조회하는 메소드>------------------------------------------------------------------------------------		
	public Member findByNamePhoneNum(String memberName, String memberPhoneNum) {
		queryFactory = new JPAQueryFactory(em);

		Member memberData = queryFactory.selectFrom(member)
				.where(member.memberName.eq(memberName), member.memberPhoneNum.eq(memberPhoneNum)).fetchOne();

		return memberData;

	}

} // MemberRepository class
