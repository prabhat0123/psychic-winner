package com.n26.test.service.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.service.impl.TrxAndStatServiceImpl;

@RunWith(SpringRunner.class)
public class TrxAndStatServiceImplTest {

	@InjectMocks
	private TrxAndStatServiceImpl trxAndStatServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void should_create_Transaction() throws InterruptedException {

		// clean up and data preparation
		Statistic oldStats = trxAndStatServiceImpl.getStatistics();
		// call
		trxAndStatServiceImpl.createTransaction(createTransactionObject());
		
		Statistic newStats = trxAndStatServiceImpl.getStatistics();
		assertThat(newStats.getCount(), is(oldStats.getCount()+1));
		
	}

	@Test
	public void should_Return_all_zero_Value_test() throws InterruptedException {

		// clean up and data preparation
		trxAndStatServiceImpl.deleteTransaction();
		// call
		Statistic stats = trxAndStatServiceImpl.getStatistics();

		// assert
		assertThat(stats.getAvg(), is("0.00"));
		assertThat(stats.getMax(), is("0.00"));
		assertThat(stats.getMin(), is("0.00"));
		assertThat(stats.getSum(), is("0.00"));
		assertThat(stats.getCount(), is(0l));

	}

	@Test
	public void should_Return_non_zero_Value_test() throws InterruptedException {

		// clean up and data preparation
		trxAndStatServiceImpl.deleteTransaction();
		trxAndStatServiceImpl.createTransaction(createTransactionObject());
		// assert
		Statistic stats = trxAndStatServiceImpl.getStatistics();

		// assert
		assertThat(stats.getAvg(), is("10.01"));
		assertThat(stats.getMax(), is("10.01"));
		assertThat(stats.getMin(), is("10.01"));
		assertThat(stats.getSum(), is("10.01"));
		assertThat(stats.getCount(), is(1l));
	}

	@Test
	public void should_delete_Transaction() throws InterruptedException {

		// clean up and data preparation
		trxAndStatServiceImpl.createTransaction(createTransactionObject());
		// call
		trxAndStatServiceImpl.deleteTransaction();
		Statistic stats = trxAndStatServiceImpl.getStatistics();
		// assert
		assertThat(stats.getCount(), is(0l));
	}
	
	@Test
	public void should_handle_duplicate_key_test() throws InterruptedException {

		// clean up and data preparation
		trxAndStatServiceImpl.deleteTransaction();
		Transaction trx=createTransactionObject();
		trxAndStatServiceImpl.createTransaction(trx);
		trxAndStatServiceImpl.createTransaction(trx);
		// assert
		Statistic stats = trxAndStatServiceImpl.getStatistics();

		// assert
		assertThat(stats.getAvg(), is("10.01"));
		assertThat(stats.getMax(), is("10.01"));
		assertThat(stats.getMin(), is("10.01"));
		assertThat(stats.getSum(), is("20.02"));
		assertThat(stats.getCount(), is(2l));
	}

	private Transaction createTransactionObject() {
		Transaction trx = new Transaction();
		trx.setTimestamp(ZonedDateTime.now(ZoneOffset.UTC));
		trx.setAmount(new BigDecimal("10.01"));
		return trx;
	}
	
	

}