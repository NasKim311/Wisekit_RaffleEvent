package com.wisekit.assignment.controller;

import com.wisekit.assignment.domain.Member;
import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

//@Controller
@RequiredArgsConstructor
public class RaffleController_back {

	private final RaffleService raffleService;

//------------<byLotPage() / 이벤트 당첨 결과 페이지로 이동>------------------------------------------------------------------------------------		
	@PostMapping("/raffle/byLot")
	public String byLotPage(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		Member memberData = (Member) session.getAttribute("member");
		Winner winner = new Winner();

		String firstWinner = "1등 당첨!!!!!";
		String secondWinner = "2등 당첨!!!";
		String thirdWinner = "3등 당첨!!";
		String fourthWinner = "4등 당첨!";
		String boom = "아쉽지만 꽝입니다...";

		// 표 추첨 메소드
		int byLotNum = raffleService.byLot(memberData.getMemberNum());

		// 해당 표에 따른 등수 추첨 메소드
		int rankNum = raffleService.rankByLot(byLotNum);

		// winner 객체에 데이터 할당
		winner.setWinnerRank(rankNum);
		winner.setMember(memberData);
		winner.setByLotDate(LocalDate.now());

		// 사용자의 당첨 데이터 저장하는 메소드
		raffleService.addByLotData(winner);

		if (rankNum == 1) {
			model.addAttribute("winnerRankTitle", firstWinner);
		} else if (rankNum == 2) {
			model.addAttribute("winnerRankTitle", secondWinner);
		} else if (rankNum == 3) {
			model.addAttribute("winnerRankTitle", thirdWinner);
		} else if (rankNum == 4) {
			model.addAttribute("winnerRankTitle", fourthWinner);
		} else {
			model.addAttribute("winnerRankTitle", boom);
		}

		return "raffle/raffleDone";
	}

} // RaffleController class
