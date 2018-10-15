package com.n26.test.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.utils.SummaryStatistics;

@RunWith(SpringRunner.class)
public class SummaryStatisticsTest {

	@InjectMocks
	private SummaryStatistics summaryStatistics;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void should_provide_statistics() {
		SummaryStatistics stats = createRequestList(2d,4d,6d,8d).stream().collect(SummaryStatistics.statistics());
		assertThat(stats.getCount(), is(4l));
		assertThat(stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)), is(new BigDecimal("5.00")));
		assertThat(stats.getMin(), is(new BigDecimal("2.00")));
		assertThat(stats.getMax(), is(new BigDecimal("8.00")));
		assertThat(stats.getSum(), is(new BigDecimal("20.00")));
	}

	@Test
	public void should_merge_statistics() {
		SummaryStatistics stats1 = createRequestList(2d,4d,6d,8d).stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats2 = createRequestList(1d,4d,6d,18d).stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats = stats1.merge(stats2);

		assertThat(stats.getCount(), is(8l));
		assertThat(stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)), is(new BigDecimal("6.13")));
		assertThat(stats.getMin(), is(new BigDecimal("1.00")));
		assertThat(stats.getMax(), is(new BigDecimal("18.00")));
		assertThat(stats.getSum(), is(new BigDecimal("49.00")));
	}
	@Test
	public void should_merge_statistics_2() {
		SummaryStatistics stats2 = createRequestList(1d,4d,6d,18d).stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats1 = createRequestList(2d,4d,6d,8d).stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats = stats2.merge(stats1);

		assertThat(stats.getCount(), is(8l));
		assertThat(stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)), is(new BigDecimal("6.13")));
		assertThat(stats.getMin(), is(new BigDecimal("1.00")));
		assertThat(stats.getMax(), is(new BigDecimal("18.00")));
		assertThat(stats.getSum(), is(new BigDecimal("49.00")));
	}
	
	@Test
	public void should_merge_empty_list_statistics() {
		SummaryStatistics stats1 = createRequestList(2d,4d,6d,8d).stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats2 = new ArrayList<BigDecimal>().stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats = stats1.merge(stats2);

		assertThat(stats.getCount(), is(4l));
		assertThat(stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)), is(new BigDecimal("5.00")));
		assertThat(stats.getMin(), is(new BigDecimal("2.00")));
		assertThat(stats.getMax(), is(new BigDecimal("8.00")));
		assertThat(stats.getSum(), is(new BigDecimal("20.00")));
	}
	@Test
	public void should_merge_empty_list_statistics_2() {
		SummaryStatistics stats2 = new ArrayList<BigDecimal>().stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats1 = createRequestList(2d,4d,6d,8d).stream().collect(SummaryStatistics.statistics());
		SummaryStatistics stats = stats2.merge(stats1);

		assertThat(stats.getCount(), is(4l));
		assertThat(stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)), is(new BigDecimal("5.00")));
		assertThat(stats.getMin(), is(new BigDecimal("2.00")));
		assertThat(stats.getMax(), is(new BigDecimal("8.00")));
		assertThat(stats.getSum(), is(new BigDecimal("20.00")));
	}

	private List<BigDecimal> createRequestList(double num1, double num2, double num3, double num4) {
		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal(num1));
		list.add(new BigDecimal(num2));
		list.add(new BigDecimal(num3));
		list.add(new BigDecimal(num4));
		return list;
	}

}