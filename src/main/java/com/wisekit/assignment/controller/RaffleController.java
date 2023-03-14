package com.wisekit.assignment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wisekit.assignment.service.RaffleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RaffleController {

	private final RaffleService raffleService;

//------------<indexPage() / 메인 페이지로 이동>------------------------------------------------------------------------------------		
	@GetMapping("/")
	public String indexPage() {
		return "index";
	}
	
//------------<byLotPage() / 이벤트 당첨 페이지로 이동>------------------------------------------------------------------------------------		
	@GetMapping("/raffle/byLot")
	public String byLotPage(Model model , HttpServletRequest request) {

		HttpSession session = request.getSession();
		
		int byLotNum = raffleService.byLOt();
		String firstWinner = "1등 당첨!!!!!";
		String secondWinner = "2등 당첨!!!";
		String thirdWinner = "3등 당첨!!";
		String fourthWinner = "4등 당첨!";
		String boom = "아쉽지만 꽝입니다...";

		if (byLotNum >= 1 && byLotNum <= 50) {
			model.addAttribute("winnerRank" , firstWinner);
		} else if (byLotNum >= 51 && byLotNum <= 250) {
			model.addAttribute("winnerRank" , secondWinner);
		} else if (byLotNum >= 251 && byLotNum <= 500) {
			model.addAttribute("winnerRank" , thirdWinner);
		} else if (byLotNum >= 501 && byLotNum <= 10800) {
			model.addAttribute("winnerRank" , fourthWinner);
		} else {
			model.addAttribute("winnerRank" , boom);
		}

		return "raffle/raffleDone";
	}
	
	
} // RaffleController class
