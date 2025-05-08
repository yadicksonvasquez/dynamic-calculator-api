/**
 * 
 */
package com.tenpo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * OpenAPI configuration
 * 
 * @author YADICKSONVM
 */
@Configuration
public class OpenAPISwaggerConfiguration {

	private final SwaggerProperties swaggerProperties;

	/**
	 * 
	 */
	public OpenAPISwaggerConfiguration(SwaggerProperties swaggerProperties) {
		this.swaggerProperties = swaggerProperties;
	}

	@Bean
	OpenAPI customOpenAPIInfo() {
		return new OpenAPI().info(new Info().title(swaggerProperties.getApplicationTitle())
				.description(swaggerProperties.getApplicationDescription())
				.version(swaggerProperties.getApplicationVersion()).contact(new Contact()
						.email(swaggerProperties.getContactEmail()).name(swaggerProperties.getContactName())));
	}

}
