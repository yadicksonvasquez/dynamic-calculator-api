/**
 * 
 */
package com.tenpo.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.tenpo.dto.ErrorMessageDTO;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception Handler
 * 
 * @author YADICKSONVM
 */
@Slf4j
@RestControllerAdvice
public class TenpoExceptionHandler {

	@ExceptionHandler(value = { TenpoBusinessErrorException.class })
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public ErrorMessageDTO handleBusinessErrorException(TenpoBusinessErrorException ex, WebRequest request) {
		log.info("[handleBusinessErrorException] Handling exception");
		return ErrorMessageDTO.builder().message(ex.getMessage()).build();
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessageDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request) {
		log.info("[handleMethodArgumentNotValidException] Handling exception");
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.toList();

		return ErrorMessageDTO.builder().message("Arguments Not valid, errors: " + errors).build();
	}

	@ExceptionHandler(value = { TenpoBadRequestException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessageDTO handleBadRequestException(TenpoBadRequestException ex, WebRequest request) {
		log.info("[handleBadRequestException] Handling exception");
		return ErrorMessageDTO.builder().message(ex.getMessage()).build();
	}

}
