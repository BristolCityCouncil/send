package uk.gov.bristol.send.fileupload.controller;

import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.fileupload.bean.FileUploadResponse;

public class UploadTestDataInitializer {

	public FileUpload initFileUpload() {
		FileUpload fileUpload = new FileUpload();
		fileUpload.setStatus(UploadStatus.SUCCESS);
		return fileUpload;
	}

	public FileUploadResponse initFileUploadSuccessResponse() {

		FileUploadResponse fileUploadResponse = new FileUploadResponse();
		fileUploadResponse.setStatus(UploadStatus.SUCCESS.toString());

		return fileUploadResponse;
	}

	public FileUploadResponse initFileUploadFailureResponse() {

		FileUploadResponse fileUploadResponse = new FileUploadResponse();
		fileUploadResponse.setStatus(UploadStatus.DISK_WRITE_ERROR.toString());

		return fileUploadResponse;
	}

	public UploadedFileInfo initUploadedFileInfo(String id, String docType, String fileName) {

		UploadedFileInfo uploadedFileInfo = new UploadedFileInfo();
		uploadedFileInfo.setId(id);
		uploadedFileInfo.setDocumentTypeName(docType);
		uploadedFileInfo.setFileName(fileName);

		return uploadedFileInfo;
	}

}
