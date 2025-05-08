/**
 * 
 */
package com.tenpo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 
 */
@Data
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {

	private String applicationDescription;
	private String applicationVersion;
	private String applicationTitle;
	private String contactName;
	private String contactEmail;
}