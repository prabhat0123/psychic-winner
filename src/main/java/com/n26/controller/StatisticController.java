package com.n26.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Statistic;
import com.n26.service.TrxAndStatService;

@RestController
public class StatisticController {

	public static final String STATISTICS_PATH = "/statistics";
	@Autowired
	private TrxAndStatService trxAndStatService;

	@RequestMapping(value = STATISTICS_PATH, method = RequestMethod.GET)
	public ResponseEntity<Statistic> statistics(HttpServletResponse response) {

		return new ResponseEntity<Statistic>(trxAndStatService.getStatistics(), HttpStatus.OK);

	}
}