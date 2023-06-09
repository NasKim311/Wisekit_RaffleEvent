package com.wisekit.assignment.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinnerDTO {

	private int winnerNum; // 당첨자 번호
	private int winnerRank; // 당첨자 등수
	private LocalDate ByLotDate; // 당첨 날짜

} // WinnerDTO class
