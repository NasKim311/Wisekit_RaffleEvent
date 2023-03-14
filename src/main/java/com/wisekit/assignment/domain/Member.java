package com.wisekit.assignment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "MemberIdSequence", sequenceName = "Member_seq", allocationSize = 1)
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MemberIdSequence")
	@Column(name = "\"회원 번호\"")
	private int memberNum;

	@Column(name = "\"회원 아이디\"")
	private String memberId;

	@Column(name = "\"회원 이름\"")
	private String memberName;

	// --------<@OneToMany / winner>-------------------------------------------------------------------------------------
	@OneToMany(mappedBy = "member")
	private List<Winner> winners = new ArrayList<Winner>();

} // Member class
