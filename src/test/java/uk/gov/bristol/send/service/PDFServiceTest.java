package uk.gov.bristol.send.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.TestDataInitializer;
import uk.gov.bristol.send.fileupload.service.SharepointService;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.repo.AssessmentsRepository;

@MockBean(AssessmentsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PDFServiceTest {

	private PDFService pdfService;

	private TestDataInitializer testDataInitializer;

	@Mock
	Assessment assessment;

	@Mock
	HttpServletResponse mockHttpServletResponse;

	@Mock
	ServletOutputStream outputStream;
	
	@MockBean
	TemplateService templateService;
	
	@MockBean
	SharepointService sharepointService;

	@BeforeAll
	public void setUp() {
		pdfService = new PDFService(templateService, sharepointService);
		testDataInitializer = new TestDataInitializer();

	}

	@AfterAll
	public void tearDown() throws Exception {
		pdfService = null;
		testDataInitializer = null;

	}

	@Test
	public void whenSavePdfWithFooter_validData_returnsValidResponseContent() throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);
		MockHttpServletResponse response = new MockHttpServletResponse();

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("ownerName", assessment.getOwner());
		pdfModel.put("needStatements", assessment.getSelectedNeedStatements());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("provisions", assessment.getSelectedProvisions());

		when(mockHttpServletResponse.getOutputStream()).thenReturn(outputStream);
		when(templateService.getTemplateContent("downloadpdf.ftlh", pdfModel)).thenReturn("<html>\r\n"
				+ "<body>" + "</body>\r\n"+ "</html>");
		

		pdfService.savePdfWithFooter(assessment.getUpn(), pdfModel, response, "");

		assertSame(response.getContentType(), "application/pdf");
		assert (response.getHeader("Content-Disposition").contains("attachment;"));
		assert (response.getHeader("Content-Disposition").contains(assessment.getUpn() + ".pdf"));
		
	}
	

	@Test
	public void whenSavePdfWithFooter_validData_returnsNoFile() throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);
		MockHttpServletResponse response = new MockHttpServletResponse();

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("ownerName", assessment.getOwner());
		pdfModel.put("needStatements", assessment.getSelectedNeedStatements());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("provisions", assessment.getSelectedProvisions());

		when(mockHttpServletResponse.getOutputStream()).thenReturn(outputStream);
		when(templateService.getTemplateContent("downloadpdf.ftlh", pdfModel)).thenReturn("<html>\r\n"
				+ "<body>" + "</body>\r\n"+ "</html>");

		pdfService.savePdfWithFooter(assessment.getUpn(), pdfModel, response, "");

		Path tmpDir = Path.of(System.getProperty("java.io.tmpdir")); 
		String sep = System.getProperty("file.separator");   
		String outputDest = tmpDir + sep + assessment.getUpn() + ".pdf";
		File destinationFile = new File(outputDest);
		
		assertFalse(destinationFile.exists());

	}
	
	@Test
	public void whenGenerateAndUploadSubmissionPdf_validData_returnsValidFile() throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("ownerName", assessment.getOwner());
		pdfModel.put("needStatements", assessment.getSelectedNeedStatements());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("provisions", assessment.getSelectedProvisions());
		pdfModel.put("submittedDate", new Date());
		
		when(templateService.getTemplateContent("submissionpdf.ftlh", pdfModel)).thenReturn("<html>\r\n"
				+ "<body>" + "</body>\r\n"+ "</html>");

		File generatedPDF = pdfService.generateAndUploadSubmissionPdf(assessment.getUpn(), assessment.getUploadFolderId(), pdfModel);		
				
		File destinationFile = new File(Path.of(System.getProperty("java.io.tmpdir")) + System.getProperty("file.separator") + assessment.getUpn() + ".pdf");
		
		assertTrue(destinationFile.exists());
		assertEquals(generatedPDF.getName(), assessment.getUpn() + ".pdf");

	}

}