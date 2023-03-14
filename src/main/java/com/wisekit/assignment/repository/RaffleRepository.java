package com.wisekit.assignment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RaffleRepository {
	
	private final EntityManager em;
	JPQLQueryFactory queryFactory;

} // RaffleRepository class
