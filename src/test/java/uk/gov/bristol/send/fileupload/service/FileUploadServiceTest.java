package uk.gov.bristol.send.fileupload.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.common.collect.Maps;

import fi.solita.clamav.ClamAVClient;
import uk.gov.bristol.send.fileupload.ClamConnector;
import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.controller.UploadTestDataInitializer;
import uk.gov.bristol.send.fileupload.factories.ClamAVClientFactory;
import uk.gov.bristol.send.fileupload.factories.ClamConnectorFactory;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.service.ConfigService;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileUploadServiceTest {

	// File upload properties
	private static final String OUTPUT_DIR = System.getProperty("java.io.tmpdir");
	private static final long MAX_FILE_SIZE = 1000000; // 1MB
	public static final String CONTENT_TYPE_PLAIN_TEXT = "text/plain";
	/** The Constant CONTENT_TYPE_PNG. */
	public static final String CONTENT_TYPE_PNG = "image/png";

	// File encoding properties
	private static final String FILE_ENCODING = "UTF-8";

	// Valid file type, valid extension, valid size
	public static final String TEST_1_NAME = "test1Input";
	public static final String TEST_1_FILENAME = "upload_test_1.txt";
	public static final int TEST_1_LENGTH = 256000; // 256kB
	public static final String TEST_1_CONTENT_TYPE = CONTENT_TYPE_PLAIN_TEXT;

	// Valid file type, valid extension, valid size
	public static final String TEST_2_NAME = "test2Input";
	public static final String TEST_2_FILENAME = "upload_test_2.txt";
	public static final int TEST_2_LENGTH = 1000000; // 1MB
	public static final String TEST_2_CONTENT_TYPE = CONTENT_TYPE_PLAIN_TEXT;

	// Valid file type, valid extension, invalid size
	public static final String TEST_3_NAME = "test3Input";
	public static final String TEST_3_FILENAME = "upload_test_3.txt";
	public static final int TEST_3_LENGTH = 1000001; // 1 byte over 1Mb
	public static final String TEST_3_CONTENT_TYPE = CONTENT_TYPE_PLAIN_TEXT;

	// Invalid file type, invalid extension, valid size
	public static final String TEST_4_NAME = "test4Input";
	public static final String TEST_4_FILENAME = "upload_test_4.png";
	public static final int TEST_4_LENGTH = 512000; // 512kB
	public static final String TEST_4_CONTENT_TYPE = CONTENT_TYPE_PNG;

	// Invalid file type, valid extension, valid size
	public static final String TEST_5_NAME = "test5Input";
	public static final String TEST_5_FILENAME = "upload_test_5.txt";
	public static final int TEST_5_LENGTH = 512000; // 512kB
	public static final String TEST_5_CONTENT_TYPE = CONTENT_TYPE_PNG;

	// UUID regex -
	// http://stackoverflow.com/questions/7905929/how-to-test-valid-uuid-guid#answer-13653180
	private static final String REGEX_UUID = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

	// See MimeTypesTest.getContentType(File file)
	protected static String methodUnderTest;

	@MockBean
	ConfigService configService;

	@MockBean
	SharepointService mockSharepointService;

	private FileUploadService fileUploadService;
	
	private UploadTestDataInitializer testDataInitializer;

	MockMultipartFile mockMultipartFile;

	Map<String, String> subtypes;

	@Mock
	ClamAVClient mockClamAVClient;

	@Mock
	ClamConnector mockClamConnector;

	@Mock
	ClamConnectorFactory mockClamConnectorFactory;

	@Mock
	ClamAVClientFactory mockClamAVClientFactory;

	@Mock
	Assessment mockAssessment;

	// This is the success value
	private byte[] validBytes = new byte[]{115,116,114,101,97,109,58,32,79,75,0};

	// This is the infected file value
	private byte[] invalidBytes = new byte[]{115,116,114,101,97,109,58,32,87,105,110,46,84,101,115,116,46,69,73,67,65,82,95,72,68,66,45,49,32,70,79,85,78,68,0};


	@BeforeAll
	public void setUp() throws Exception {
		fileUploadService = new FileUploadService(configService, mockSharepointService, new FileUploadWriter(), new FileUploadValidator(), mockClamConnectorFactory, mockClamAVClientFactory);
		testDataInitializer = new UploadTestDataInitializer();
		when(mockClamAVClientFactory.createClamAVClient(anyString(), anyInt())).thenReturn(mockClamAVClient);
		when(mockClamConnectorFactory.createClamConnector(anyString(), anyInt(), anyInt())).thenReturn(mockClamConnector);
		when(mockAssessment.getSchoolName()).thenReturn("school name");
		when(mockAssessment.getUploadFolderId()).thenReturn("uploadFolderName");	
		when(mockAssessment.getUpn()).thenReturn("101");							
	}

	@BeforeEach
	public void setVirusScan() throws Exception {
		when(configService.getClamAVRemoteURL()).thenReturn("localhost");
		when(configService.getClamAVRemotePort()).thenReturn(3310);
		when(mockClamConnector.canConnect()).thenReturn(true);
		when(mockClamAVClient.scan(any(InputStream.class))).thenReturn(validBytes);	
	}

	@AfterAll
	public void tearDown() throws Exception {
		fileUploadService = null;
	}


//	@Test
	public void whenProcessFileUpload_validFile_UploadSuccess () throws Exception {
		// Create the sample content
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_1_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_1_LENGTH, fileContent.length);

		// Create the multipart file objects representing our test data
		MockMultipartFile multipartFile = createMultipartFile(TEST_1_NAME, TEST_1_FILENAME, TEST_1_CONTENT_TYPE, fileContent);
		when(configService.getMaxFileSize()).thenReturn(MAX_FILE_SIZE);
		when(configService.getFileLocaton()).thenReturn(OUTPUT_DIR + System.getProperty("file.separator"));
		Map<String, String> subtypes = Maps.newLinkedHashMap();
		subtypes.put("100", "Support plan (mandatory)");
		subtypes.put("101", "Whole school provision map (mandatory)");
		subtypes.put("102", "Application form (mandatory)");
		when(configService.getSubtypes()).thenReturn(subtypes);
			
		// Process the uploaded multipart file object
		FileUpload originalFileUpload = fileUploadService.processFileUpload(multipartFile, "102", mockAssessment);
		assertNotNull(originalFileUpload);

		// Verify the results of the upload are as expected
		verifyFileUpload(originalFileUpload, UploadStatus.SUCCESS, TEST_1_FILENAME, TEST_1_LENGTH, TEST_1_CONTENT_TYPE,
				"Application form (mandatory)");

		// Verify the file contents as are expected
		verifyFileContents(originalFileUpload, fileContent);
	}


	@Test 
	public void whenProcessFileUpload_fileInfected_UploadFails() throws Exception{
		// Create the sample content
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_1_LENGTH).getBytes(FILE_ENCODING);
		setUpBasicMultipartFile(fileContent);

		//Mock virus scanning, infected file
		when(mockClamAVClient.scan(any(InputStream.class))).thenReturn(invalidBytes);		

		// Process the uploaded multipart file object
		FileUpload fileUpload = fileUploadService.processFileUpload(mockMultipartFile, "102", mockAssessment);

		assertNotNull(fileUpload);
		assertEquals(UploadStatus.FILE_CONTAINS_VIRUS, fileUpload.getStatus() );
	}


