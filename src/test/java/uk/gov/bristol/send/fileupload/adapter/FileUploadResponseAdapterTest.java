package uk.gov.bristol.send.fileupload.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileUploadResponseAdapterTest {

	private FileUploadResponseAdapter fileUploadResponseAdapter;
	@BeforeAll
	public void setUp() throws Exception {
		fileUploadResponseAdapter = new FileUploadResponseAdapter( );
	}

	@AfterAll
	public void tearDown() throws Exception {
		fileUploadResponseAdapter = null;
	}
	

	@Test
	public void getMessageForUploadStatus_whenInvoked_returnsExpectedMessage() {
		String expectedMessage = "Invalid file type";
		String message = fileUploadResponseAdapter.getMessageForUploadStatus(UploadStatus.INVALID_CONTENT_TYPE);
		assertEquals(expectedMessage, message);
	}

}
