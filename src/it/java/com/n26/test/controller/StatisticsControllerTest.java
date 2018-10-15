package com.n26.test.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.n26.controller.StatisticController;
import com.n26.model.Statistic;
import com.n26.service.TrxAndStatService;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticController.class)
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

	@MockBean
	private TrxAndStatService trxAndStatService;

	@Test
	public void shouldReturnSampleStatistics() throws Exception {
		when(trxAndStatService.getStatistics()).thenReturn(new Statistic("10.20", "5.10", "5.20", "5.00", 2));

		mvc.perform(get(StatisticController.STATISTICS_PATH).accept("application/json")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json")).andExpect(jsonPath("count", is(2)))
				.andExpect(jsonPath("sum", is("10.20"))).andExpect(jsonPath("avg", is("5.10")))
				.andExpect(jsonPath("min", is("5.00"))).andExpect(jsonPath("max", is("5.20")));
	}
}