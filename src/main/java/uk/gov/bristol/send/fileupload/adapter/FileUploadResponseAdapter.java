package uk.gov.bristol.send.fileupload.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.bean.FileUploadResponse;

/**
 * The Class FileUploadResponseAdapter.
 */
@Service
public class FileUploadResponseAdapter extends GenericAdapter<FileUploadResponse> {

	/**
	 * Instantiates a new file upload response adapter.
	 *
	 * @param propertiesCache            the properties cache
	 * @param configService the config service
	 */
	@Autowired
	public FileUploadResponseAdapter() {
		super();	
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.gov.bristol.evidence.fileupload.adapter.GenericAdapter#convertFileUpload(uk.gov.bristol.evidence.fileupload.bean.FileUpload,
	 * java.lang.String)
	 */
	@Override
	public FileUploadResponse convertFileUpload(FileUpload fileUpload, String assessmentId){

		FileUploadResponse uploadResponse = new FileUploadResponse();

		// Set the general file upload properties
		uploadResponse.setId(fileUpload.getId());
		uploadResponse.setFileName(fileUpload.getFileName());
		uploadResponse.setFileSize(byteCountToDisplaySize(fileUpload.getFileSize()));
		uploadResponse.setUploadDocumentType(fileUpload.getUploadDocumentType());
		
		// Set the upload status, including the (if applicable) error message for the respective status
		UploadStatus uploadStatus = fileUpload.getStatus();
		uploadResponse.setStatus(uploadStatus.toString());
		uploadResponse.setErrorMessage(getMessageForUploadStatus(uploadStatus));		

		return uploadResponse;
	}
}
