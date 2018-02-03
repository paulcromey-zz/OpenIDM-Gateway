package com.cromey.gateway;

import java.net.URI;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Paul Cromey
 */
@RestController
@SpringBootApplication
public class Application {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${remote.home}")
	private URI home;

	@GetMapping(path = "/users")
	public @ResponseBody ResponseEntity<Object> proxy(ProxyExchange<Object> proxy) throws Exception {
		return proxy.uri(home + "/openidm/managed/user/?_queryId=query-all-ids")
				.header("X-OpenIDM-Username", "openidm-admin").header("X-OpenIDM-Password", "openidm-admin")
				.get(response());
	}

	private Function<ResponseEntity<Object>, ResponseEntity<Object>> response() {

		logger.info("This is an info message");
		
		return response -> ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders())
				.body(response.getBody());

	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
