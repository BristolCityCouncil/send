package uk.gov.bristol.send.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import uk.gov.bristol.send.fileupload.service.SharepointService;
import uk.gov.bristol.send.model.Assessment;                        



@Service
public class PDFService {
	
	private Logger LOGGER = LoggerFactory.getLogger(PDFService.class);
	
	/** The Template Service. */
	private TemplateService templateService;
	
	private SharepointService sharepointService;
	 
    private Path tmpDir;
   

    public PDFService(TemplateService templateService, SharepointService sharepointService){
        tmpDir = Path.of(System.getProperty("java.io.tmpdir")); 
        this.templateService = templateService;
        this.sharepointService = sharepointService;
    }
  
	public HttpServletResponse savePdfWithFooter(String outputFileName, Map<String, Object> pdfModel,
			HttpServletResponse response, String simulateDownloadPDFFailure) throws Exception {
		
		String sep = System.getProperty("file.separator");
		String outputDest = tmpDir + sep + outputFileName + ".pdf";
		
		String html = templateService.getTemplateContent("downloadpdf.ftlh", pdfModel);

		File destinationFile = createPDF(simulateDownloadPDFFailure, html, outputDest);

		// Copy the generated pdf content file to the response
		generateOutputResponse(response, destinationFile);

		return response;

	}
    
	public File generateAndUploadSubmissionPdf(String UPN, String uploadFolderId, Map<String, Object> pdfModel) throws Exception {
		
		String sep = System.getProperty("file.separator");
		String outputDest = tmpDir + sep + UPN + ".pdf";

		String html = templateService.getTemplateContent("submissionpdf.ftlh", pdfModel);

		File destinationFile = createPDF("", html, outputDest);
		LOGGER.info("created PDF: " + destinationFile.getName() + " under " + tmpDir);
		
		sharepointService.addSubmittedFileToUPNFolder(UPN, uploadFolderId, destinationFile);	
				
		return destinationFile;

	}    
	
	private File createPDF(String simulateDownloadPDFFailure, String html, String outputDest)
			throws FileNotFoundException, Exception, IOException {
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
		return destinationFile;
	}
	
	private void generateOutputResponse(HttpServletResponse response, File destinationFile)
			throws FileNotFoundException, IOException {
		InputStream inputStream = new BufferedInputStream(new FileInputStream(destinationFile));
		
		response.setContentType("application/pdf");		   
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + destinationFile.getName() + "\""));			
		 
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		response.getOutputStream().flush();
		
		FileSystemUtils.deleteRecursively(destinationFile);
	}


}
