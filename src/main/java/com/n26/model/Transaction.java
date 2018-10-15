package com.n26.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;

public class Transaction{
	
	@Min(0)
	private BigDecimal amount;

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