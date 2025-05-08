package com.tenpo.calculator.service;

import java.util.Optional;

import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.dto.SumResponseDTO;

/**
 * Calculator service
 */
public interface IDynamicCalculatorService {

	/**
	 * Sum two numbers and apply a percent
	 * @param parameter
	 * @return SumResponseDTO
	 */
	Optional<SumResponseDTO> applySum(SumRequestParameterDTO parameter) throws Exception;

}
