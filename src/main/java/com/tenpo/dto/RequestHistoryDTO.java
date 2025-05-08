/**
 * 
 */
package com.tenpo.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestHistoryDTO {

	private Long id;

	@NotNull
	private String endpoint;

	@NotNull
	private String parameters;

	@NotNull
	private String response;

	private Instant created;

}
