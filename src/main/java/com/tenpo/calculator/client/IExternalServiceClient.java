/**
 * 
 */
package com.tenpo.calculator.client;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Client to consume external service API
 * 
 * @author YADICKSONVM
 */
public interface IExternalServiceClient {
	Optional<BigDecimal> getPercentage() throws Exception;
}
