package com.wisekit.assignment.domain;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.*;

@Entity
@SequenceGenerator(name = "winnerIdSequence", sequenceName = "winner_seq", allocationSize = 1)
@Data
@NoArgsConstructor
public class Winner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "winnerIdSequence")
	@Column(name = "\"당첨자 번호\"")
	private Long winnerNum;

	@Column(name = "\"당첨자 등수\"")
	private int winnerRank;
	
	@Column(name = "\"당첨 날짜\"")
	private LocalDate ByLotDate;

	// --------<@ManyToOne / member>-------------------------------------------------------------------------------------
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "\"회원 번호\"")
	private Member member;

	public void setMember(Member member) {
		this.member = member;
		member.getWinners().add(this);
	}

} // Winner class
