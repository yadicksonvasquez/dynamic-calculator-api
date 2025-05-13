/**
 * 
 */
package com.tenpo.calculator.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tenpo.calculator.client.IExternalServiceClient;
import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.dto.SumResponseDTO;
import com.tenpo.exception.TenpoBadRequestException;
import com.tenpo.exception.TenpoBusinessErrorException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Service
public class DynamicCalculatorServiceImpl implements IDynamicCalculatorService {

	private static final String ERROR = "Error";
	private final IExternalServiceClient externalServiceClient;

	/**
	 * 
	 */
	public DynamicCalculatorServiceImpl(IExternalServiceClient externalServiceClient) {
		this.externalServiceClient = externalServiceClient;
	}

	@Override
	public Optional<SumResponseDTO> applySum(SumRequestParameterDTO parameter) throws Exception {
		try {
			log.info("[applySum] parameter: {}", parameter);

			if (parameter == null) {
				throw new TenpoBadRequestException("parameter is mandatory");
			}

			if (parameter.getNumber1() == null || parameter.getNumber2() == null) {
				throw new TenpoBadRequestException("Numbers are mandatory");
			}

			Optional<BigDecimal> percentaje = this.externalServiceClient.getPercentage();
			if (percentaje.isPresent()) {
				return Optional.of(SumResponseDTO.builder()
						.result(parameter.getNumber1().add(parameter.getNumber2()).multiply(percentaje.get()))
						.applyPercent(percentaje.get()).build());
			}
			throw new TenpoBusinessErrorException(
					"Percentage of calculation not available in external service and cache");
		} catch (Exception e) {
			log.error(ERROR, e);
			throw e;
		}
	}

}
