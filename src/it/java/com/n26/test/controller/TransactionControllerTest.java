package com.n26.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.n26.controller.TransactionController;
import com.n26.service.TrxAndStatService;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TrxAndStatService trxAndStatService;

	@Test
	public void should_Add_Transaction() throws Exception {
		mvc.perform(post(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(createTransaction(new BigDecimal("1.01"), ZonedDateTime.now(ZoneOffset.UTC))))
				.andExpect(status().isCreated());
	}

	@Test
	public void should_Handle_Old_Timestamp() throws Exception {
		mvc.perform(post(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(createTransaction(new BigDecimal("1.01"), ZonedDateTime.now(ZoneOffset.UTC).minusMinutes(5))))
				.andExpect(status().isNoContent());
	}
	@Test
	public void should_Handle_null_Timestamp() throws Exception {
		mvc.perform(post(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
				.content("{\"amount\":\"" + 10.00 + "\"}"))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void should_Handle_Future_Timestamp() throws Exception {
		mvc.perform(post(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(createTransaction(new BigDecimal("1.01"), ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(5))))
				.andExpect(status().isUnprocessableEntity());
	}
	@Test 
	public void should_Handle_Invalid_DateFormat() throws Exception {
		mvc.perform(post(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(createInvalidTransactionRequest()))
				.andExpect(status().isUnprocessableEntity());
	}
	
	@Test 
	public void should_Handle_Invalid_Json() throws Exception {
		mvc.perform(post(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON)
				.content("{\" Hello World \" }"))
				.andExpect(status().isBadRequest());
	}
	@Test
	public void should_Delete_All_Transaction() throws Exception {
		mvc.perform(delete(TransactionController.TRANSACTION_PATH).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	private String createTransaction(BigDecimal amount, ZonedDateTime timestamp) {

		return "{\"amount\":\"" + amount.toString() + "\",\"timestamp\":\"" + timestamp.toString() + "\"}";

	}
	
	private String createInvalidTransactionRequest() {

		return "{\"amount\":\"" +10.00 + "\",\"timestamp\":\"" +"4/23/2018 11:32 PM"+ "\"}";

	}

}
