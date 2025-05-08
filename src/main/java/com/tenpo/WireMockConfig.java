/**
 * 
 */
package com.tenpo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.github.tomakehurst.wiremock.WireMockServer;

import lombok.extern.slf4j.Slf4j;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * 
 */
@Slf4j
@Configuration
@EnableScheduling
public class WireMockConfig {

	private WireMockServer wireMockServer;

	@Bean(destroyMethod = "stop")
	WireMockServer mockService() {
		log.info("[mockService] WireMockServer start");
		wireMockServer = new WireMockServer(options().port(9090));
		wireMockServer.stubFor(get(urlEqualTo("/api/v1/external-service")).willReturn(aResponse().withBody("0.5")));
		wireMockServer.start();
		log.info("[mockService] WireMockServer started, and it will stop in 10 minutes");
		return wireMockServer;
	}

	@EventListener(ContextClosedEvent.class)
	public void stop() {
		if (wireMockServer != null && wireMockServer.isRunning()) {
			wireMockServer.stop();
		}
	}

	@Scheduled(initialDelay = 600000)
	public void scheduledStopWireMockServer() {
		log.info("[scheduledStopWireMockServer] WireMockServer stop");
		wireMockServer.stop();
	}

}
