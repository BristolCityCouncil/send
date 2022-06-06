package uk.gov.bristol.send.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendAuthServiceException extends RuntimeException {
	private Logger LOGGER = LoggerFactory.getLogger(SendAuthServiceException.class);

	public SendAuthServiceException(String message) {
		super(message);
		LOGGER.error(message);
	}
}
