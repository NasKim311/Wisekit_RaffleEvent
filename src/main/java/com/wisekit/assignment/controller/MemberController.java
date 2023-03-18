package com.wisekit.assignment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.dto.MemberDTO;
import com.wisekit.assignment.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

//------------<indexPage() / 메인 페이지로 이동>------------------------------------------------------------------------------------		
	@GetMapping("/")
	public String indexPage(@ModelAttribute MemberDTO memberDTO) {
		return "index";
	}

//------------<memberJoin() / 멤버 정보 입력>------------------------------------------------------------------------------------		
	@PostMapping("/member/join")
	public String memberJoin(@ModelAttribute MemberDTO memberDTO, Model model, HttpServletRequest request) {

		Member member = new Member();
		member.setMemberName(memberDTO.getMemberName());
		member.setMemberPhoneNum(memberDTO.getMemberPhoneNum());

		// 최초 추첨일 경우 (데이터 저장 및 추첨 진행 o)
		memberService.memberJoin(member);
		HttpSession session = request.getSession(); // 세션사용
		session.setAttribute("member", member);
		return "raffle/raffleReady";
	}

//------------<memberDoubleCheck() / 멤버 중복 확인>------------------------------------------------------------------------------------		
	@ResponseBody
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public String memberDoubleCheck(@ModelAttribute MemberDTO memberDTO) {
		System.out.println(memberDTO.getMemberName());
		System.out.println(memberDTO.getMemberPhoneNum());
		if (memberService.memberDoubleCheck(memberDTO.getMemberPhoneNum()) != null) {
			return "중복 추첨은 불가합니다.";
		}
		return "성공";
	}

} // MemberController class
