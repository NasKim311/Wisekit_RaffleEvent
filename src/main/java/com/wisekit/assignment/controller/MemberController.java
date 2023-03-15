package com.wisekit.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.dto.MemberDTO;
import com.wisekit.assignment.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
//------------<memberJoin() / 멤버 정보 입력>------------------------------------------------------------------------------------		
	@PostMapping("/member/join")
	public String memberJoin(@ModelAttribute MemberDTO memberDTO) {
		
		Member member = new Member();
		
		member.setMemberName(memberDTO.getMemberName());
		member.setMemberPhoneNum(memberDTO.getMemberPhoneNum());
		
		System.out.println(member.getMemberNum());
		System.out.println(member.getMemberName());
		System.out.println(member.getMemberPhoneNum());
		memberService.memberJoin(member);
		
		
		return "raffle/raffleReady";
	}
	
	
} // MemberController class
