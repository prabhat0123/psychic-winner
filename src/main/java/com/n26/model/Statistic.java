package com.n26.model;

public class Statistic {

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

	public String getAvg() {
		return avg;
	}

	public String getMax() {
		return max;
	}

	public String getMin() {
		return min;
	}

	public long getCount() {
		return count;
	}
}
