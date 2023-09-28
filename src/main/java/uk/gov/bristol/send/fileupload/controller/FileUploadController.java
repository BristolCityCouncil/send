package uk.gov.bristol.send.fileupload.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import uk.gov.bristol.send.exceptions.SendAuthServiceException;
import uk.gov.bristol.send.exceptions.SendException;
import uk.gov.bristol.send.fileupload.adapter.FileUploadResponseAdapter;
import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.bean.FileUploadResponse;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.fileupload.service.FileUploadService;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Owner;
import uk.gov.bristol.send.service.AssessmentService;
import uk.gov.bristol.send.service.AuthenticationService;
import uk.gov.bristol.send.service.ConfigService;


/**
 * This class is the Controller for uploading files and interacting with
 * uploaded files.
 * 
 * In this component it is called by the javascript code
 * 
 * The Class FileUploadController.
 */
@Controller
public class FileUploadController {
	private Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
	public static final String pathPrefix = "/top-up-assessment";
	
    @Autowired
    AssessmentService assessmentService;
    
    @Autowired
    AuthenticationService authenticationService;
    
	@Autowired
	ConfigService configService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	private FileUploadResponseAdapter responseAdapter;


	public FileUploadController(AuthenticationService authenticationService, AssessmentService assessmentService, ConfigService configService, FileUploadService fileUploadService, FileUploadResponseAdapter responseAdapter) {
		super();
		this.authenticationService = authenticationService;
		this.assessmentService = assessmentService;
		this.configService = configService;
		this.fileUploadService = fileUploadService;
		this.responseAdapter = responseAdapter;
	}
	
	@PostMapping(value = { pathPrefix + "/uploadSupportDocument" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE} )
	public ResponseEntity<?> upload(HttpServletRequest request, Model model, @RequestPart MultipartFile file, @RequestParam HashMap<String, String> data) {
		
		//Do an owner check before preceding
    	Owner owner = getLoggedInUser(request);	
    	String assessmentId = data.get("assessmentId");
    	String supportDocType = data.get("supportDocType");
    	String documentTypeName = configService.getSubtypes().get(supportDocType);	

		try {
			
			Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
			
			// Process the uploaded file and return the processed response to the caller						
			FileUpload fileUpload = fileUploadService.processFileUpload(file, supportDocType, assessment);
			FileUploadResponse fileUploadResponse = responseAdapter.convertFileUpload(fileUpload, assessmentId);
						
			LOGGER.info("Started Uploading multipart file of File Type " + documentTypeName);

			
			// If UploadStatus is SUCCESS, Save uploaded file info against the assessment
			if (fileUpload.getStatus().equals(UploadStatus.SUCCESS)) {
			
				LOGGER.info("Successsfully Uploaded multipart file of File Type " + documentTypeName + " and size " + fileUploadResponse.getFileSize());
				assessmentService.saveUploadedFileInfo(assessment, fileUploadResponse, fileUpload);
				return ResponseEntity.ok(fileUploadResponse);				
				
			} else {
				LOGGER.info("Failed to  Uploaded multipart file of File Type " + documentTypeName + " and size " + fileUploadResponse.getFileSize());					
			    ResponseEntity<FileUploadResponse> responseEntity = new ResponseEntity<FileUploadResponse>(fileUploadResponse, HttpStatus.BAD_REQUEST);
			    return responseEntity;

			}						
			
		} catch (Exception e) {
			LOGGER.error("Exception Uploading multipart file" + e.getMessage());
			FileUpload fileUpload = new FileUpload();
			fileUpload.setId(UUID.randomUUID().toString());
			fileUpload.setStatus(UploadStatus.DISK_WRITE_ERROR);
			fileUpload.setUploadDocumentType(documentTypeName);
			
			return new ResponseEntity<>(responseAdapter.convertFileUpload(fileUpload, assessmentId), HttpStatus.BAD_REQUEST);
		}
		
	}
	
    
    @RequestMapping(value = { pathPrefix + "/deleteFile" }, params = { "assessmentId", "fileId"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK) 
    public void deleteFile(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId, @RequestParam("fileId") String fileId) {           
    	Owner owner = getLoggedInUser(request);
        try{
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());           
           
            List<UploadedFileInfo> uploadedFiles= assessment.getUploadedFilesInfo();
            fileUploadService.deleteUploadedFile(assessment.getUploadFolderId(),  fileId);         		
            
    		uploadedFiles = assessmentService.deleteUploadedFileInfo( assessment, uploadedFiles,  fileId);    		
    		    		
        }catch(Exception e){
            throw new SendException("Unable to delete file in database: " + e.getMessage() );
        }
                 
    }
	
    
	private Owner getLoggedInUser(HttpServletRequest request) {
		Owner owner;
		try {
			  owner = authenticationService.getLoggedInUser(request);
		} catch (Exception e) {		  
		    throw new SendAuthServiceException("Could not Authenticate User: " + e.getMessage() );
		}
		return owner;
	}
	
    protected void setLogger(Logger logger){
        LOGGER = logger;
    }
    

}
