package com.wisekit.assignment.service;

import javax.transaction.Transactional;

import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.repository.RaffleRepository;
import com.wisekit.assignment.repository.MemberRepositoryInterface;
import org.springframework.stereotype.Service;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RaffleRepository raffleRepository;
    private final MemberRepositoryInterface memberRepositoryInterface;

    //------------<createMember() / 회원 생성 메소드 (회원이 있을시를 가정하기 위함)>------------------------------------------------------------------------------------
    public void createMember() {
        System.out.println("createMember 사용");
        int basicMemberCount = 10;
        List<Member> memberDatas = new ArrayList<Member>() {
            {
                for (int i = 1; i < basicMemberCount; i++) {
                    add(Member.builder().memberName("회원" + i).memberPhoneNum(i + "").build());
                }
            }
        };
        memberRepositoryInterface.saveAll(memberDatas);

    }

    //------------<memberLogin() / 로그인>------------------------------------------------------------------------------------
    public Boolean memberLogin(Member member) {
        boolean isLogin = false;

        if (memberRepository.findByNamePhoneNum(member.getMemberName(), member.getMemberPhoneNum()) != null) {
            isLogin = true;
        }
        return isLogin;
    }

    //------------<memberJoin() / 회원 가입>------------------------------------------------------------------------------------
    @Transactional
    public void memberJoin(Member member) {
        memberRepository.memberJoin(member);

    }

    //------------<memberDoubleCheck() /  회원 중복 확인 메소드>------------------------------------------------------------------------------------
    @Transactional
    public Member memberDoubleCheck(String memberName, String memberPhoneNum) {

        Member memberData = memberRepository.findByNamePhoneNum(memberName, memberPhoneNum);

        return memberData;
    }

    //------------<byLotDoubleCheck() /  추첨 이벤트 중복 사용 확인 메소드 (RaffleRepository)>------------------------------------------------------------------------------------
    @Transactional
    public Winner byLotDoubleCheck(String memberName, String memberPhoneNum) {

        Winner memberDataInWinner = raffleRepository.findByMemberNamePhoneNum(memberName ,memberPhoneNum);

        return memberDataInWinner;
    }

} // MemberService class
