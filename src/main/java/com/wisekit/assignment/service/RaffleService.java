package com.wisekit.assignment.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
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
	@Transactional
	public int byLOt(Long MemberNum) {

		// 난수 범위 설정
		Random r = new Random();
		int byLotNum = r.nextInt(10800);

		// 이벤트 날짜 설정
		LocalDate eventStartDate = LocalDate.of(2023, 03, 19);
		LocalDate eventNowDate = LocalDate.now();
		LocalDate eventEndDate = LocalDate.of(2023, 03, 30);
		Period period = Period.between(eventStartDate, eventNowDate);

		// 일 평균 당첨자
		int dayAverageWinnerCount = 720;

		// 당첨 데이터가 아무것도 없을시 오류가 발생하기 떄문
		if (MemberNum != 1) {

			// 당첨자 모든 데이터 조회
			List<Winner> winnerdatas = (List<Winner>) raffleRepository.findAllByLotData();

			// 응모자가 일평균 당첨자보다 초과인 경우
			if (MemberNum > dayAverageWinnerCount * (period.getDays() + 1)) {
				// 일단위 총 1,2,3등이 당첨된 경우
				if (raffleRepository.firstWinnerCount() > 3.33 * (period.getDays() + 1)
						&& raffleRepository.secondWinnerCount() > 16.66 * (period.getDays() + 1)
						&& raffleRepository.thirdWinnerCount() > 33.33 * (period.getDays() + 1)) {

					// 4등만 뽑히게 난수 범위 조절
					byLotNum = r.nextInt(10800 - 801 + 1) + 801;

					for (int i = 0; i < winnerdatas.size(); i++) {
						if (winnerdatas.get(i).getWinnerByLotNum() == byLotNum) {
							byLotNum++;
						}
					}

				}

				// 응모자가 일평균 당첨자보다 미만인 경우
			} else {
				// 3등부터 오름차순으로 당첨확률이 높게 설정
				if (raffleRepository.thirdWinnerCount() > 33.33 * (period.getDays() + 1)) {
					byLotNum = r.nextInt(10800 - 801 + 1) + 801;
				} else if (raffleRepository.secondWinnerCount() > 16.66 * (period.getDays() + 1)) {
					byLotNum = r.nextInt(10800 - 801 + 1) + 801;
				} else if (raffleRepository.firstWinnerCount() > 3.33 * (period.getDays() + 1)) {
					byLotNum = r.nextInt(10800 - 801 + 1) + 801;
				}
			}

		}

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
