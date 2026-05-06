package com.sist.vo;
import lombok.Data;

@Data
public class BuyStatisticsVO {
    private String id;        // 구매자 아이디
    private int buy_count;    // 구매 횟수
    private int total_pay;    // 누적 결제 금액
}