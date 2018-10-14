package com.n26.service;

import com.n26.model.Statistic;
import com.n26.model.Transaction;

public interface TrxAndStatService {
	
	void createTransaction(Transaction transaction);
	Statistic getStatistics();
	void deleteTransaction();

}
