package uk.gov.bristol.send.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.TestDataInitializer;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.repo.AssessmentsRepository;

@MockBean(AssessmentsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemplateServiceTest {

	private TemplateService templateService;

	private TestDataInitializer testDataInitializer;

	@Mock
	HttpServletResponse mockHttpServletResponse;

	@Mock
	ServletOutputStream outputStream;

	@BeforeAll
	public void setUp() {
		templateService = new TemplateService();
		testDataInitializer = new TestDataInitializer();

	}

	@AfterAll
	public void tearDown() throws Exception {
		templateService = null;
		testDataInitializer = null;

	}

	@Test
	public void whenGetTemplateContent_downloadPdfTemplate_returnsValidDownloadPdfTemplateContent() throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("ownerName", assessment.getOwner());
		pdfModel.put("needStatements", assessment.getSelectedNeedStatements());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("provisions", assessment.getSelectedProvisions());

		String html = templateService.getTemplateContent("downloadpdf.ftlh", pdfModel);

		assertTrue(html.contains("<span>UPN: </span> <span class=\"bolded\" > Z123456788888B </span>"));
		assertTrue(html.contains("<span>School name: </span> <span class=\"bolded\" >Springfield Elementary</span>"));
		assertTrue(html.contains("<img src=\"https://style.bristol.gov.uk/images/logo-bcc.png\" />"));
		assertTrue(html.contains("<h2>Selected needs</h2>"));
		assertTrue(html.contains("<h2>Planned  provision</h2>"));

	}

	@Test
	public void whenGetTemplateContent_submissionPdfTemplate_returnsValidSubmissionPdfTemplateContent()
			throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);

		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("ownerName", assessment.getOwner());
		pdfModel.put("needStatements", assessment.getSelectedNeedStatements());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("provisions", assessment.getSelectedProvisions());

		pdfModel.put("submittedDate", new Date());

		String html = templateService.getTemplateContent("submissionpdf.ftlh", pdfModel);

		assertTrue(html.contains("<span>UPN: </span> <span class=\"bolded\" > Z123456788888B </span>"));
		assertTrue(html.contains("<span>School name: </span> <span class=\"bolded\" >Springfield Elementary</span>"));
		assertTrue(html.contains("<img src=\"https://style.bristol.gov.uk/images/logo-bcc.png\" />"));
		assertTrue(html.contains("<h2>Selected needs</h2>"));
		assertTrue(html.contains("<h2>Planned funded provision</h2>"));
		assertTrue(html.contains("Total cost of provision request: £"));

	}

	@Test
	public void whenGetTemplateContent_submissionEmailTemplate_returnsValidSubmissionEmailTemplateContent()
			throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);

		Map<String, Object> pdfModel = new HashMap<>();

		pdfModel.put("ownerEmail", assessment.getOwner());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("submittedDate", new Date());

		String html = templateService.getTemplateContent("submissionEmail.ftlh", pdfModel);

		assertTrue(html.contains("<p>UPN: Z123456788888B </p>"));
		assertTrue(html.contains("<p>School: Springfield Elementary</p>"));
		assertTrue(html.contains("<p>Submitted on: " + new DateTime(new Date()).toString("dd/MM/yyyy") + "</p>"));

	}

	@Test
	public void whenGetTemplateContent_invalidTemplate_throwsException() throws Exception {

		Map<String, Object> model = new HashMap<>();

		assertThrows(Exception.class, () -> {
			templateService.getTemplateContent("invalidTemplate.ftlh", model);

		});

	}

}