package uk.gov.bristol.send.fileupload.model;

public class UploadedFileInfo {

	/** The id. */
	// File specific properties
	private String id;

	/** The file name. */
	private String fileName;

	/** The file size. */
	private String fileSize;

	/** The upload document type. */
	private String documentTypeName;

	private String fileContentType;

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

}
