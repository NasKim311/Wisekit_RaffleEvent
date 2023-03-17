package com.wisekit.assignment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RaffleRepository {
	
	private final EntityManager em;
	JPQLQueryFactory queryFactory;

//------------<byLot() / 해당 사용자의 표번호를 저장하는 메소드>------------------------------------------------------------------------------------	
	public void addByLotData(Long memberNum , int byLotNum) {
		queryFactory = new JPAQueryFactory(em);
		
		
		
	}
	
} // RaffleRepository class
