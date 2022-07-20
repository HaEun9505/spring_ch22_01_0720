package com.haeun.ch0720.dto;

//데이터 이동 객체
public class TicketDto {
	
	private String consumerid;
	private int amount;
	
	public String getConsumerid() {
		return consumerid;
	}
	public void setConsumerid(String consumerid) {
		this.consumerid = consumerid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
