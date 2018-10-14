package com.n26.model;

import java.io.Serializable;

public class Statistic implements Serializable {

	private String sum = "0.00";
	private String avg = "0.00";
	private String max = "0.00";
	private String min = "0.00";
	private long count;

	public Statistic() {
		super();

	}

	public Statistic(String sum, String avg, String max, String min, long count) {
		super();
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
