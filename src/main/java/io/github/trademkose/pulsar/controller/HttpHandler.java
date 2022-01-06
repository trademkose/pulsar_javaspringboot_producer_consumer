package io.github.trademkose.pulsar.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.trademkose.pulsar.bus.PulsarJavaSpringBootStarterApplicationTests;

@RestController
@RequestMapping(path = "/api/1.0")
public class HttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandler.class);	
	@Autowired
	private PulsarJavaSpringBootStarterApplicationTests mycustomservice;
	@RequestMapping(value = "/sendObjectdata/{data}", method = RequestMethod.POST)
	public ResponseEntity<?> httpTelemetryMessageHandlerObject(@PathVariable("data") String data) throws IOException {

		LOGGER.info("[httpTelemetryMessageHandlerObject]: Data Received: {}", data.toString());
		try {
			mycustomservice.testProducerSendMethod(data);
		} catch (Exception e) {
			LOGGER.error("[httpTelemetryMessageHandlerObject]: Exception : {}", e.toString());
			e.printStackTrace();
			return new ResponseEntity<>("{\"Success\": false,\"Message\": \"Exception."+e.toString()+ "\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("{\"Success\": true,\"Message\": \"Message has been sent to Pulsar.\"}", HttpStatus.OK);
	}	
	@RequestMapping(value = "/sendstringdata/{data}", method = RequestMethod.POST)
	public ResponseEntity<?> httpTelemetryMessageHandlerString(@PathVariable("data") String data) throws IOException {

		LOGGER.info("[httpTelemetryMessageHandlerString]: Data Received: {}", data.toString());
		try {
			mycustomservice.stringSerializationTestOk(data);
		} catch (Exception e) {
			LOGGER.error("[httpTelemetryMessageHandlerString]: Exception : {}", e.toString());
			e.printStackTrace();
			return new ResponseEntity<>("{\"Success\": false,\"Message\": \"Exception."+e.toString()+ "\"}", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("{\"Success\": true,\"Message\": \"Message has been sent to Pulsar.\"}", HttpStatus.OK);		
	}
}
