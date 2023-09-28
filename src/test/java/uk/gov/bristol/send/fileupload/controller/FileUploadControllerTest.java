package uk.gov.bristol.send.fileupload.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import uk.gov.bristol.send.exceptions.SendException;
import uk.gov.bristol.send.fileupload.adapter.FileUploadResponseAdapter;
import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUploadResponse;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.fileupload.service.FileUploadService;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Owner;
import uk.gov.bristol.send.service.AssessmentService;
import uk.gov.bristol.send.service.AuthenticationService;
import uk.gov.bristol.send.service.ConfigService;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileUploadControllerTest {

	private FileUploadController fileUploadController;

	private BindingAwareModelMap bindingAwareModelMap;

	private final String OWNER_EMAIL = "mauriceminor@leyland.co.uk";

	// File encoding properties
	private static final String FILE_ENCODING = "UTF-8";

	// Valid file

	private static final String TEST_FILE_NAME = "test1Input";
	private static final String TEST_FILE_FILENAME = "upload_test_1.txt";
	private static final int TEST_FILE_LENGTH = 256000; // 256kB

	private static final String TEST_FILE_CONTENT_TYPE = "text/plain";
	private final String VALID_ASSESSMENT_ID = "5942d909-36ca-4d63-92e1-292102a1740c";

	@MockBean
	AssessmentService assessmentService;

	@MockBean
	AuthenticationService authenticationService;

	@MockBean
	ConfigService configService;

	@MockBean
	FileUploadService fileUploadService;

	@Mock
	HashMap<String, String> map;

	@Mock
	Assessment assessment;

	private UploadTestDataInitializer testDataInitializer;

	@Mock
	Owner owner;

	@Mock
	List<Assessment> listAssessments;

	@Mock
	HttpServletRequest mockHttpServletRequest;

	@Mock
	private Logger log;

	@Spy
	BindingAwareModelMap spyBindingAwareModelMap;
	
	@MockBean
	private FileUploadResponseAdapter responseAdapter;
	
	@Mock
	List<UploadedFileInfo> uploadedFiles;


	@BeforeAll
	public void setUp() {
		fileUploadController = new FileUploadController(authenticationService, assessmentService, configService,
				fileUploadService, responseAdapter);
		bindingAwareModelMap = new BindingAwareModelMap();
		spyBindingAwareModelMap = Mockito.spy(bindingAwareModelMap);
		fileUploadController.setLogger(log);
		testDataInitializer = new UploadTestDataInitializer();
	}

	@AfterAll
	public void tearDown() throws Exception {
		fileUploadController = null;
		bindingAwareModelMap = null;
		spyBindingAwareModelMap = null;
	}

	@Test
	public void whenUpload_validFile_UploadSuccess() throws Exception {

		setUpLogAndServices();

		// Create the sample byte content to upload
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_FILE_LENGTH).getBytes(FILE_ENCODING);

		// Create a mock multipart file, that returns our test when called
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getBytes()).thenReturn(fileContent);
		when(multipartFile.getContentType()).thenReturn(TEST_FILE_CONTENT_TYPE);
		when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));
		when(multipartFile.getName()).thenReturn(TEST_FILE_NAME);
		when(multipartFile.getOriginalFilename()).thenReturn(TEST_FILE_FILENAME);
		when(multipartFile.getSize()).thenReturn((long) TEST_FILE_LENGTH);
		MockHttpServletResponse response = new MockHttpServletResponse();
		// Upload the file

		assertNotNull(response);

		FileUpload fileUpload = testDataInitializer.initFileUpload();
		fileUpload.setFileContentType(TEST_FILE_CONTENT_TYPE);
		fileUpload.setFileName(TEST_FILE_FILENAME);
		fileUpload.setFileSize(TEST_FILE_LENGTH);
		fileUpload.setId("1234");
		when(fileUploadService.processFileUpload(any(),any(), any())).thenReturn(fileUpload);

		FileUploadResponse fileUploadResponseMock = testDataInitializer.initFileUploadSuccessResponse();
		 
		when(responseAdapter.convertFileUpload(fileUpload,VALID_ASSESSMENT_ID)).thenReturn(fileUploadResponseMock);
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("assessmentId", VALID_ASSESSMENT_ID);
		
		ResponseEntity<?> fileUploadResponse = fileUploadController.upload(mockHttpServletRequest, spyBindingAwareModelMap,
				 multipartFile, data);

		assertSame(fileUploadResponse.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
    public void whenUpload_invalidSubType_UploadFailure() throws Exception{
		setUpLogAndServices();
        fileUploadController.setLogger(log);    
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(fileUploadService.processFileUpload(any(), any(), any())).thenThrow(new Exception()); 
      
       
		FileUploadResponse fileUploadResponseMock = testDataInitializer.initFileUploadFailureResponse();
		when(responseAdapter.convertFileUpload(any(),any())).thenReturn(fileUploadResponseMock);
		
		ResponseEntity<?> fileUploadResponse = fileUploadController.upload(mockHttpServletRequest, spyBindingAwareModelMap,
				 multipartFile, new HashMap<String, String>());
        
        assertSame(fileUploadResponse.getStatusCode(), HttpStatus.BAD_REQUEST);       
		       
    }
	
	@Test
	public void whenDeleteFile_deletedFile_DeeletedFromAssessment() throws Exception {
		setUpLogAndServices();
		fileUploadController.setLogger(log);
		UploadedFileInfo uploadedFileInfo = testDataInitializer.initUploadedFileInfo("1234", "Application form (mandatory)", "Application form (mandatory).txt");

		List<UploadedFileInfo> uploadedFiles = new ArrayList<UploadedFileInfo>();
		uploadedFiles.add(uploadedFileInfo);		

		when(assessment.getUploadedFilesInfo()).thenReturn(uploadedFiles);

		fileUploadController.deleteFile(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, "1234");

		verify(assessmentService, times(1)).deleteUploadedFileInfo(any(), any(), any());

	}    
	
	@Test
	public void whenDeleteFile_cannotGetAssessment_ThrowsException() throws Exception {
		setUpLogAndServices();
		fileUploadController.setLogger(log);
		when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenThrow(new Exception());

		assertThrows(SendException.class, () -> {
			fileUploadController.deleteFile(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID,
					any());

		});

	}    

	public void setUpLogAndServices() throws Exception {	
		fileUploadController.setLogger(log);
		spyBindingAwareModelMap.clear();
		when(assessment.getOwner()).thenReturn(OWNER_EMAIL);

		when(authenticationService.getLoggedInUser(mockHttpServletRequest)).thenReturn(owner);
		when(owner.getOwnerEmail()).thenReturn(OWNER_EMAIL);
		when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenReturn(assessment);
		when(assessmentService.getAssessmentsByOwner(OWNER_EMAIL)).thenReturn(listAssessments);
		when(assessment.getUploadedFilesInfo()).thenReturn(uploadedFiles);

	}

}
