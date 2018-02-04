package com.cromey.gateway;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author paulcromey
 *
 */
@RestController
public class PolicyController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> policy(@RequestParam("id") String id) {

		logger.info("Call policy endpoint");

		if (id.equalsIgnoreCase("paul")) {
			return ResponseEntity.status(200).body("OK");
		} else {
			throw new ValidationException();
		}
		
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "wrong id supplied") // 404
	public class ValidationException extends RuntimeException {
		// ...
	}

}
