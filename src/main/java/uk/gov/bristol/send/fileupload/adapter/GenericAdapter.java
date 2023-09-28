package uk.gov.bristol.send.fileupload.adapter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.service.FileUploadConstants;

/**
 * <p>
 * In order for the common file upload objects to communicate with service specific libraries (which do not have visibility of the common code), each
 * interface to a service must implement an instance of this interface that will convert between a {@link FileUpload} object and an object specific to
 * the interface, which in turn contains the required fields to satisfy the interface contract.
 * </p>
 *
 * @param <T>
 *            The object that the instance of {@link FileUpload} is being converted to.
 */
public abstract class GenericAdapter<T> {
	/**
	 * The logger. Abstract logger - all child classes should use this
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/** The Constant resourceBundle. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages", Locale.ROOT);
	
	/** The Constant ERROR_CODES. */
	private static final Map<UploadStatus, String> ERROR_CODES = new HashMap<UploadStatus, String>();
	
	static {
		ERROR_CODES.put(UploadStatus.NO_FILE_PROVIDED, FileUploadConstants.ERROR_NO_FILE_PROVIDED);
		ERROR_CODES.put(UploadStatus.INVALID_FILE_SIZE, FileUploadConstants.ERROR_FILE_TOO_LARGE);
		ERROR_CODES.put(UploadStatus.INVALID_CONTENT_TYPE, FileUploadConstants.ERROR_INVALID_FILE_TYPE);
		ERROR_CODES.put(UploadStatus.INVALID_FILE_NAME, FileUploadConstants.ERROR_INVALID_FILE_NAME);
		ERROR_CODES.put(UploadStatus.DISK_WRITE_ERROR, FileUploadConstants.ERROR_GENERAL);
		ERROR_CODES.put(UploadStatus.FILE_CONTAINS_VIRUS, FileUploadConstants.ERROR_FILE_CONTAINS_VIRUS);
	}
	
	/** The byte count size formatter. */
	@Autowired
	private ByteCountSizeFormatter byteCountSizeFormatter;

	/**
	 * Instantiates a new generic adapter.
	 *
	 * @param propertiesCache
	 *            the properties cache
	 */
	protected GenericAdapter() {
		
		if (byteCountSizeFormatter == null) {
			byteCountSizeFormatter = new ByteCountSizeFormatter();
		}
		
	}

	/**
	 * Convert file upload.
	 *
	 * @param fileUpload
	 *            the file upload
	 * @param uploadType
	 *            the upload type
	 * @return the t
	 * @throws Exception 
	 */
	public abstract T convertFileUpload(FileUpload fileUpload, String assessmentId) throws Exception;

	/**
	 * Gets the property.
	 *
	 * @param property the property
	 * @return the property
	 */
	protected String getProperty(String property) {		
		Object value = RESOURCE_BUNDLE.getString(property);
		return  (value != null) ? (String) value : null;
	}

	/**
	 * Byte count to display size.
	 *
	 * This used to use a modification of Apache's FileUtils.byteCountToDisplaySize(long size), but that was inexplicably rounding down (i.e. no
	 * decimal places)?!
	 *
	 * New code courtesy of http://stackoverflow.com/a/3758880
	 *
	 * @param bytes
	 *            the bytes
	 * @return String bytes converted to a friend display format.
	 */
	protected String byteCountToDisplaySize(long bytes) {
		return byteCountSizeFormatter.format(bytes);
	}

	/**
	 * Gets the message for upload status.
	 *
	 * @param uploadStatus
	 *            the upload status
	 * @param uploadType
	 *            the upload type
	 * @return the message for upload status
	 */
	protected String getMessageForUploadStatus(UploadStatus uploadStatus) {
		String key = ERROR_CODES.get(uploadStatus);
		
		return key !=null ? getProperty(key) : null;
	}
	
}
