/**
 * 
 */
package com.tenpo.calculator.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
public class IExternalServiceClientImplTest {

	@Mock
	private RestTemplate restTemplate;

	@Value("${endpoints.getPercentage}")
	private String urlGetPercentage;

	private IExternalServiceClient service;

	@BeforeEach
	public void setUp() {
		this.service = new IExternalServiceClientImpl(restTemplate);
	}

	@Test
	public void getPercentage() throws Exception {
		String percentaje = "0.5";
		Mockito.when(restTemplate.getForObject(urlGetPercentage, String.class)).thenReturn(percentaje);

		var result = service.getPercentage();

		assertThat(result).isNotNull();
		assertThat(result.get()).isEqualTo(BigDecimal.valueOf(0.5));
	}

	@Test
	public void getPercentageThrowException() throws Exception {
		String percentaje = "0.5";
		Mockito.when(restTemplate.getForObject(urlGetPercentage, String.class)).thenReturn(percentaje);

		when(restTemplate.getForObject(urlGetPercentage, String.class)).thenThrow(new RestClientException("timeout"));

		assertThrows(RuntimeException.class, () -> restTemplate.getForObject(urlGetPercentage, String.class));
		
		var result = service.getPercentage();

		assertThat(result).isNotNull();
	}

}
