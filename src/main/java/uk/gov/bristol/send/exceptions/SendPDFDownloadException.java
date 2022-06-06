package uk.gov.bristol.send.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendPDFDownloadException extends RuntimeException {
	private Logger LOGGER = LoggerFactory.getLogger(SendPDFDownloadException.class);

	public SendPDFDownloadException(String message) {
		super(message);
		LOGGER.error(message);
	}
}
