package com.wisekit.assignment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wisekit.assignment.domain.Winner;

import static com.wisekit.assignment.domain.QWinner.*;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RaffleRepository {

	private final EntityManager em;
	JPAQueryFactory queryFactory;

//------------<addByLotData() / 당첨 데이터 저장 메소드>------------------------------------------------------------------------------------	
	public void addByLotData(Winner winnerdata) {
		em.persist(winnerdata);

	}

} // RaffleRepository class
