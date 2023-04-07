package com.wisekit.assignment.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wisekit.assignment.domain.RankResultContents;
import com.wisekit.assignment.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.dto.MemberDTO;
import com.wisekit.assignment.service.RaffleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;
    private final MemberRepository memberRepository;

    //------------<byLotPage() / 이벤트 당첨 결과 페이지로 이동>------------------------------------------------------------------------------------
    @PostMapping("/raffle/byLot")
    public String byLotPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member sessionMemberData = (Member) session.getAttribute("member");
        // 세션값 사용해서 DB조회 데이터 사용.(세션값을 위너테이블의 멤버에 넣어 넘기면 룰렛실행시 멤버 테이블에 새로 데이터가 들어가면서 룰렛실행됨)
        Member memberData = memberRepository.findByNamePhoneNum(sessionMemberData.getMemberName(), sessionMemberData.getMemberPhoneNum());

        Winner winner = new Winner();

        // 표 추첨 메소드
        int byLotNum = raffleService.byLot();

        // 해당 표에 따른 등수 추첨 메소드
        int rankNum = raffleService.rankByLot(byLotNum);

        // winner 객체에 데이터 할당
        winner.setWinnerRank(rankNum);
        winner.setMember(memberData);
        winner.setByLotDate(LocalDate.now());

        // 사용자의 당첨 데이터 저장하는 메소드
        raffleService.addByLotData(winner);

        if (rankNum == 1) {
            model.addAttribute("winnerRankTitle", RankResultContents.FIRSTWINNER.getRankResultContents());
        } else if (rankNum == 2) {
            model.addAttribute("winnerRankTitle", RankResultContents.SECONDWINNER.getRankResultContents());
        } else if (rankNum == 3) {
            model.addAttribute("winnerRankTitle", RankResultContents.THIRDWINNER.getRankResultContents());
        } else if (rankNum == 4) {
            model.addAttribute("winnerRankTitle", RankResultContents.FOURTHWINNER.getRankResultContents());
        } else {
            model.addAttribute("winnerRankTitle", RankResultContents.BOOM.getRankResultContents());
        }

        return "raffle/raffleDone";
    }

    //------------<byLotDoubleCheck() / 이벤트 사용 중복 확인>------------------------------------------------------------------------------------
    @ResponseBody
    @RequestMapping(value = "/byLotDoubleCheck", method = RequestMethod.POST)
    public String byLotDoubleCheck(@ModelAttribute MemberDTO memberDTO) {
        if (raffleService.byLotDoubleCheck(memberDTO.getMemberName(), memberDTO.getMemberPhoneNum()) != null) {
            return "중복 추첨은 불가합니다.";
        }
        return "성공";
    }

} // RaffleController class
