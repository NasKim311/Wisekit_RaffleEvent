package com.wisekit.assignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WinnerDTO_back {

	private int winnerNum; // 당첨자 번호
	private int winnerByLotNum; // 당첨자 표 번호
	private int winnerRank; // 당첨자 등수
	private LocalDate ByLotDate; // 당첨 날짜

} // WinnerDTO class
