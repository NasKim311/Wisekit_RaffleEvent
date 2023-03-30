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
    public int byLot(Long MemberNum) {

        Random r = new Random();

        // 당첨순위별 인원 변수
        int firstWinnerCount = 50;
        int secondWinnerCount = 250;
        int thirdWinnerCount = 500;
        int fourthWinnerCount = 10000;
        int allWinnerCount = firstWinnerCount + secondWinnerCount + thirdWinnerCount + fourthWinnerCount;

        // 순위별 난수 생성 변수 -> [ 1등 = 1~50 , 2등 = 51~300 , 3등 = 301~800 , 4등 = 801~10800 ]
        int firstWinnerbyLot = r.nextInt(firstWinnerCount);
        int secondWinnerbyLot = r.nextInt(secondWinnerCount) + firstWinnerCount + 1;
        int thirdWinnerbyLot = r.nextInt(thirdWinnerCount) + firstWinnerCount + secondWinnerCount + 1;
        int fourthWinnerbyLot = r.nextInt(fourthWinnerCount) + firstWinnerCount + secondWinnerCount + thirdWinnerCount + 1;

        // 순위를 결정짓는 난수를 담을 변수
        int byLotNum = 0;

        // 이벤트 날짜별 변수
        LocalDate eventStartDate = LocalDate.of(2023, 03, 30);
        LocalDate eventEndDate = LocalDate.of(2023, 04, 13);
        LocalDate eventNowDate = LocalDate.now();

        // 진행기간별 변수
        int ongoingPeriod = Period.between(eventStartDate, eventNowDate).getDays() + 1;
        int wholePeriod = Period.between(eventStartDate, eventEndDate).getDays() + 1;

        // 일 평균 당첨자
        int dayAverageWinnerCount = allWinnerCount / wholePeriod;













        // 당첨 데이터가 아무것도 없을시 오류가 발생하기 떄문
        if (MemberNum != 1) {

            // 당첨자 모든 데이터 조회
//			List<Winner> winnerdatas = (List<Winner>) raffleRepository.findAllByLotData();

            // 응모자가 일평균 당첨자보다 초과인 경우
            if (MemberNum > dayAverageWinnerCount * (ongoingPeriod + 1)) {
                // 일단위 총 1,2,3등이 당첨된 경우
                if (raffleRepository.firstWinnerCount() > 3.33 * (ongoingPeriod + 1)
                        && raffleRepository.secondWinnerCount() > 16.66 * (ongoingPeriod + 1)
                        && raffleRepository.thirdWinnerCount() > 33.33 * (ongoingPeriod + 1)) {

                    // 4등만 뽑히게 난수 범위 조절
                    byLotNum = r.nextInt(10800 - 801 + 1) + 801;


                }

                // 응모자가 일평균 당첨자보다 미만인 경우
            } else {
                // 3등부터 오름차순으로 당첨확률이 높게 설정
                if (raffleRepository.thirdWinnerCount() > 33.33 * (ongoingPeriod + 1)) {
                    byLotNum = r.nextInt(10800 - 801 + 1) + 801;
                } else if (raffleRepository.secondWinnerCount() > 16.66 * (ongoingPeriod + 1)) {
                    byLotNum = r.nextInt(10800 - 801 + 1) + 801;
                } else if (raffleRepository.firstWinnerCount() > 3.33 * (ongoingPeriod + 1)) {
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
        } else if (byLotNum >= 51 && byLotNum <= 300) {
            rankNum = 2;
        } else if (byLotNum >= 301 && byLotNum <= 800) {
            rankNum = 3;
        } else if (byLotNum >= 801 && byLotNum <= 10800) {
            rankNum = 4;
        } else {
            rankNum = 5;
        }

        return rankNum;
    }

    //------------<addByLotData() / 추첨 정보 저장 메소드>------------------------------------------------------------------------------------
    @Transactional
    public void addByLotData(Winner winnerData) {
        raffleRepository.addByLotData(winnerData);

    }

} // RaffleService class
