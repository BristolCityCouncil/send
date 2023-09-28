package uk.gov.bristol.send.fileupload.bean;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a single uploaded file. All uploaded files will have an {@link UploadStatus}, representing the status of the uploaded file
 * (i.e. whether it was successful, or failed for some reason).

 */
@SuppressWarnings("serial")
public class FileUpload implements Serializable {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FileUpload.class);

	/**
	 * The Enum UploadStatus.
	 */
	public enum UploadStatus {

		/** The success. */
		SUCCESS,
		/** The no file provided. */
		NO_FILE_PROVIDED,
		/** The invalid file size. */
		INVALID_FILE_SIZE,
		/** The invalid content type. */
		INVALID_CONTENT_TYPE,
		/** The invalid file name */
		INVALID_FILE_NAME,
		/** File contains a virus */
		FILE_CONTAINS_VIRUS,
		/** The disk write error. */
		DISK_WRITE_ERROR
	}
  
	/** The id. */
	private String id;

	/** The file name. */
	private String fileName;

	/** The file content type. */
	private String fileContentType;

	/** The file size. */
	private long fileSize;

	/** The status. */
	private UploadStatus status;

	/** The file. */
	private File file;

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
	 * @param id
	 *            the new id
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
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the file content type.
	 *
	 * @return the file content type
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * Sets the file content type.
	 *
	 * @param fileContentType
	 *            the new file content type
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * Gets the file size.
	 *
	 * @return the file size
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * Sets the file size.
	 *
	 * @param fileSize
	 *            the new file size
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public UploadStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(UploadStatus status) {
		this.status = status;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Checks if is file readable.
	 *
	 * @return true, if is file readable
	 */
	public boolean isFileReadable() {

		boolean readable = isFileReadableInternal();
		LOG.debug(String.format("Is file [%s] readable: [%s]", file, readable));
		return readable;
	}

	/**
	 * Checks if is file readable internal.
	 *
	 * @return true, if is file readable internal
	 */
	private boolean isFileReadableInternal() {

		if (file != null) {
			return Files.isReadable(file.toPath());
		}

		return false;
	}

	/**
	 * Checks if is file writable.
	 *
	 * @return true, if is file writable
	 */
	public boolean isFileWritable() {

		boolean writable = isFileWritableInternal();
		LOG.debug(String.format("Is file [%s] writable: [%s]", file, writable));
		return writable;
	}

	/**
	 * Checks if is file writable internal.
	 *
	 * @return true, if is file writable internal
	 */
	private boolean isFileWritableInternal() {

		if (file != null) {
			return Files.isWritable(file.toPath());
		}

		return false;
	}

    public String getUploadDocumentType() {
        return uploadDocumentType;
    }


    public void setUploadDocumentType(String uploadDocumentType) {
        this.uploadDocumentType = uploadDocumentType;
    }
	
	
	@Override
	public String toString() {
		return "FileUpload [id=" + id + ", fileName=" + fileName + ", fileContentType=" + fileContentType 
				+ ", fileSize=" + fileSize + ", uploadDocumentType=" + uploadDocumentType + ", status="
				+ status + ", file=" + file + "]";
	}
}
