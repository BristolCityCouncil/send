package uk.gov.bristol.send.fileupload.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUpload.UploadStatus;
import uk.gov.bristol.send.fileupload.service.FileUploadValidator;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileUploadValidatorTest {
	private static final long MAX_FILE_SIZE = 1000L;
	private static final String[] ALLOWED_FILE_TYPES = new String[] { "application/msword",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
			"application/vnd.oasis.opendocument.text", "application/vnd.ms-excel",
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			"application/vnd.oasis.opendocument.spreadsheet", "application/vnd.ms-excel", "application/msword",
			"application/pdf", "image/jpg", "image/jpeg", "image/png", "image/gif", "image/tiff" };

	@Test
	public void evaluate_nullFileUploadSupplied() {		
        assertThrows(IllegalArgumentException.class, () -> {
        	new FileUploadValidator().evaluate(null, MAX_FILE_SIZE, ALLOWED_FILE_TYPES);
        	
        	   
        }); 
	}

	@Test
	public void evaluate_fileWasNotSupplied() {
		doStatusTest(UploadStatus.NO_FILE_PROVIDED, new FileUpload(), ALLOWED_FILE_TYPES);
	}

	@Test
	public void evalulate_fileSupplied() {
		FileUpload fileUpload = new FileUpload();
		fileUpload.setFileSize(100L);
		fileUpload.setFileName("validFileName.png");

		assertNotEquals(UploadStatus.NO_FILE_PROVIDED,
				new FileUploadValidator().evaluate(fileUpload, MAX_FILE_SIZE, ALLOWED_FILE_TYPES));
	}

	private void doStatusTest(UploadStatus expected, FileUpload fileUpload, String[] allowedFileTypes) {
		assertEquals(expected, new FileUploadValidator().evaluate(fileUpload, MAX_FILE_SIZE, allowedFileTypes));
	}

	@Test
	public void evaluate_fileIsHuge() {
		FileUpload source = new FileUpload();
		source.setFileSize(900000L);
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.INVALID_FILE_SIZE, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileIsAtTheLimit() {
		FileUpload source = new FileUpload();
		source.setFileSize(1000L);
		source.setFileContentType("image/jpg");
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.SUCCESS, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileIsAOK() {
		FileUpload source = new FileUpload();
		source.setFileSize(900L);
		source.setFileContentType("image/jpg");
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.SUCCESS, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_contentTypeIsInvalid() {
		FileUpload source = new FileUpload();
		source.setFileSize(900L);
		source.setFileContentType("image/bmp");
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.INVALID_CONTENT_TYPE, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_noContentTypesSupplied() {
		FileUpload source = new FileUpload();
		source.setFileSize(900L);
		source.setFileContentType("image/bmp");
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.SUCCESS, source, null);
	}

	@Test
	public void evaluate_emptyContentTypesSupplied() {
		FileUpload source = new FileUpload();
		source.setFileSize(900L);
		source.setFileContentType("image/bmp");
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.SUCCESS, source, new String[] {});
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_lessThan() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file<name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_greaterThan() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file>name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_colon() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file:name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_forwardSlash() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file/name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_backslash() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file\\name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_pipe() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file|name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_questionMark() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file?name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameInvalidWhenContains_asterisk() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file*name.png");
		doStatusTest(UploadStatus.INVALID_FILE_NAME, source, ALLOWED_FILE_TYPES);
	}

	@Test
	public void evaluate_fileNameValidChars() {
		FileUpload source = getValidFileUpload();
		source.setFileName("file_name-with_Valid.ch4racters.png");
		doStatusTest(UploadStatus.SUCCESS, source, ALLOWED_FILE_TYPES);
	}

	private FileUpload getValidFileUpload() {
		FileUpload source = new FileUpload();
		source.setFileSize(900L);
		source.setFileContentType("image/png");
		source.setFileName("validFileName.png");
		return source;
	}

	@Test
	public void evaluate() {
		FileUpload source = new FileUpload();
		source.setFileSize(900L);
		source.setFileContentType("application/vnd.ms-excel");
		source.setFileName("validFileName.png");

		doStatusTest(UploadStatus.SUCCESS, source, ALLOWED_FILE_TYPES);
	}
}