//	@Test 
	public void whenProcessFileUpload_fileFailsToLoadToVirusScanner_UploadContinues() throws Exception{
		// Create the sample content
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_1_LENGTH).getBytes(FILE_ENCODING);
		setUpBasicMultipartFile(fileContent);
	
		//Mock general virus scanning failure
		when(mockClamAVClient.ping()).thenReturn(false);
		when(mockClamAVClient.scan(any(InputStream.class))).thenThrow(new IOException());
	
		// Process the uploaded multipart file object
		FileUpload originalFileUpload = fileUploadService.processFileUpload(mockMultipartFile, "100", mockAssessment);
		assertNotNull(originalFileUpload);

		// Verify the results of the upload are as expected
		verifyFileUpload(originalFileUpload, UploadStatus.SUCCESS, TEST_1_FILENAME, TEST_1_LENGTH, TEST_1_CONTENT_TYPE,	"Support plan (mandatory)");

		// Verify the file contents as are expected
		verifyFileContents(originalFileUpload, fileContent);
	}


//	@Test
	public void whenProcessFileUpload_VirusScanNotAvailable_UploadContinues() throws Exception {
		// Create the sample content
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_1_LENGTH).getBytes(FILE_ENCODING);
		setUpBasicMultipartFile(fileContent);

		//Mock virus scanning unavailable
		when(mockClamAVClient.ping()).thenReturn(false);
	
		// Process the uploaded multipart file object
		FileUpload originalFileUpload = fileUploadService.processFileUpload(mockMultipartFile, "100", mockAssessment);
		assertNotNull(originalFileUpload);

		// Verify the results of the upload are as expected
		verifyFileUpload(originalFileUpload, UploadStatus.SUCCESS, TEST_1_FILENAME, TEST_1_LENGTH, TEST_1_CONTENT_TYPE,	"Support plan (mandatory)");

		// Verify the file contents as are expected
		verifyFileContents(originalFileUpload, fileContent);
	}


	@Test
	public void whenProcessFileUpload_NoFileProvided_UploadFail() throws Exception {
		// Create the multipart file objects representing our test data
		MockMultipartFile multipartFile = createMultipartFile(TEST_1_FILENAME, null, null, new byte[] {});

		//Mock virus scanning
		when(mockClamAVClient.ping()).thenReturn(true);
		when(mockClamAVClient.scan(any(InputStream.class))).thenReturn(validBytes);	

		// Process the uploaded multipart file object
		FileUpload fileUpload = fileUploadService.processFileUpload(multipartFile, "102", mockAssessment);
	
		assertNotNull(fileUpload);
		assertEquals(fileUpload.getStatus(), UploadStatus.NO_FILE_PROVIDED);
	}


	@Test
	public void whenProcessFileUpload_InvalidFileSize_UploadFail() throws Exception {
		// Create the sample content
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_3_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_3_LENGTH, fileContent.length);

		// Create the multipart file object representing our test data
		MockMultipartFile multipartFile = createMultipartFile(TEST_3_NAME, TEST_3_FILENAME, TEST_3_CONTENT_TYPE, fileContent);

		// Process the uploaded multipart file object
		FileUpload fileUpload = fileUploadService.processFileUpload(multipartFile, "102", mockAssessment);

		assertNotNull(fileUpload);
		assertEquals(UploadStatus.INVALID_FILE_SIZE, fileUpload.getStatus() );
	}


	@Test
	public void whenProcessFileUpload_InvalidContentType_UploadFail() throws Exception {
		// Create the sample content
		byte[] file4Content = RandomStringUtils.randomAscii(TEST_4_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_4_LENGTH, file4Content.length);
		byte[] file5Content = RandomStringUtils.randomAscii(TEST_5_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_5_LENGTH, file5Content.length);

		// Create the multipart file object representing our test data
		MockMultipartFile multipartFile4 = createMultipartFile(TEST_4_NAME, TEST_4_FILENAME, TEST_4_CONTENT_TYPE, file4Content);
		MockMultipartFile multipartFile5 = createMultipartFile(TEST_5_NAME, TEST_5_FILENAME, TEST_5_CONTENT_TYPE, file5Content);

		// Process the uploaded multipart file object
		FileUpload originalFileUpload4 = fileUploadService.processFileUpload( multipartFile4, "102", mockAssessment);
		FileUpload originalFileUpload5 = fileUploadService.processFileUpload( multipartFile5,"102", mockAssessment);
		assertNotNull(originalFileUpload4);
		assertNotNull(originalFileUpload5);

		// Verify the results of the upload are as expected
		assertEquals(UploadStatus.INVALID_FILE_SIZE, originalFileUpload4.getStatus());
		assertEquals(UploadStatus.INVALID_FILE_SIZE, originalFileUpload5.getStatus());			
	}
	

	@Test
	public void whenProcessFileUpload_InvalidFileName_UploadFail() throws Exception {
		// Create the sample content
		byte[] fileContent = RandomStringUtils.randomAscii(TEST_1_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_1_LENGTH, fileContent.length);

		// Create the multipart file object representing our test data
		MockMultipartFile multipartFile = createMultipartFile(TEST_1_NAME, null, TEST_1_CONTENT_TYPE, fileContent);
		when(configService.getMaxFileSize()).thenReturn(MAX_FILE_SIZE);
		
		// Process the uploaded multipart file object
		FileUpload originalFileUpload = fileUploadService.processFileUpload( multipartFile, "102", mockAssessment);
		
		assertNotNull(originalFileUpload);		
		assertEquals(UploadStatus.INVALID_FILE_NAME,originalFileUpload.getStatus());
	}
	

