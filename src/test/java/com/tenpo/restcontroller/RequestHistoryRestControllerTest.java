/**
 * 
 */
package com.tenpo.restcontroller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.calculator.service.IRequestHistoryService;
import com.tenpo.dto.RequestHistoryDTO;
import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.dto.SumResponseDTO;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;

/**
 * 
 */
@AutoConfigureWebClient
@WebMvcTest(RequestHistoryRestController.class)
public class RequestHistoryRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private IRequestHistoryService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void findAllTest() throws Exception {
		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		Optional<SumResponseDTO> response = Optional.of(
				SumResponseDTO.builder().result(BigDecimal.valueOf(30)).applyPercent(BigDecimal.valueOf(0.5)).build());

		List<RequestHistoryDTO> findAllResponse = new ArrayList<>();
		findAllResponse.add(RequestHistoryDTO.builder().id(1l).created(Instant.now())
				.endpoint("/api/v1/calculator/addNumbers").parameters(objectMapper.writeValueAsString(request))
				.response(objectMapper.writeValueAsString(response)).build());

		Mockito.when(service.findAll()).thenReturn(CompletableFuture.completedFuture(findAllResponse));

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/calculator-history").accept(MediaType.APPLICATION_JSON))
				.andExpect(request().asyncStarted()).andReturn();

		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void addHistoryTest() throws Exception {
		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		Optional<SumResponseDTO> response = Optional.of(
				SumResponseDTO.builder().result(BigDecimal.valueOf(30)).applyPercent(BigDecimal.valueOf(0.5)).build());

		RequestHistoryDTO requestHistory = RequestHistoryDTO.builder().id(1l).created(Instant.now())
				.endpoint("/api/v1/calculator/addNumbers").parameters(objectMapper.writeValueAsString(request))
				.response(objectMapper.writeValueAsString(response)).build();

		Mockito.when(service.add(requestHistory)).thenReturn(CompletableFuture.completedFuture(true));

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/calculator-history")
						.content(objectMapper.writeValueAsString(requestHistory))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(request().asyncStarted()).andReturn();

		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk()).andDo(print());

	}

}
