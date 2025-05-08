/**
 * 
 */
package com.tenpo.calculator.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import com.tenpo.dto.RequestHistoryDTO;
import com.tenpo.exception.TenpoBusinessErrorException;
import com.tenpo.model.RequestHistory;
import com.tenpo.model.repository.IRequestHistoryRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Service
public class RequestHistoryServiceImpl implements IRequestHistoryService {

	private static final String ERROR = "Error";
	private final IRequestHistoryRepository repository;

	/**
	 * 
	 */
	public RequestHistoryServiceImpl(IRequestHistoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public CompletableFuture<Boolean> add(RequestHistoryDTO requestHistory) throws Exception {
		try {
			log.info("[add] Register API request");
			RequestHistory entity = RequestHistory.builder().endpoint(requestHistory.getEndpoint())
					.parameters(requestHistory.getParameters()).response(requestHistory.getResponse())
					.created(Instant.now()).build();

			repository.save(entity);

			return CompletableFuture.completedFuture(true);

		} catch (Exception e) {
			log.error(ERROR, e);
			throw new TenpoBusinessErrorException("Request not inserted");
		}
	}

	@Override
	public CompletableFuture<List<RequestHistoryDTO>> findAll() throws Exception {
		try {
			List<RequestHistory> entityList = this.repository.findAll();
			return CompletableFuture.completedFuture(entityList.stream()
					.map(entity -> RequestHistoryDTO.builder().id(entity.getId()).endpoint(entity.getEndpoint())
							.parameters(entity.getParameters()).response(entity.getResponse())
							.created(entity.getCreated()).build())
					.toList());
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new TenpoBusinessErrorException("Business Error to get all request");
		}
	}

}