//	@Test
	public void whenDeleteUploadedFile_validFile_FileDeleted() throws Exception {
		// Create the sample content
		byte[] file1Content = RandomStringUtils.randomAscii(TEST_1_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_1_LENGTH, file1Content.length);
		byte[] file2Content = RandomStringUtils.randomAscii(TEST_2_LENGTH).getBytes(FILE_ENCODING);
		assertEquals(TEST_2_LENGTH, file2Content.length);

		// Create the multipart file objects representing our test data
		MockMultipartFile multipartFile1 = createMultipartFile(TEST_1_NAME, TEST_1_FILENAME, TEST_1_CONTENT_TYPE, file1Content);
		MockMultipartFile multipartFile2 = createMultipartFile(TEST_2_NAME, TEST_2_FILENAME, TEST_2_CONTENT_TYPE, file2Content);
		
		when(configService.getMaxFileSize()).thenReturn(MAX_FILE_SIZE);
		when(configService.getFileLocaton()).thenReturn(OUTPUT_DIR + System.getProperty("file.separator"));
		Map<String, String> subtypes = Maps.newLinkedHashMap();
		subtypes.put("100", "Support plan (mandatory)");
		subtypes.put("101", "Whole school provision map (mandatory)");
		subtypes.put("102", "Application form (mandatory)");
		when(configService.getSubtypes()).thenReturn(subtypes);

		// Process the uploaded multipart file objects
		FileUpload originalFileUpload1 = fileUploadService.processFileUpload( multipartFile1,"101", mockAssessment);
		FileUpload originalFileUpload2 = fileUploadService.processFileUpload( multipartFile2, "102", mockAssessment);
		assertNotNull(originalFileUpload1);
		assertNotNull(originalFileUpload2);
	
		// Verify the results of the uploads are as expected
		verifyFileUpload(originalFileUpload1, UploadStatus.SUCCESS, TEST_1_FILENAME, TEST_1_LENGTH, TEST_1_CONTENT_TYPE, "Whole school provision map (mandatory)");
		verifyFileUpload(originalFileUpload2, UploadStatus.SUCCESS, TEST_2_FILENAME, TEST_2_LENGTH, TEST_2_CONTENT_TYPE, "Application form (mandatory)");
				
		// Verify the file contents as are expected
		verifyFileContents(originalFileUpload1, file1Content);
		verifyFileContents(originalFileUpload2, file2Content);
		
		UploadedFileInfo uploadedFileInfo1 = testDataInitializer.initUploadedFileInfo("101", "Whole school provision map (mandatory)", originalFileUpload1.getFileName());
		UploadedFileInfo uploadedFileInfo2 = testDataInitializer.initUploadedFileInfo("102", "Application form (mandatory)", originalFileUpload2.getFileName());

		List<UploadedFileInfo> uploadedFiles = new ArrayList<UploadedFileInfo>();
		uploadedFiles.add(uploadedFileInfo1);
		uploadedFiles.add(uploadedFileInfo2);	
		
		//  Delete one specific file upload and verify it has been deleted
		fileUploadService.deleteUploadedFile(  "111", "102");
		
		File file = originalFileUpload2.getFile();
		assertFalse(file.exists());		
	}


	private void setUpBasicMultipartFile(byte[] fileContent) throws Exception{
		// Create the multipart file object representing our test data
		mockMultipartFile = createMultipartFile(TEST_1_NAME, TEST_1_FILENAME, TEST_1_CONTENT_TYPE, fileContent);
		when(configService.getMaxFileSize()).thenReturn(MAX_FILE_SIZE);
		when(configService.getFileLocaton()).thenReturn(OUTPUT_DIR + System.getProperty("file.separator"));
		subtypes = Maps.newLinkedHashMap();
		subtypes.put("100", "Support plan (mandatory)");
		when(configService.getSubtypes()).thenReturn(subtypes);		
	}

	
	private MockMultipartFile createMultipartFile(String name, String filename, String contentType, byte[] content)
			throws IOException {
		return new MockMultipartFile(name, filename, contentType, new ByteArrayInputStream(content));
	}


	private void verifyFileUpload(FileUpload fileUpload, UploadStatus expectedUploadStatus, String expectedFilename,
			long expectedFileSize, String expectedContentType, String expecteduploadDocumentType) {

		String id = fileUpload.getId();
		assertNotNull(id);
		try {
			UUID.fromString(id);
			assertTrue(id.matches(REGEX_UUID));
		} catch (IllegalArgumentException e) {
			fail(String.format("ID [%s] is not a valid UUID", id));
		}

		UploadStatus actualUploadStatus = fileUpload.getStatus();
		assertEquals(expectedUploadStatus, actualUploadStatus);

		String actualFilename = fileUpload.getFileName();
		assertEquals(expecteduploadDocumentType + ".txt", actualFilename);

		long actualFileSize = fileUpload.getFileSize();
		assertEquals(expectedFileSize, actualFileSize);

		String actualContentType = fileUpload.getFileContentType();
		assertEquals(expectedContentType, actualContentType);

		File file = fileUpload.getFile();
		if (actualUploadStatus.equals(UploadStatus.SUCCESS)) {
			assertTrue(file.exists());
			String filePath = file.getAbsolutePath();
			assertTrue(filePath.startsWith(OUTPUT_DIR));
		} else {
			// If the upload was not successful, ensure no file was created
			assertNull(file);
		}
	}

	private void verifyFileContents(FileUpload fileUpload, byte[] expectedContents) throws IOException {
		// Get a handle on the filer and ensure it exists
		File file = fileUpload.getFile();
		assertNotNull(file);
		assertTrue(file.exists());
		// Read the file in and write the contents to a string
		InputStream inputStream = new FileInputStream(file);
		Writer writer = new StringWriter();
		try {
			IOUtils.copy(inputStream, writer);

			// Get the contents of the string an array of bytes
			byte[] actualContents = writer.toString().getBytes(FILE_ENCODING);

			// Verify the expected contents matches the actual contents
			assertEquals(expectedContents.length, actualContents.length);
			assertArrayEquals(expectedContents, actualContents);
		} finally {
			// Make sure file closed
			IOUtils.closeQuietly(inputStream);
		}

	}

}