package com.n26.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.stereotype.Service;

import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.service.TrxAndStatService;
import com.n26.utils.SummaryStatistics;

@Service
public class TrxAndStatServiceImpl implements TrxAndStatService {

	ConcurrentSkipListMap<ZonedDateTime, BigDecimal> transactionMap = new ConcurrentSkipListMap<>(
			Comparator.comparingLong(v -> v.toInstant().toEpochMilli()));

	@Override
	public void createTransaction(Transaction transaction) {
		transactionMap.put(transaction.getTimestamp(), transaction.getAmount());

	}

	@Override
	public Statistic getStatistics() {

		if (transactionMap.isEmpty()) {
			return new Statistic();

		} else {
			SummaryStatistics stats = transactionMap.tailMap(ZonedDateTime.now().minusMinutes(1)).values().stream()
					.collect(SummaryStatistics.statistics());

			return new Statistic(stats.getSum().toString(),
					stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)).toString(), stats.getMax().toString(),
					stats.getMin().toString(), stats.getCount());

		}

	}

	@Override
	public void deleteTransaction() {

		transactionMap.clear();

	}

}
