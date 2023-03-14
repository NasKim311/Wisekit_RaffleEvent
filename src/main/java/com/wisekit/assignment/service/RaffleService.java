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

		if (res >= 1 && res <= 50) {
			System.out.println("1등!!!!!!");
			System.out.println(res);
		} else if (res >= 51 && res <= 250) {
			System.out.println("2등 당첨!!!");
			System.out.println(res);
		} else if (res >= 251 && res <= 500) {
			System.out.println("3등 당첨!");
			System.out.println(res);
		} else if (res >= 501 && res <= 10800) {
			System.out.println("4등..!");
			System.out.println(res);
		} else {
			System.out.println("꽝....");
			System.out.println(res);
		}

		return res;

	} // byLOt class

} // RaffleService class
