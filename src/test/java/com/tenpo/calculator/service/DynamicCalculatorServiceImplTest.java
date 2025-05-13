/**
 * 
 */
package com.tenpo.calculator.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tenpo.calculator.client.IExternalServiceClient;
import com.tenpo.dto.SumRequestParameterDTO;
import com.tenpo.exception.TenpoBadRequestException;
import com.tenpo.exception.TenpoBusinessErrorException;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
public class DynamicCalculatorServiceImplTest {

	@Mock
	private IExternalServiceClient externalServiceClient;
	private IDynamicCalculatorService service;

	@BeforeEach
	public void setUp() {
		this.service = new DynamicCalculatorServiceImpl(externalServiceClient);
	}

	@Test
	void applySumTest() throws Exception {

		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		Mockito.when(externalServiceClient.getPercentage()).thenReturn(Optional.of(BigDecimal.valueOf(0.5)));

		var result = service.applySum(request);

		assertThat(result).isNotNull();
		assertThat(result.get().getResult()).isEqualTo(BigDecimal.valueOf(30.0));
	}

	@Test
	void applySumParametersNullTest() throws Exception {

		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(null).number2(BigDecimal.valueOf(40))
				.build();

		assertThatThrownBy(() -> service.applySum(request)).isInstanceOf(TenpoBadRequestException.class)
				.hasMessage("Numbers are mandatory");

	}

	@Test
	void applySumParametersNull2Test() throws Exception {

		assertThatThrownBy(() -> service.applySum(null)).isInstanceOf(TenpoBadRequestException.class)
				.hasMessage("parameter is mandatory");

	}

	@Test
	void applySumParametersNull3Test() throws Exception {

		SumRequestParameterDTO request = SumRequestParameterDTO.builder().number1(BigDecimal.valueOf(20))
				.number2(BigDecimal.valueOf(40)).build();

		Mockito.when(externalServiceClient.getPercentage()).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.applySum(request)).isInstanceOf(TenpoBusinessErrorException.class)
				.hasMessage("Percentage of calculation not available in external service and cache");

	}

}
