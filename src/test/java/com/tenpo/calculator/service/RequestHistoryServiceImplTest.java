/**
 * 
 */
package com.tenpo.calculator.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.dto.RequestHistoryDTO;
import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.dto.SumResponseDTO;
import com.tenpo.exception.TenpoBusinessErrorException;
import com.tenpo.model.RequestHistory;
import com.tenpo.model.repository.IRequestHistoryRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * 
 */
@ExtendWith(MockitoExtension.class)
public class RequestHistoryServiceImplTest {

	@Mock
	private IRequestHistoryRepository repository;

	private IRequestHistoryService service;

	@Spy
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		this.service = new RequestHistoryServiceImpl(repository);
	}

	@Test
	void addTest() throws Exception {

		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		SumResponseDTO response = SumResponseDTO.builder().result(BigDecimal.valueOf(30))
				.applyPercent(BigDecimal.valueOf(0.5)).build();

		RequestHistoryDTO requestHistory = RequestHistoryDTO.builder().id(1l).created(Instant.now())
				.endpoint("/api/v1/calculator/addNumbers").parameters(objectMapper.writeValueAsString(request))
				.response(objectMapper.writeValueAsString(response)).build();

		RequestHistory entity = RequestHistory.builder().endpoint(requestHistory.getEndpoint())
				.parameters(requestHistory.getParameters()).response(requestHistory.getResponse())
				.created(requestHistory.getCreated()).build();

		Mockito.when(repository.save(entity)).thenReturn(entity);

		var result = service.add(requestHistory);

		assertThat(result).isNotNull();
		assertThat(result.get()).isEqualTo(true);
	}

	@Test
	void addThrowExceptionTest() throws Exception {

		assertThrows(TenpoBusinessErrorException.class, () -> {
			service.add(null);
		});

	}

	@Test
	void findAllTest() throws Exception {

		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		SumResponseDTO response = SumResponseDTO.builder().result(BigDecimal.valueOf(30))
				.applyPercent(BigDecimal.valueOf(0.5)).build();

		List<RequestHistoryDTO> findAllResponse = new ArrayList<>();
		findAllResponse.add(RequestHistoryDTO.builder().id(1l).created(Instant.now())
				.endpoint("/api/v1/calculator/addNumbers").parameters(objectMapper.writeValueAsString(request))
				.response(objectMapper.writeValueAsString(response)).build());

		Mockito.when(repository.findAll()).thenReturn(findAllResponse.stream()
				.map(h -> RequestHistory.builder().created(h.getCreated()).endpoint(h.getEndpoint()).build()).toList());

		var result = service.findAll();

		assertThat(result).isNotNull();
		assertThat(result.get().size()).isEqualTo(1);
	}

}
