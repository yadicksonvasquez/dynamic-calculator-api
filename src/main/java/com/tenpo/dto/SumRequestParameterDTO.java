/**
 * 
 */
package com.tenpo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 */
@Data
@AllArgsConstructor
@Builder
public class SumRequestParameterDTO {

	@NotNull(message = "number1 mandatory")
	private BigDecimal number1;

	@NotNull(message = "number2 mandator")
	private BigDecimal number2;

}
