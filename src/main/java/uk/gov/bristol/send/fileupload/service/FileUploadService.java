package uk.gov.bristol.send.fileupload.service;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import fi.solita.clamav.ClamAVClient;
import uk.gov.bristol.send.fileupload.ClamConnector;
import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.factories.ClamAVClientFactory;
import uk.gov.bristol.send.fileupload.factories.ClamConnectorFactory;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.service.ConfigService;

/**
 * The Class FileUploadService.
 */
@org.springframework.stereotype.Service("fileUploadService")
public class FileUploadService {

	/** The Constant LOG. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** The config service. */
	private ConfigService configService;

	private SharepointService sharepointService;

		
	private FileUploadValidator fileUploadValidator;

	private ClamAVClient clamAVClient;

	private ClamConnector clamConnector;

	private ClamConnectorFactory clamConnectorFactory;

	private ClamAVClientFactory clamAVClientFactory;

	/**
	 * Instantiates a new file upload service.
	 *
	 * @param configService the config service
	 */
	@Autowired
	public FileUploadService(ConfigService configService, SharepointService sharepointService, FileUploadWriter writer, FileUploadValidator fileUploadValidator, ClamConnectorFactory clamConnectorFactory, ClamAVClientFactory clamAVClientFactory) {
		this.configService = configService;
		this.sharepointService = sharepointService;		
		this.fileUploadValidator = fileUploadValidator;
		this.clamConnectorFactory = clamConnectorFactory;	
		this.clamAVClientFactory = clamAVClientFactory;
	}


	/**
	 * Process file upload.
	 *	
	 * @param multipartFile  the multipart file
	 * @param type           the type
	 * @param subType        the subType
	 * @return the file upload
	 */
	public FileUpload processFileUpload(MultipartFile multipartFile, String subType, Assessment assessment) throws Exception {
		String docType = configService.getSubtypes().get(subType);
		logger.info("Processing file upload of type: " + docType);

		FileUpload fileUpload = new FileUpload();
					
		clamAVClient = clamAVClientFactory.createClamAVClient(configService.getClamAVRemoteURL(), configService.getClamAVRemotePort());
		clamConnector = clamConnectorFactory.createClamConnector(configService.getClamAVRemoteURL(), configService.getClamAVRemotePort(), 4000);
			
		try {
			long checkStart = System.currentTimeMillis();
			//Check virus scan is online, log if it is not responding.
			if(clamConnector.canConnect()){
				long checkDuration = System.currentTimeMillis() - checkStart;
				logger.info("Checking clamAV status took: " + checkDuration + "ms");

				long start = System.currentTimeMillis();
				if(isFileInfected(subType, multipartFile)){
					fileUpload.setStatus(UploadStatus.FILE_CONTAINS_VIRUS);
					fileUpload.setUploadDocumentType(configService.getSubtypes().get(docType));
					return fileUpload;
				}
				long duration = System.currentTimeMillis() - start;
				logger.info("Virus scan for file of type: " + docType + " and size: " + multipartFile.getSize() + " took: " + duration + "ms");
			}else{
				logger.error("Unable to scan file of type " + docType + " for viruses. Caused by ClamAV not responding. Upload will continue");
			}
		} catch (IOException e1) {
			logger.error("Unable to scan file of type " + docType + " for viruses. Caused by IOException. Upload will continue");
		}	

		fileUpload.setFileContentType(multipartFile.getContentType());
		fileUpload.setFileName(multipartFile.getOriginalFilename());
		fileUpload.setFileSize(multipartFile.getSize());
		fileUpload.setUploadDocumentType(docType);
		fileUpload.setStatus(fileUploadValidator.evaluate( fileUpload, configService.getMaxFileSize(), configService.getAllowedFileTypes()));

		if (UploadStatus.SUCCESS.equals(fileUpload.getStatus())) {			
			storeFileSharepoint(multipartFile, fileUpload, assessment);
		}

		return fileUpload;
	}
	
	
	public void deleteUploadedFile( String uploadFolder, String id) throws Exception {
		logger.info("Deleting file upload file with  id " + id);		
		sharepointService.deleteFileFromUPNFolder(uploadFolder, id);
	}

	
	private void storeFileSharepoint(final MultipartFile multipartFile, final FileUpload fileUpload, Assessment assessment) {		
		HashMap<String, String> result;
		sharepointService.getGraphServiceClient();  		 		
		result = sharepointService.addFileToUPNFolder(assessment.getUpn(), multipartFile, fileUpload.getUploadDocumentType(), assessment.getUploadFolderId());		

		if (result.get("id") != null) {
			fileUpload.setStatus(UploadStatus.SUCCESS);
			fileUpload.setId(result.get("id"));		
			fileUpload.setFileName(result.get("name"));
			assessment.setUploadFolderId(result.get("uploadFolder"));						
			logger.info("file stored successfuly in sharepoint folder " + result.get("name"));			
		} else {
			fileUpload.setStatus(UploadStatus.DISK_WRITE_ERROR);
		}
	}

	
	private boolean isFileInfected(String uploadDocumentType, MultipartFile multipartFile) throws IOException {
		logger.info("Scanning upload file type " + uploadDocumentType + " for viruses");

		try {			
			byte[] r = clamAVClient.scan(multipartFile.getInputStream());
			return !ClamAVClient.isCleanReply(r);
		}catch(IOException ioe){
			throw ioe;
		}			
	}

}
