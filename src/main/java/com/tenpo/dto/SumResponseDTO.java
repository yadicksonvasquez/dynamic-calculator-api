/**
 * 
 */
package com.tenpo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 */
@Data
@AllArgsConstructor
@Builder
public class SumResponseDTO {

	private BigDecimal result;
	private BigDecimal applyPercent;

}
