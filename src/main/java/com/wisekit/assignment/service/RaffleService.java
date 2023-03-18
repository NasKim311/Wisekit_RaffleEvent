package com.wisekit.assignment.service;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.repository.RaffleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RaffleService {

	private final RaffleRepository raffleRepository;

//------------<byLot() / 표 추첨하는 메소드>------------------------------------------------------------------------------------	
	public int byLOt() {

		Random r = new Random();
		int byLotNum = r.nextInt(20000);

		return byLotNum;
	}

//------------<rankByLot() / 추첨된 표에 따른 등수 추첨 메소드>------------------------------------------------------------------------------------	
	public int rankByLot(int byLotNum) {

		// rankNum = 1(1등) , 2(2등) , 3(3등) , 4(4등) , 5(꽝)
		int rankNum = 0;

		if (byLotNum >= 1 && byLotNum <= 50) {
			rankNum = 1;
			System.out.println("1등");
		} else if (byLotNum >= 51 && byLotNum <= 300) {
			rankNum = 2;
			System.out.println("2등");
		} else if (byLotNum >= 301 && byLotNum <= 800) {
			rankNum = 3;
			System.out.println("3등");
		} else if (byLotNum >= 801 && byLotNum <= 10800) {
			rankNum = 4;
			System.out.println("4등");
		} else {
			rankNum = 5;
			System.out.println("꽝");
		}

		return rankNum;
	}

//------------<addByLotData() / 추첨 정보 저장 메소드>------------------------------------------------------------------------------------	
	@Transactional
	public void addByLotData(Winner winnerData) {
		raffleRepository.addByLotData(winnerData);

	}

} // RaffleService class
