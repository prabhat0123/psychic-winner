package com.n26.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Transaction;
import com.n26.service.TrxAndStatService;

@RestController
public class TransactionController {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
	private static final int duration = 60;

	@Autowired
	private TrxAndStatService trxAndStatService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<Void> transaction(@Valid @RequestBody Transaction transaction, HttpServletResponse response,
			Errors errors) {

		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

		if (transaction.getTimestamp() == null || transaction.getTimestamp().isBefore(now.minusSeconds(duration))) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			trxAndStatService.createTransaction(transaction);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}

	}

	@RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
	public ResponseEntity<Void> transaction(HttpServletResponse response) {

		trxAndStatService.deleteTransaction();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}