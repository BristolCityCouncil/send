package uk.gov.bristol.send.fileupload.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * This class is responsible for formatting file size from raw bytes to a friendly display format.
 * 
 */
@Component
public class ByteCountSizeFormatter {
	
	/** The Constant DISPLAY_UNITS. */
	private static final Map<Integer,String> DISPLAY_UNITS = new HashMap<Integer,String>();
	
	/** The Constant UNIT. */
	private static final int UNIT = 1000;
	
	static {
		DISPLAY_UNITS.put(1, "K");
		DISPLAY_UNITS.put(2, "M");
		DISPLAY_UNITS.put(3, "G");
		DISPLAY_UNITS.put(4, "T");
		DISPLAY_UNITS.put(5, "P");
		DISPLAY_UNITS.put(6, "E");
	}
	
	/**
	 * This method formats the number of bytes into a friendly format.
	 * 
	 * @param bytes
	 *            number of bytes
	 * @return String number of bytes formatted in a friendly format.
	 */
	public String format(long bytes) {
		String friendlyFormat;

		if (bytes < UNIT) {
			friendlyFormat = bytes + " B";
		} else {
			int exp = (int) (Math.log(bytes) / Math.log(UNIT));
			friendlyFormat = String.format("%.1f %sB", bytes / Math.pow(UNIT, exp), DISPLAY_UNITS.get(exp));
		}

		return friendlyFormat;
	}
}
