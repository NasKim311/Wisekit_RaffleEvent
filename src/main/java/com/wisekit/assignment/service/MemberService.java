package com.wisekit.assignment.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

//------------<memberJoin() / 멤버 정보 입력>------------------------------------------------------------------------------------		
	@Transactional
	public void memberJoin(Member member) {
		
		memberRepository.memberJoin(member);
		
	}
	
} // MemberService class
