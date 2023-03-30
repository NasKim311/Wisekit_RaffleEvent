package com.wisekit.assignment.service;

import javax.transaction.Transactional;

import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.repository.RaffleRepository;
import com.wisekit.assignment.repository.RaffleRepositoryInterface;
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
    private final RaffleRepositoryInterface raffleRepositoryInterface;

    //------------<createMember() / 회원 생성 메소드 (회원이 있을시를 가정하기 위함)>------------------------------------------------------------------------------------
    public void createMember() {

        List<Member> memberDatas = new ArrayList<>();
        Member member = new Member();

        for (int i = 0; i < 10000; i++) {
            member.setMemberNum(i);
            member.setMemberName("회원" + i);
            member.setMemberPhoneNum(i + "");
            memberDatas.add(i, member);
            System.out.println(memberDatas.get(i).getMemberNum());
            System.out.println(memberDatas.get(i).getMemberName());
            System.out.println(memberDatas.get(i).getMemberPhoneNum());
        }
        raffleRepositoryInterface.saveAll(memberDatas);

    }

    //------------<memberJoin() / 멤버 정보 입력>------------------------------------------------------------------------------------
    @Transactional
    public void memberJoin(Member member) {

        memberRepository.memberJoin(member);

    }

    //------------<() / 중복 사용자 정보 확인하는 메소드>------------------------------------------------------------------------------------
    @Transactional
    public Winner memberDoubleCheck(String memberPhoneNum) {

        Winner memberData = raffleRepository.findByMemberPhoneNum(memberPhoneNum);

        return memberData;
    }

} // MemberService class
