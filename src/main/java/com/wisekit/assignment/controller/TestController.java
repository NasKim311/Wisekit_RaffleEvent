package com.wisekit.assignment.controller;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.dto.MemberDTO;
import com.wisekit.assignment.repository.MemberRepository;
import com.wisekit.assignment.repository.RaffleRepository;
import com.wisekit.assignment.repository.RaffleRepositoryInterface;
import com.wisekit.assignment.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final RaffleService raffleService;
    private final MemberRepository memberRepository;
    private final RaffleRepository raffleRepository;
    private final RaffleRepositoryInterface raffleRepositoryInterface;

    @GetMapping("/test/byLot")
    public String testByLot() {
        MemberDTO member = new MemberDTO();

        Member memberData = new Member();


        for (int i = 1; i < 100; i++) {
            System.out.println("--------------------------------테스트 메서드 실행" + i);
            Winner winner = new Winner();

            member.setMemberName("회원" + i);
            member.setMemberPhoneNum(i + "");

            memberData = memberRepository.findByNamePhoneNum(member.getMemberName(), member.getMemberPhoneNum());

            // 표 추첨 메소드
            int byLotNum = raffleService.byLot();

            // 해당 표에 따른 등수 추첨 메소드
            int rankNum = raffleService.rankByLot(byLotNum);

            // winner 객체에 데이터 할당
            winner.setWinnerRank(rankNum);
            winner.setMember(memberData);
            winner.setByLotDate(LocalDate.now());

            raffleRepositoryInterface.save(winner);


            System.out.println("--------------------------------테스트 메서드 종료" + i);
        }

        return "redirect:/test/testByLot";
    }

    @GetMapping("/test/testByLot")
    public String resultByLot(Model model){

        model.addAttribute("firstWinnerCount", raffleRepository.firstWinnerCount());
        model.addAttribute("secondWinnerCount", raffleRepository.secondWinnerCount());
        model.addAttribute("thirdWinnerCount", raffleRepository.thirdWinnerCount());
        model.addAttribute("fourthWinnerCount", raffleRepository.fourthWinnerCount());
        model.addAttribute("testbyLotList", raffleRepositoryInterface.findAll());

        return "test/testByLot";
    }
}
