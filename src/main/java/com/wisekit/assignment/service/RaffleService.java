package com.wisekit.assignment.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
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
        LocalDate eventStartDate = LocalDate.of(2023, 04, 06);
        LocalDate eventEndDate = LocalDate.of(2023, 04, 20);
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

        /*
        기간은 12/16 ~ 12/30 까지이고 당첨자는 총 1등 50명 2등 250명 3등 500명 4등 10000명 입니다.
        일별로 최대 가능한 당첨자는 일 평균 당첨자를 넘을 수 없지만,
        일 평균 당첨자를 초과하여 응모될 경우에는 1,2,3등이 전부 당첨된 경우에 한해 예외적으로 4등으로 당첨됩니다.
        또한, 일별 응모자가 적은 경우를 고려하여(1~4등의 합보다 응모가 적은 경우)
        최소 1~3등은 이벤트 종료시까지 빠짐없이 당첨될 수 있도록(혹은 최대한 당첨자가 선정될 수 있도록) 확률 조정이 되야 합니다.
        확률조정의 기준은 일단위 입니다.
        */


        // 일별로 최대 가능한 당첨자는 일 평균 당첨자를 넘을 수 없으므로
        // 일별 1등 당첨자가 초과되었을 시 2등 당첨
        if (raffleRepository.firstWinnerCount() > dayAverageFirstWinnerCount * ongoingPeriod) {
            byLotNum = secondWinnerByLot;
        }
        // 일별 2등 당첨자가 초과되었을 시 3등 당첨
        if (raffleRepository.secondWinnerCount() > dayAverageSecondWinnerCount * ongoingPeriod) {
            byLotNum = thirdWinnerByLot;
        }
        // 일별 3등 당첨자가 초과되었을 시 4등 당첨
        if (raffleRepository.thirdWinnerCount() > dayAverageThirdWinnerCount * ongoingPeriod) {
            byLotNum = fourthWinnerByLot;
        }


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


            // 응모자가 일평균 당첨자보다 적은 경우
        } else {
            // 3등부터 내림차순으로 당첨확률이 높게 설정
            // 4등이 추첨되었지만 일단위 3등이 모두 당첨되지 않은 경우 3등만 당첨
            if (rankByLot(byLotNum) == 4 && raffleRepository.thirdWinnerCount() < dayAverageThirdWinnerCount * ongoingPeriod) {
                byLotNum = thirdWinnerByLot;

                // 4등이 추첨되었지만 일단위 2등이 모두 당첨되지 않은 경우 2등만 당첨
            } else if (rankByLot(byLotNum) == 4 && raffleRepository.secondWinnerCount() < dayAverageSecondWinnerCount * ongoingPeriod) {
                byLotNum = secondWinnerByLot;

                // 4등이 추첨되었지만 일단위 1등이 모두 당첨되지 않은 경우 1등만 당첨
            } else if (rankByLot(byLotNum) == 4 && raffleRepository.firstWinnerCount() < dayAverageFirstWinnerCount * ongoingPeriod) {
                byLotNum = firstWinnerByLot;
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
//      raffleRepository.addByLotData(winnerData);
        raffleRepositoryInterface.save(winnerData);
    }

} // RaffleService class
