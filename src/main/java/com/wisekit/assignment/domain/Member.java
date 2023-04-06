package com.wisekit.assignment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"회원 번호\"")
    private long memberNum;

    @Column(name = "\"회원 이름\"")
    private String memberName;

    @Column(name = "\"회원 핸드폰 번호\"")
    private String memberPhoneNum;

    // --------<@OneToMany / winner>-------------------------------------------------------------------------------------
    @OneToMany(mappedBy = "member")
    private List<Winner> winners = new ArrayList<Winner>();

    @Builder
    public Member(String memberName, String memberPhoneNum) {
        this.memberName = memberName;
        this.memberPhoneNum = memberPhoneNum;
    }
} // Member class
