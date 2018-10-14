package com.n26.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class SummaryStatistics implements Consumer<BigDecimal> {

	public static Collector<BigDecimal, ?, SummaryStatistics> statistics() {
		return Collector.of(SummaryStatistics::new, SummaryStatistics::accept, SummaryStatistics::merge);
	}

	private BigDecimal sum = BigDecimal.ZERO, min, max;
	private long count;

	@Override
	public void accept(BigDecimal t) {
		if (count == 0) {
			Objects.requireNonNull(t);
			count = 1;
			sum = t;
			min = t;
			max = t;
		} else {
			sum = sum.add(t);
			if (min.compareTo(t) > 0)
				min = t;
			if (max.compareTo(t) < 0)
				max = t;
			count++;
		}
	}

	public SummaryStatistics merge(SummaryStatistics s) {
		if (s.count > 0) {
			if (count == 0) {
				count = s.count;
				sum = s.sum;
				min = s.min;
				max = s.max;
			} else {
				sum = sum.add(s.sum);
				if (min.compareTo(s.min) > 0)
					min = s.min;
				if (max.compareTo(s.max) < 0)
					max = s.max;
				count += s.count;
			}
		}
		return this;
	}

	public long getCount() {
		return count;
	}

	public BigDecimal getSum() {
		return sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getAverage(MathContext mc) {
		return count < 2 ? sum.setScale(2, BigDecimal.ROUND_HALF_UP)
				: sum.divide(BigDecimal.valueOf(count), mc).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMin() {
		return min.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getMax() {
		return max.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return count == 0 ? "empty" : (count + " elements between " + min + " and " + max + ", sum=" + sum);
	}
}