package uk.gov.bristol.send.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.TestDataInitializer;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Owner;
import uk.gov.bristol.send.repo.AssessmentsRepository;

@MockBean(AssessmentsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailServiceTest {

	private EmailService emailService;

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
	ConfigService configService;

	@MockBean
	private JavaMailSender javaMailSender;

	@BeforeAll
	public void setUp() {
		emailService = new EmailService(configService, javaMailSender, templateService);
		testDataInitializer = new TestDataInitializer();

	}

	@AfterAll
	public void tearDown() throws Exception {
		emailService = null;
		testDataInitializer = null;

	}

	@Test
	public void whenSendEmail_validData_sendEmail() throws Exception {

		Assessment assessment = testDataInitializer.initAssessments().get(1);

		Map<String, Object> emailModel = new HashMap<>();
		Owner owner = new Owner();
		owner.setOwnerEmail("");

		emailModel.put("yourAssessment", assessment);

		emailModel.put("submittedDate", new Date());
		emailModel.put("yourAssessment", assessment);
		emailModel.put("ownerEmail", assessment.getOwner());
		MimeMessage mimeMessage = new MimeMessage((Session) null);
		
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		when(configService.getSubmissionEmailSubject()).thenReturn("test");
		when(configService.getEmailFrom()).thenReturn("test@test.com");
		when(configService.getSubmissionEmailTopUpPanel()).thenReturn("test@test.com");

		when(templateService.getTemplateContent("submissionEmail.ftlh", emailModel)).thenReturn("test");

		when(mockHttpServletResponse.getOutputStream()).thenReturn(outputStream);
		when(templateService.getTemplateContent("downloadpdf.ftlh", emailModel))
				.thenReturn("<html>\r\n" + "<body>" + "</body>\r\n" + "</html>");

		emailService.sendEmail(emailModel);

		verify(javaMailSender, times(1)).send(mimeMessage);

	}

}