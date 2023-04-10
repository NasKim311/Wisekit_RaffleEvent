package com.wisekit.assignment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wisekit.assignment.domain.Winner;

import static com.wisekit.assignment.domain.QWinner.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RaffleRepository {

    private final EntityManager em;
    JPAQueryFactory queryFactory;

    //------------<addByLotData() / 당첨 데이터 저장 메소드>------------------------------------------------------------------------------------
    public void addByLotData(Winner winnerdata) {
        em.persist(winnerdata);

    }

    //------------<firstWinnerCount() / 1등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------
    public int firstWinnerCount() {
        queryFactory = new JPAQueryFactory(em);

        List<Winner> firstWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(1)).fetch();

        return firstWinnerData.size();

    }

    //------------<secondWinnerCount() / 2등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------
    public int secondWinnerCount() {
        queryFactory = new JPAQueryFactory(em);

        List<Winner> secondWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(2)).fetch();

        return secondWinnerData.size();

    }

    //------------<thirdWinnerCount() / 3등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------
    public int thirdWinnerCount() {
        queryFactory = new JPAQueryFactory(em);

        List<Winner> thirdWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(3)).fetch();

        return thirdWinnerData.size();

    }

    //------------<fourthWinnerCount() / 4등 데이터를 리턴하는 메소드>------------------------------------------------------------------------------------
    public int fourthWinnerCount() {
        queryFactory = new JPAQueryFactory(em);

        List<Winner> thirdWinnerData = queryFactory.selectFrom(winner).where(winner.winnerRank.eq(4)).fetch();

        return thirdWinnerData.size();

    }

    //------------<findByMemberNamePhoneNum() / 중복추첨 방지를 위한 메소드(멤버정보를 이용해 당첨테이블에서 데이터 조회)>------------------------------------------------------------------------------------
    public Winner findByMemberNamePhoneNum(String memberName, String memberPhoneNum) {
        queryFactory = new JPAQueryFactory(em);

        Winner memberDataInWinner = queryFactory.selectFrom(winner).where(winner.member.memberName.eq(memberName), winner.member.memberPhoneNum.eq(memberPhoneNum)).fetchOne();

        return memberDataInWinner;
    }

} // RaffleRepository class
