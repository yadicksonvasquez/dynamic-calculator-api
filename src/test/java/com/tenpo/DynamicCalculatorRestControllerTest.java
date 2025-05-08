/**
 * 
 */
package com.tenpo;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.calculator.service.IDynamicCalculatorService;
import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.dto.SumResponseDTO;
import com.tenpo.restcontroller.DynamicCalculatorRestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author YADICKSONVM
 */
@AutoConfigureWebClient
@WebMvcTest(DynamicCalculatorRestController.class)
@ExtendWith(MockitoExtension.class)
public class DynamicCalculatorRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private IDynamicCalculatorService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void applySumTest() throws Exception {

		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		Optional<SumResponseDTO> response = Optional.of(
				SumResponseDTO.builder().result(BigDecimal.valueOf(30)).applyPercent(BigDecimal.valueOf(0.5)).build());

		Mockito.when(service.applySum(Mockito.any(SumRequestParameterDTO.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/calculator/addNumbers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value(response.get().getResult()))
				.andExpect(jsonPath("$.applyPercent").value(response.get().getApplyPercent()));
	}

}
