/**
 * 
 */
package com.tenpo.restcontroller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tenpo.calculator.service.IRequestHistoryService;
import com.tenpo.dto.ErrorMessageDTO;
import com.tenpo.dto.RequestHistoryDTO;
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
@RequestMapping("/api/v1/calculator-history")
@Slf4j
@RestController
public class RequestHistoryRestController {

	private final IRequestHistoryService service;

	/**
	 * 
	 */
	public RequestHistoryRestController(IRequestHistoryService service) {
		this.service = service;
	}

	@Operation(summary = "Get the history of calls to the calculator API", description = "Get the history of calls to the calculator API")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
			@ApiResponse(responseCode = "503", description = "Business login error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }) })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompletableFuture<List<RequestHistoryDTO>>> findAll() throws Exception {
		log.info("[findAll] Find all");
		return new ResponseEntity<>(this.service.findAll(), HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Add history of calls to the calculator API", description = "Add history of calls to the calculator API")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request invalid paramters", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }),
			@ApiResponse(responseCode = "503", description = "Business login error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }) })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompletableFuture<Boolean>> addHistory(@Valid @RequestBody RequestHistoryDTO history)
			throws Exception {
		log.info("[addHistory] Add history of calls to the calculator API");

		return new ResponseEntity<>(service.add(history), HttpStatus.OK);

	}

}
