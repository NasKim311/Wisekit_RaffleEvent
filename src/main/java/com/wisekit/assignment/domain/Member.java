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
@SequenceGenerator(name = "memberIdSequence", sequenceName = "member_seq", allocationSize = 1)
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberIdSequence")
	@Column(name = "\"회원 번호\"")
	private Long memberNum;

	@Column(name = "\"회원 이름\"")
	private String memberName;

	@Column(name = "\"회원 핸드폰 번호\"")
	private String memberPhoneNum;

	// --------<@OneToMany /
	// winner>-------------------------------------------------------------------------------------
	@OneToMany(mappedBy = "member")
	private List<Winner> winners = new ArrayList<Winner>();

} // Member class
