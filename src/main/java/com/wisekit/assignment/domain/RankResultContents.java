package com.wisekit.assignment.domain;

// 코드 당첨 내용 enum
public enum RankResultContents {
    FIRSTWINNER("1등 당첨!!!!") , SECONDWINNER("2등 당첨!!!") , THIRDWINNER("3등 당첨!!") , FOURTHWINNER("4등 당첨!") , BOOM("아쉽지만 꽝입니다...");

    private final String value;

    private RankResultContents(String value) {
      this.value = value;
    }

    public String getRankResultContents(){
        return value;
    }
}
