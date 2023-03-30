package com.wisekit.assignment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

//@Entity
@SequenceGenerator(name = "winnerIdSequence", sequenceName = "winner_seq", allocationSize = 1)
@Getter
@Setter
public class Winner_back {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "winnerIdSequence")
	@Column(name = "\"당첨자 번호\"")
	private Long winnerNum;

	@Column(name = "\"당첨자 표 번호\"")
	private int winnerByLotNum;

	@Column(name = "\"당첨자 등수\"")
	private int winnerRank;
	
	@Column(name = "\"당첨 날짜\"")
	private LocalDate ByLotDate;

	// --------<@ManyToOne / member>-------------------------------------------------------------------------------------
//	@ManyToOne
//	@JoinColumn(name = "\"회원 번호\"")
//	private Member member;
//
//	public void setMember(Member member) {
//		this.member = member;
//		member.getWinners().add(this);
//	}

} // Winner class
