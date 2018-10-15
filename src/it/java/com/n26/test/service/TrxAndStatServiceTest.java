package com.n26.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.service.TrxAndStatService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrxAndStatServiceTest {

	@Autowired
	private TrxAndStatService trxAndStatService;

	@Test
	public void contextLoads() {
		Assert.assertNotNull("trxAndStatService can not be null", trxAndStatService);
	}

}