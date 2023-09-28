package uk.gov.bristol.send.fileupload.bean;

/**
 * <p>
 * Represents the response to a file upload request. This object is typically
 * used during AJAX upload requests, where the client expects some information
 * regarding the attempted upload.
 * </p>
 */
public class FileUploadResponse {

	/** The id. */
	// File specific properties
	private String id;

	/** The file name. */
	private String fileName;

	/** The file size. */
	private String fileSize;

	/** The status. */
	// Upload status properties
	private String status;

	/** The message. */
	private String errorMessage;

	/** The upload document type. */
	private String uploadDocumentType;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the file size.
	 *
	 * @return the file size
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * Sets the file size.
	 *
	 * @param fileSize the new file size
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the upload document type.
	 *
	 * @return the upload document type
	 */
	public String getUploadDocumentType() {
		return uploadDocumentType;
	}

	/**
	 * Sets the upload document type.
	 *
	 * @param uploadDocumentType the new upload document type
	 */
	public void setUploadDocumentType(String uploadDocumentType) {
		this.uploadDocumentType = uploadDocumentType;
	}

}
