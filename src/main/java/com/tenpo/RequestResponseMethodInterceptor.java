/**
 * 
 */
package com.tenpo;

import java.util.Arrays;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tenpo.calculator.service.IRequestHistoryService;
import com.tenpo.dto.RequestHistoryDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Save request call history
 * 
 * @author YADICKSONVM
 */
@Slf4j
@AllArgsConstructor
@Aspect
@Component
public class RequestResponseMethodInterceptor {

	private final ObjectMapper objectMapper;
	private final IRequestHistoryService requestHistoryService;
	private static final String ERROR = "Error";

	@Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public Object registerHttpRequestResponse(ProceedingJoinPoint joinPoint) {
		Object response = null;
		try {
			log.info("[registerHttpRequestResponse] Intercept method");
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

			HttpServletRequest request = ((ServletRequestAttributes) Objects
					.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
			HttpServletResponse httpResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getResponse();

			log.info("[registerHttpRequestResponse] request URI: {}", request.getRequestURI());

			String requestBody = this.getRequestBody(joinPoint);

			String error = "";
			try {
				response = joinPoint.proceed();
			} catch (Throwable e) {
				error = e.getMessage();
				log.error(ERROR, error);

			}

			String responseBody = convertObjectToJson(response);

			this.requestHistoryService
					.add(RequestHistoryDTO.builder().endpoint(request.getRequestURI()).parameters(requestBody)
							.response(httpResponse != null && response != null ? responseBody : error).build());

		} catch (Exception e) {
			log.error(ERROR, e);
		}
		return response;
	}

	private String getRequestBody(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args.length > 0) {
			try {
				return Arrays.stream(args).map(this::convertObjectToJson).reduce((arg1, arg2) -> arg1 + ", " + arg2)
						.orElse("");
			} catch (Exception e) {
				log.error(ERROR, e);
			}
		}
		return "";
	}

	private String convertObjectToJson(Object object) {
		if (object == null)
			return "";
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error(ERROR, e);
			return "";
		}
	}

}
