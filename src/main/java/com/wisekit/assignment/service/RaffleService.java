package com.wisekit.assignment.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import javax.transaction.Transactional;

import com.wisekit.assignment.repository.RaffleRepositoryInterface;
import org.springframework.stereotype.Service;

import com.wisekit.assignment.domain.Winner;
import com.wisekit.assignment.repository.RaffleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RaffleService {

    private final RaffleRepository raffleRepository;
    private final RaffleRepositoryInterface raffleRepositoryInterface;

    // 당첨순위별 인원 변수
    private final int allFirstWinnerCount = 50;
    private final int allSecondWinnerCount = 250;
    private final int allThirdWinnerCount = 500;
    private final int allFourthWinnerCount = 10000;
    private final int allWinnerCount = allFirstWinnerCount + allSecondWinnerCount + allThirdWinnerCount + allFourthWinnerCount;

    //------------<byLot() / 표 추첨하는 메소드>------------------------------------------------------------------------------------
//    @Transactional
    public int byLot() {
        Random r = new Random();

        // 순위별 난수 생성 변수 -> [ 1등 = 1~50 , 2등 = 51~300 , 3등 = 301~800 , 4등 = 801~10800 , all = 1~10800 ]
        int firstWinnerByLot = r.nextInt(allFirstWinnerCount) + 1;
        int secondWinnerByLot = r.nextInt(allSecondWinnerCount) + allFirstWinnerCount + 1;
        int thirdWinnerByLot = r.nextInt(allThirdWinnerCount) + allFirstWinnerCount + allSecondWinnerCount + 1;
        int fourthWinnerByLot = r.nextInt(allFourthWinnerCount) + allFirstWinnerCount + allSecondWinnerCount + allThirdWinnerCount + 1;
        int allWinnerByLot = r.nextInt(allWinnerCount) + 1;

        // 순위를 결정짓는 난수를 담은 변수(전체범위)
        int byLotNum = allWinnerByLot;

        // 이벤트 날짜별 변수
        LocalDate eventStartDate = LocalDate.of(2023, 04, 07);
        LocalDate eventEndDate = LocalDate.of(2023, 04, 21);
        LocalDate eventNowDate = LocalDate.now();

        // 진행기간별 변수
        int ongoingPeriod = Period.between(eventStartDate, eventNowDate).getDays() + 1;
        int wholePeriod = Period.between(eventStartDate, eventEndDate).getDays() + 1;

        // 순위별 당첨자 평균(일단위)
        int dayAverageFirstWinnerCount = allFirstWinnerCount / wholePeriod;     // 3.33...
        int dayAverageSecondWinnerCount = allSecondWinnerCount / wholePeriod;   // 16.66...
        int dayAverageThirdWinnerCount = allThirdWinnerCount / wholePeriod;     // 33.33...
        int dayAverageFourthWinnerCount = allFourthWinnerCount / wholePeriod;   // 666.66...
        int dayAverageAllWinnerCount = allWinnerCount / wholePeriod;            // 720

        // 응모자 인원
        int applicantCount = raffleRepository.findApplicantCountCount();

        // 응모자가 일평균 당첨자보다 초과인 경우
        if (applicantCount > dayAverageAllWinnerCount * ongoingPeriod) {
            // 1등이 추첨되었지만 일단위 1등이 모두 당첨된 경우 4등만 당첨
            if (rankByLot(byLotNum) == 1 && raffleRepository.firstWinnerCount() > dayAverageFirstWinnerCount * ongoingPeriod) {
                byLotNum = fourthWinnerByLot;

            // 2등이 추첨되었지만 일단위 2등이 모두 당첨된 경우 4등만 당첨
            } else if (rankByLot(byLotNum) == 2 && raffleRepository.secondWinnerCount() > dayAverageSecondWinnerCount * ongoingPeriod) {
                byLotNum = fourthWinnerByLot;

            // 3등이 추첨되었지만 일단위 3등이 모두 당첨된 경우 4등만 당첨
            } else if (rankByLot(byLotNum) == 3 && raffleRepository.thirdWinnerCount() > dayAverageThirdWinnerCount * ongoingPeriod) {
                byLotNum = fourthWinnerByLot;
            }

        }


        return byLotNum;
    }

    //------------<rankByLot() / 추첨된 표에 따른 등수 추첨 메소드>------------------------------------------------------------------------------------
    public int rankByLot(int byLotNum) {
        // rankNum = 1(1등) , 2(2등) , 3(3등) , 4(4등) , 5(꽝 / 꽝기능 넣을 수 있음. 현재는 x)
        int rankNum = 0;

        if (byLotNum >= 1 && byLotNum <= allFirstWinnerCount) {
            rankNum = 1;
        } else if (byLotNum >= (1 + allFirstWinnerCount) && byLotNum <= (allFirstWinnerCount + allSecondWinnerCount)) {
            rankNum = 2;
        } else if (byLotNum >= (1 + allFirstWinnerCount + allSecondWinnerCount) && byLotNum <= (allFirstWinnerCount + allSecondWinnerCount + allThirdWinnerCount)) {
            rankNum = 3;
        } else if (byLotNum >= (1 + allFirstWinnerCount + allSecondWinnerCount + allThirdWinnerCount) && byLotNum <= (allFirstWinnerCount + allSecondWinnerCount + allThirdWinnerCount + allFourthWinnerCount)) {
            rankNum = 4;
        } else {
            rankNum = 5;
        }

        return rankNum;
    }

    //------------<addByLotData() / 추첨 정보 저장 메소드>------------------------------------------------------------------------------------
    @Transactional
    public void addByLotData(Winner winnerData) {
        raffleRepositoryInterface.save(winnerData);
    }

    //------------<byLotDoubleCheck() /  추첨 이벤트 중복 사용 확인 메소드>------------------------------------------------------------------------------------
    @Transactional
    public Winner byLotDoubleCheck(String memberName, String memberPhoneNum) {
        Winner memberDataInWinner = raffleRepository.findByMemberNamePhoneNum(memberName, memberPhoneNum);

        return memberDataInWinner;
    }

} // RaffleService class
