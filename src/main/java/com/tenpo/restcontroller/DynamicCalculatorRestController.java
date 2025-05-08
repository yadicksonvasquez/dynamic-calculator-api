/**
 * 
 */
package com.tenpo.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tenpo.calculator.service.IDynamicCalculatorService;
import com.tenpo.dto.ErrorMessageDTO;
import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.dto.SumResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RequestMapping("/api/v1/calculator")
@Slf4j
@RestController
public class DynamicCalculatorRestController {

	private final IDynamicCalculatorService service;

	/**
	 * 
	 */
	public DynamicCalculatorRestController(IDynamicCalculatorService service) {
		this.service = service;
	}

	@Operation(summary = "Applies addition to two numbers", description = "Applies addition to two numbers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request invalid paramters", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }),
			@ApiResponse(responseCode = "503", description = "Business login error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }) })
	@PostMapping(path = "/addNumbers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SumResponseDTO> applySum(@Valid @RequestBody SumRequestParameterDTO numbersParameter)
			throws Exception {
		log.info("[applySum] Applies addition to two numbers");

		return new ResponseEntity<>(service.applySum(numbersParameter).get(), HttpStatus.OK);

	}

}
