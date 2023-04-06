package com.wisekit.assignment.repository;

import javax.persistence.EntityManager;

import com.wisekit.assignment.domain.Member;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wisekit.assignment.domain.Winner;

import static com.wisekit.assignment.domain.QWinner.*;

import java.util.List;

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

    //------------<findByMemberNamePhoneNum() / 중복추첨 방지를 위한 메소드(멤버정보를 이용해 당첨테이블에서 데이터 조회)>------------------------------------------------------------------------------------
    public Winner findByMemberNamePhoneNum(String memberName, String memberPhoneNum) {
        queryFactory = new JPAQueryFactory(em);

        Winner memberDataInWinner = queryFactory.selectFrom(winner).where(winner.member.memberName.eq(memberName),winner.member.memberPhoneNum.eq(memberPhoneNum)).fetchOne();

        return memberDataInWinner;
    }

    //------------<findApplicantCountCount() / 총 응모자 인원을 리턴하는 메소드>------------------------------------------------------------------------------------
    public Integer findApplicantCountCount(){
        queryFactory = new JPAQueryFactory(em);

        Integer applicantCount =queryFactory.select(winner.count()).from(winner).fetchOne().intValue();

        return applicantCount;
    }

} // RaffleRepository class
