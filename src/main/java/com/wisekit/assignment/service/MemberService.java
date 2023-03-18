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

//------------<() / 중복 사용자 정보 확인하는 메소드>------------------------------------------------------------------------------------		
	@Transactional
	public Member memberDoubleCheck(String memberPhoneNum) {

		Member memberData = memberRepository.findByNamePhoneNum(memberPhoneNum);

		return memberData;
	}

} // MemberService class
