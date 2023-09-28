package uk.gov.bristol.send.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * The Class EmailService.
 */

@Service
public class EmailService {

	/** The Constant LOG. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** The config service. */
	private ConfigService configService;

	/** The Template Service. */
	private TemplateService templateService;

	@Autowired
	private JavaMailSender javaMailSender;

	public EmailService(ConfigService configService, JavaMailSender javaMailSender, TemplateService templateService) {
		this.configService = configService;
		this.javaMailSender = javaMailSender;
		this.templateService = templateService;
	}

	public void sendEmail(Map<String, Object> emailModel) throws Exception {
	
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		String emailContent = templateService.getTemplateContent("submissionEmail.ftlh", emailModel);
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		helper.setSubject(configService.getSubmissionEmailSubject());
		helper.setTo((String)emailModel.get("ownerEmail"));
		helper.setFrom(configService.getEmailFrom());
		helper.setBcc(configService.getSubmissionEmailTopUpPanel());
		helper.setText(emailContent, true);
		
		javaMailSender.send(mimeMessage);
	}

}
