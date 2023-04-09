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

    //------------<findByMemberNamePhoneNum() / 중복추첨 방지를 위한 메소드(멤버정보를 이용해 당첨테이블에서 데이터 조회)>------------------------------------------------------------------------------------
    public Winner findByMemberNamePhoneNum(String memberName, String memberPhoneNum) {
        queryFactory = new JPAQueryFactory(em);

        Winner memberDataInWinner = queryFactory.selectFrom(winner).where(winner.member.memberName.eq(memberName), winner.member.memberPhoneNum.eq(memberPhoneNum)).fetchOne();

        return memberDataInWinner;
    }

} // RaffleRepository class
