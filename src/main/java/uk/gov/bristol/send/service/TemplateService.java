package uk.gov.bristol.send.service;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Component
public class TemplateService {

	private Logger LOGGER = LoggerFactory.getLogger(TemplateService.class);

	/**
	 * Gets the template content.
	 *
	 * @param templateName the template name
	 * @param data         the data
	 * @return the template content
	 * @throws Exception
	 */
	public String getTemplateContent(String templateName, Object data) throws Exception {
		StringWriter stringWriter = new StringWriter();
		try {
			String sep = System.getProperty("file.separator");
			
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			cfg.setClassForTemplateLoading(getClass(), sep + "templates");
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setClassicCompatible(true);
			
			Template temp = cfg.getTemplate(templateName);
			temp.process(data, stringWriter);
		} catch (Exception e) {
			LOGGER.error("Exception while Template Processing: " + e.getMessage());
			throw new Exception("Exception while Template Processing");
		}
		return stringWriter.getBuffer().toString();
	}

}
