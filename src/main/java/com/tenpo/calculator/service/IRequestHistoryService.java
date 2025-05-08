/**
 * 
 */
package com.tenpo.calculator.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.tenpo.dto.RequestHistoryDTO;

/**
 * Request history service
 * 
 * @author YADICKSONVM
 */
public interface IRequestHistoryService {

	/**
	 * Add a request
	 * 
	 * @param requestHistory
	 * @return
	 * @throws Exception
	 */
	CompletableFuture<Boolean> add(RequestHistoryDTO requestHistory) throws Exception;

	/**
	 * Get all request history
	 * 
	 * @return CompletableFuture<List<RequestHistoryDTO>>
	 * @throws Exception
	 */
	CompletableFuture<List<RequestHistoryDTO>> findAll() throws Exception;

}
