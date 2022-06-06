package uk.gov.bristol.send.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;                        



@Service
public class PDFService {
	
	private Logger LOGGER = LoggerFactory.getLogger(PDFService.class);
	 
    private Path tmpDir;
   

    public PDFService(){
        tmpDir = Path.of(System.getProperty("java.io.tmpdir"));       
    }
  
    public HttpServletResponse  savePdfWithFooter(String outputFileName, Map<String, Object> pdfModel, HttpServletResponse response, String simulateDownloadPDFFailure) throws Exception{
        String html = new String();
        String sep = System.getProperty("file.separator");
        String outputDest = tmpDir + sep + outputFileName + ".pdf";

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        
        try {
            cfg.setClassForTemplateLoading(getClass(), sep + "templates");            
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setClassicCompatible(true);
            Template temp = cfg.getTemplate("pdf.ftlh");
            StringWriter stringWriter = new StringWriter();
            temp.process(pdfModel, stringWriter);
            html = stringWriter.toString();
        }catch(Exception e){
            LOGGER.error("Exception while Template Processing: " + e.getMessage());
            throw new Exception("Could not Save PDF");
        } 
        
          
        try {
            File destinationFile = new File(outputDest);
            FileOutputStream fosDestination = new FileOutputStream(destinationFile);
           
            html = StringUtils.isNotEmpty(simulateDownloadPDFFailure) && simulateDownloadPDFFailure.equals("true")? "": html;
            /** THE FLYING SAUCER WAY TO DO IT, FROM PROPERTY LICENSING */                       
            ITextRenderer renderer = new ITextRenderer();
		    renderer.setDocumentFromString(html);
		    renderer.layout();
		    try {
    			renderer.createPDF(fosDestination);

		    } catch (Exception ex) {
    			LOGGER.error("Exception while ITextRenderer create PDF: " + ex.getMessage());
    			throw new Exception("Could not Save PDF");
		    }finally{               
                renderer.finishPDF();
                fosDestination.close();
            }
		    

            //Copy the generated pdf content file to the response
		    InputStream inputStream = new BufferedInputStream(new FileInputStream(destinationFile));
		    
		    response.setContentType("application/pdf");		   
		    response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + destinationFile.getName() + "\""));			
			 
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.getOutputStream().flush();
			
			FileSystemUtils.deleteRecursively(destinationFile);	
	    
        } catch (Exception e) {
        	LOGGER.error("Exception while Updating the reponse with PDF: " + e.getMessage());
            throw new Exception("Could not Save PDF");
        }
        
        return response;

    }


}
