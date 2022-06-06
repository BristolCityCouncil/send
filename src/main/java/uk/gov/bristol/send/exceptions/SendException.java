package uk.gov.bristol.send.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendException extends RuntimeException {
	private Logger LOGGER = LoggerFactory.getLogger(SendException.class);

	public SendException(String message) {
		super(message);
		LOGGER.error(message);
	}
}
