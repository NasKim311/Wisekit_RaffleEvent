package com.wisekit.assignment.service;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wisekit.assignment.repository.RaffleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RaffleService {

	private final RaffleRepository raffleRepository;

//------------<byLot() / 표 추첨하는 메소드>------------------------------------------------------------------------------------	
	@Transactional
	public int byLOt() {

		Random r = new Random();
		int byLotNum = r.nextInt(20000);

		return byLotNum;
	}
	
//------------<byLot() / 표 추첨하는 메소드>------------------------------------------------------------------------------------	
	@Transactional
	public void addByLotData(Long memberNum) {
//		raffleRepository.addByLotData(memberNum );
	}
	

} // RaffleService class
