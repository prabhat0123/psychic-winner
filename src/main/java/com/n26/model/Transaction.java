package com.n26.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;

public class Transaction implements Serializable{

	@Min(0)
	private BigDecimal amount;

	//@WithinSeconds(duration = 60,message="Transaction is older than 60 seconds")
	@PastOrPresent(message="Transaction not be in future time")
	private ZonedDateTime timestamp;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

}