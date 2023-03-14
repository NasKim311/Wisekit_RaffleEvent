package com.wisekit.assignment.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.wisekit.assignment.repository.RaffleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RaffleService {

	private final RaffleRepository raffleRepository;

//------------<byLot() / 이벤트 당첨하는 메소드>------------------------------------------------------------------------------------	
	public int byLOt() {

		Random r = new Random();

		int res = r.nextInt(20000);

		return res;

	} // byLOt class

} // RaffleService class
