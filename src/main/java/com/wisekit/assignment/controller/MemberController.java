package com.wisekit.assignment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.dto.MemberDTO;
import com.wisekit.assignment.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //------------<addBasicMember() / 기존회원 버튼 클릭시 이동>------------------------------------------------------------------------------------
    @GetMapping("/member/basic")
    public String addBasicMember(@RequestParam(defaultValue = "/") String redirectURL) {
        memberService.createbasicMember();
        return "redirect:" + redirectURL;
    }

    //------------<memberLoginPage() / 로그인페이지로 이동>------------------------------------------------------------------------------------
    @GetMapping("/member/loginPage")
    public String memberLoginPage(@ModelAttribute MemberDTO memberDTO) {
        return "member/memberLogin";
    }

    //------------<memberJoinPage() / 회원가입페이지로 이동>------------------------------------------------------------------------------------
    @GetMapping("/member/joinPage")
    public String memberJoinPage(@ModelAttribute MemberDTO memberDTO) {
        return "member/memberJoin";
    }

    //------------<memberLogin() / 멤버 로그인(회원확인은 ajax에서 확인)>------------------------------------------------------------------------------------
    @PostMapping("/member/login")
    public String memberLogin(@ModelAttribute MemberDTO memberDTO, HttpServletRequest request) {
        Member member = Member.builder().memberName(memberDTO.getMemberName()).memberPhoneNum(memberDTO.getMemberPhoneNum()).build();

        // 세션사용
        HttpSession session = request.getSession();
        session.setAttribute("member", member);

        return "raffle/raffleReady";

    }

    //------------<memberJoin() / 멤버 회원가입>------------------------------------------------------------------------------------
    @PostMapping("/member/join")
    public String memberJoin(@ModelAttribute MemberDTO memberDTO) {
        Member member = new Member(memberDTO.getMemberName(), memberDTO.getMemberPhoneNum());

        memberService.memberJoin(member);

        return "raffle/raffleReady";
    }

    //------------<joinDoubleCheck() / 회원가입 중복 확인>------------------------------------------------------------------------------------
    @ResponseBody
    @RequestMapping(value = "/joinDoubleCheck", method = RequestMethod.POST)
    public String joinDoubleCheck(@ModelAttribute MemberDTO memberDTO) {
        if (memberService.memberDoubleCheck(memberDTO.getMemberName(), memberDTO.getMemberPhoneNum()) != null) {
            return "존재하는 회원입니다.";
        }
        return "성공";
    }

    //------------<loginDoubleCheck() / 로그인 중복 확인>------------------------------------------------------------------------------------
    @ResponseBody
    @RequestMapping(value = "/loginDoubleCheck", method = RequestMethod.POST)
    public String loginDoubleCheck(@ModelAttribute MemberDTO memberDTO) {
        if (memberService.memberDoubleCheck(memberDTO.getMemberName(), memberDTO.getMemberPhoneNum()) == null) {
            return "회원 정보가 없습니다.";
        }
        return "성공";
    }

} // MemberController class
