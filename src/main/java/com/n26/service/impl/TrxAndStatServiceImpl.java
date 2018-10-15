package com.n26.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.service.TrxAndStatService;
import com.n26.utils.SummaryStatistics;

@Service
public class TrxAndStatServiceImpl implements TrxAndStatService {
	private static final Logger LOG = LoggerFactory.getLogger(TrxAndStatServiceImpl.class);

	private static final ConcurrentSkipListMap<ZonedDateTime, List<BigDecimal>> transactionMap = new ConcurrentSkipListMap<>(
			Comparator.comparingLong(v -> v.toInstant().toEpochMilli()));

	private ReentrantLock lock = new ReentrantLock();

	@Override
	public void createTransaction(Transaction transaction) {
		lock.lock();
		try {
			LOG.info("Contains Key in Map : {} ", transactionMap.containsKey(transaction.getTimestamp()));
			if (transactionMap.containsKey(transaction.getTimestamp())) {
				transactionMap.get(transaction.getTimestamp()).add(transaction.getAmount());
			} else {
				List<BigDecimal> amountList = new ArrayList<>();
				amountList.add(transaction.getAmount());
				transactionMap.put(transaction.getTimestamp(), amountList);
			}
		} finally {
			Set<ZonedDateTime> oldKeySet = transactionMap.headMap(ZonedDateTime.now().minusMinutes(1)).keySet();
			this.removeOldData(oldKeySet, true);
			lock.unlock();
		}

	}

	@Override
	public Statistic getStatistics() {

		Statistic result = new Statistic();
		if (!transactionMap.isEmpty()) {

			ConcurrentNavigableMap<ZonedDateTime, List<BigDecimal>> recentTransactionMap = transactionMap
					.tailMap(ZonedDateTime.now().minusMinutes(1));
			
			Set<ZonedDateTime> keySet = recentTransactionMap.keySet();
			Collection<List<BigDecimal>> amountListCollection = recentTransactionMap.values();
			
			List<BigDecimal> amountCollect = amountListCollection.stream().collect(ArrayList::new, List::addAll,
					List::addAll);

			SummaryStatistics stats = amountCollect.stream().collect(SummaryStatistics.statistics());

			result = new Statistic(stats.getSum().toString(),
					stats.getAverage(new MathContext(5, RoundingMode.HALF_UP)).toString(), stats.getMax().toString(),
					stats.getMin().toString(), stats.getCount());
			this.removeOldData(keySet, false);
		}
		LOG.info("Stat Response : {} ", result);
		return result;
	}

	@Override
	public void deleteTransaction() {
		transactionMap.clear();
	}

	private void removeOldData(Set<ZonedDateTime> keySet, boolean flag) {
		if (flag)
			transactionMap.keySet().removeAll(keySet);
		else
			transactionMap.keySet().retainAll(keySet);
	}

}
