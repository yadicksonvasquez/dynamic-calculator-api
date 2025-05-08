/**
 * 
 */
package com.tenpo.calculator.client;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;

import lombok.extern.slf4j.Slf4j;

/**
 * Client to consume external service API
 * 
 * @author YADICKSONVM
 */
@Slf4j
@Component
public class IExternalServiceClientImpl implements IExternalServiceClient {

	protected final RestTemplate restTemplate;

	@Value("${endpoints.getPercentage}")
	private String urlGetPercentage;

	public IExternalServiceClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Cacheable(value = "percentageCache")
	@Override
	public Optional<BigDecimal> getPercentage() throws Exception {
		try {
			log.info("[getPercentage] Getting Percentage from external service");
			String percentaje = restTemplate.getForObject(urlGetPercentage, String.class);
			return Optional.of(new BigDecimal(percentaje));

		} catch (Exception e) {
			log.error("[getPercentage] Error", e);
			log.error("[getPercentage] Use cache if exists");
		}
		return Optional.empty();
	}

}
