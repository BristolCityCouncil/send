package uk.gov.bristol.send.fileupload.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import uk.gov.bristol.send.fileupload.bean.FileUpload;

/**
 * This class is responsible for writing a file upload to disk storage.
 * 
 */
@Component
public class FileUploadWriter {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * This method is responsible for writing the content in given multipart file to
	 * a target location.
	 * 
	 * @param multipartFile file content to be written to target location
	 * @param fileUpload
	 * @return File instance of file on disk
	 */
	File writeFileToDisk(final MultipartFile uploadedFile, final String targetLocation, FileUpload fileUpload) {

		logger.info("Got directory for uploaded files: " + targetLocation);

		String originalFileName = uploadedFile.getOriginalFilename();
		String fileExtension = StringUtils.substringAfterLast(originalFileName, ".");
		
		String friendlyFileName = fileUpload.getUploadDocumentType() + "." + fileExtension ;
		friendlyFileName = StringUtils.replace(friendlyFileName, "/", "-");
		File targetFile = new File(targetLocation + friendlyFileName);
		File directoryFile = new File(targetLocation);

		if (folderExists(directoryFile)) {
			try {
				logger.info("Writing contents of multipart file " + uploadedFile + " to temporary file " + targetFile);
				uploadedFile.transferTo(targetFile);
			} catch (IOException e) {
				logger.error("IOException while transfer to uploaded files: " + targetLocation + e.getLocalizedMessage());
			}
		}

		return targetFile;
	}

	private boolean folderExists(final File directory) {
		boolean exists = directory.exists();

		if (!exists) {
			exists = directory.mkdirs();

			if (!exists) {
				logger.error("Unable to create directory for  uploaded files: " + directory);
			}

		}

		return exists;
	}
}
