package com.wisekit.assignment.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "winnerIdSequence", sequenceName = "winner_seq", allocationSize = 1)
@Getter
@Setter
public class Winner {

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
	@ManyToOne
	@JoinColumn(name = "\"회원 번호\"")
	private Member member;

	public void setMember(Member member) {
		this.member = member;
		member.getWinners().add(this);
	}

} // Winner class
