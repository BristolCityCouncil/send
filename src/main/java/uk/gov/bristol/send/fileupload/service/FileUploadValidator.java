package uk.gov.bristol.send.fileupload.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;

/**
 * This class is responsible for validating an instance of FileUpload.
 */
@Component
public class FileUploadValidator {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * This method is responsible for evaluating a given fileupload.
	 * 
	 * It will verify the following:
	 * 
	 * <pre>
	 * <ol>
	 * <li>an instance of FileUpload file has been supplied</li>
	 * <li>a file has actually been specified</li>	
	 * <li>the size of the uploaded file</li>
	 * <li>the content type (mime) of the uploaded file</li>
	 * </ol>
	 * </pre>
	 * 
	 * IF all checks have passed the file is treated as valid. If no list of allowed content types is supplied then the file is treated as valid.
	 * 
	 * An IllegalArgumentException will be thrown if the given file upload is null.
	 * 
	 * @param fileUploads
	 *            files already uploaded and verified
	 * @param fileUpload
	 *            file to be verified
	 * @param maximumNumberOfFiles maximum number of files that can be uploaded
	 * @param maximumFileSize maximum size of a file that can be uploaded
	 * @param fileTypesPermitted a collection of permitted file types (mime types)
	 * @return
	 */
	
	public UploadStatus evaluate(final FileUpload fileUpload, long maximumFileSize,
			String[] fileTypesPermitted) {
		UploadStatus status;

		if (fileUpload == null) {
			throw new IllegalArgumentException("A file upload must be supplied");
		} else if (fileDoesNotExist(fileUpload)) {
			status = UploadStatus.NO_FILE_PROVIDED;
		}  else if (fileIsTooLarge(fileUpload, maximumFileSize)) {
			status = UploadStatus.INVALID_FILE_SIZE;
		} else if (contentTypeIsNotValid(fileUpload, fileTypesPermitted)) {
			status = UploadStatus.INVALID_CONTENT_TYPE;
		} else if (fileNameIsInvalid(fileUpload)) {
			status = UploadStatus.INVALID_FILE_NAME;
		} else {
			status = UploadStatus.SUCCESS;
		}

		return status;
	}

	private boolean fileNameIsInvalid(FileUpload fileUpload) {
		String fullFileName = fileUpload.getFileName();
		
		String fileName = StringUtils.substringBeforeLast(fullFileName, ".");
		String regex = "^(?!(COM[1-9]|LPT[1-9]|CON|PRN|AUX|NUL)$)[^:*?\"<>|/\\\\]+$";
		Pattern pattern = Pattern.compile(regex, 
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(fileName);
		return !matcher.matches();
	}

	private boolean fileDoesNotExist(FileUpload fileUpload) {
		logger.debug("Verifying file upload " + fileUpload + " is larger than 0 bytes");

		return fileUpload.getFileSize() <= 0;
	}	

	private boolean fileIsTooLarge(FileUpload fileUpload, long maximumFileSize) {
		logger.debug("Verifying file upload " + fileUpload + " is less than maximum file size " + maximumFileSize);

		return fileUpload.getFileSize() > maximumFileSize;
	}

	private boolean contentTypeIsNotValid(FileUpload fileUpload, String[] fileTypesPermitted) {
		logger.debug("Verifying file upload " + fileUpload + " is an acceptable content type");

		Set<String> allowedFileTypes = new HashSet<String>();
		if (fileTypesPermitted != null) {
			allowedFileTypes.addAll(Arrays.asList(fileTypesPermitted));
		}
		
		boolean invalid = !allowedFileTypes.isEmpty() && !allowedFileTypes.contains(fileUpload.getFileContentType());

		if (invalid) {
			logger.warn("Content type for uploaded file " + fileUpload + " is not acceptable");
		}

		return invalid;
	}
}
