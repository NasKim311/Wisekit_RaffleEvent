package com.wisekit.assignment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wisekit.assignment.domain.Winner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wisekit.assignment.domain.QWinner.winner;

//@Repository
@RequiredArgsConstructor
public class RaffleRepository_back {

	private final EntityManager em;
	JPAQueryFactory queryFactory;

//------------<addByLotData() / 당첨 데이터 저장 메소드>------------------------------------------------------------------------------------	
	public void addByLotData(Winner winnerdata) {
		em.persist(winnerdata);

	}

//------------<findAllByLotData() / 모든 당첨 데이터를 표번호 오름차순으로 리턴하는 메소드>------------------------------------------------------------------------------------	
//	public List findAllByLotData() {
//		queryFactory = new JPAQueryFactory(em);
//
//		List<Winner> allByLotDatas = queryFactory.selectFrom(winner).orderBy(winner.winnerByLotNum.asc()).fetch();
//
//		return allByLotDatas;
//	}

//------------<findAllByLotData() / 1등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------	
	public int firstWinnerCount() {
		queryFactory = new JPAQueryFactory(em);

		List<Winner> firstWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(1)).fetch();

		return firstWinnerData.size();

	}

//------------<findAllByLotData() / 2등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------	
	public int secondWinnerCount() {
		queryFactory = new JPAQueryFactory(em);

		List<Winner> secondWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(2)).fetch();

		return secondWinnerData.size();

	}

//------------<findAllByLotData() / 3등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------	
	public int thirdWinnerCount() {
		queryFactory = new JPAQueryFactory(em);

		List<Winner> thirdWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(3)).fetch();

		return thirdWinnerData.size();

	}

} // RaffleRepository class
