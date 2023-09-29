package uk.gov.bristol.send.fileupload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.google.gson.JsonPrimitive;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCreateUploadSessionParameterSet;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.Folder;
import com.microsoft.graph.models.UploadSession;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.DriveItemCollectionRequestBuilder;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.microsoft.graph.tasks.LargeFileUploadTask;

import uk.gov.bristol.send.fileupload.adapter.ByteCountSizeFormatter;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.service.ConfigService;



@Service
public class SharepointService {
    /** The Constant LOG. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
    private ConfigService configService;

    private GraphServiceClient<?> graphClient;
    
    private String clientId;

    private String siteId;
    
    private String driveId;

    private String secret;

    private String sendFolder;
    
	// TODO: add your organisation's id, tenant guid, here
    final String TENANT_GUID = "";

    final String SCOPE = "https://graph.microsoft.com/.default";
    

    /** The byte count size formatter. */
	@Autowired
	private ByteCountSizeFormatter byteCountSizeFormatter;


    @Autowired
    public SharepointService(ConfigService configService){
        this.configService = configService;
        byteCountSizeFormatter = new ByteCountSizeFormatter();
        this.clientId = configService.getSharepointClientId();
        this.siteId = configService.getSharepointSiteId();
        this.driveId = configService.getSharepointDriveId();
        this.secret = configService.getSharepointAppSecret(); 
        this.sendFolder = configService.getSharepointSubFolder();     
    }

    
    public void setUpConnectionForApp(){
        List<String> scopes = List.of(SCOPE);

        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(secret)
            .tenantId(TENANT_GUID)
            .build();

        final TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential);

        graphClient = GraphServiceClient
            .builder()
            .authenticationProvider(tokenCredAuthProvider)
            .buildClient();

    }    

  
    public List<UploadedFileInfo> getFileListForUPN(String UPN, String uploadFolder){
        List<UploadedFileInfo> uploadedFileList = new ArrayList<UploadedFileInfo>();
        Map<String, String> subTypes = configService.getSubtypes();
        DriveItemCollectionPage children;        
        String path;

        // If there is no folder, no files have been uploaded yet
        if((uploadFolder == null) || (uploadFolder.trim().isEmpty())){
            return uploadedFileList;
        }

        getGraphServiceClient();        

        if(!sendFolder.equals("NO_SUB") ){
            path = sendFolder + "/" + uploadFolder;
        }else{
            path = uploadFolder;
        }    
        
        
        try{
            //Fetch items in a folder with the requested UPN. If no folder it will throw an exception
            children = graphClient
                .sites(siteId)
                .drives(driveId)
                .root()
                .itemWithPath(path)            
                .children()                           
                .buildRequest()
                .get();

        }catch(GraphServiceException gse){            
            logger.info("No files found");
            return uploadedFileList;
        }        

        
        //There should only be one UPN folder with the exact name. Sharepoint won't allow duplicate UPN named folders 
        List<DriveItem> pages = children.getCurrentPage();
        Iterator<DriveItem> iter = pages.iterator();
            
        //Populate the file list
        while (iter.hasNext()) {
            DriveItem page = iter.next(); 
            Folder folder = page.folder;
            com.microsoft.graph.models.File file = page.file;
                
            //Add only DriveItems of type file to the list                 
            if(folder == null && file != null){
                UploadedFileInfo fileInfo = new UploadedFileInfo();
                String fileName = page.name;
                int extIndex = page.name.lastIndexOf(".") + 1;

                // Don't include the panel submission in file list
                if(!fileName.startsWith(UPN + "_provisioncosts")){
                    fileInfo.setFileName(fileName);
                    fileInfo.setFileSize(byteCountSizeFormatter.format(page.size));                    
                    fileInfo.setId(page.id);
                    fileInfo.setDocumentTypeName(getContentTypeName(fileName, subTypes));               
                    fileInfo.setFileContentType(fileName.substring(extIndex));
                    uploadedFileList.add(fileInfo);
                    logger.info(page.name + " is a file of type: " + file.mimeType);
                }    
            }
        }  
          
        return uploadedFileList;
    }


    public HashMap<String, String> addFileToUPNFolder(String UPN, MultipartFile file, String uploadDocumentType, String uploadFolder) {
        HashMap<String, String> fileResult = new HashMap<>();
        getGraphServiceClient();
        String itemPath;

               
        try{                       
            InputStream fileStream = file.getInputStream();
            long streamSize = file.getSize();     
            
    		String originalFileName = file.getOriginalFilename();
    		String fileExtension = StringUtils.substringAfterLast(originalFileName, ".");
    		
    		String friendlyFileName = uploadDocumentType + "." + fileExtension;           
    		friendlyFileName = StringUtils.replace(friendlyFileName, "/", "-");

            if(!sendFolder.equals("NO_SUB") ){
                itemPath = "/" + sendFolder + "/" + uploadFolder + "/" + friendlyFileName;
            }else{
                itemPath = "/" + uploadFolder + "/" + friendlyFileName;
            } 
                       
            LargeFileUploadResult<DriveItem> result =  upload(fileStream, streamSize, itemPath);
            
            fileResult.put("id", result.responseBody.id);
            fileResult.put("name", result.responseBody.name);
            fileResult.put("uploadFolder", uploadFolder);

            return fileResult;
            
        }catch(IOException ioe){
           logger.error("Unable to add file to folder: " + ioe.getMessage());
        }
        return fileResult;    
    }
    
    
    public void addSubmittedFileToUPNFolder(String UPN, String uploadFolderId, File file) {
        getGraphServiceClient();  
        
        
        try{     
        	InputStream fileStream = new FileInputStream(file);
           
            long streamSize =  file.length();
            
    		String originalFileName = file.getName();
    		String fileExtension = StringUtils.substringAfterLast(originalFileName, ".");
    		
    		DateTime dt = new DateTime(new Date());
            String currentDate = dt.toString("dd-MM-yyyy");
    		
    		String friendlyFileName = UPN + "_provisioncosts_" + currentDate + "." + fileExtension ;
    		
            String itemPath = "/" + uploadFolderId + "/" + friendlyFileName;
                       
            upload(fileStream, streamSize, itemPath);
           
            
        }catch(IOException ioe){
        	logger.error("Unable to add file to sharepoint folder\n\n" + ioe.getMessage());
        }
        catch(Exception ioe){
        	logger.error("Unable to add file to sharepoint folder\n\n" + ioe.getMessage());
        }
        finally{
        	FileSystemUtils.deleteRecursively(file);
        	 logger.info("\nall done uploadin'\n");
        }
            
    }
    
    
	private LargeFileUploadResult<DriveItem> upload(InputStream fileStream, long streamSize, String itemPath) throws IOException {
		DriveItemUploadableProperties diup = new DriveItemUploadableProperties();
        diup.additionalDataManager().put("@microsoft.graph.conflictBehavior", new JsonPrimitive("rename"));
                    
        DriveItemCreateUploadSessionParameterSet uploadParams =
            DriveItemCreateUploadSessionParameterSet.newBuilder().withItem(diup).build();
        
        // Create an upload session
        UploadSession uploadSession = graphClient
                        .sites(siteId)
                        .drives(driveId)
                        .root()         
                        .itemWithPath(itemPath)  // itemPath like "/Folder/file.txt" does not need to be a path to an existing item
                        .createUploadSession(uploadParams)                            
                        .buildRequest()
                        .post();
        
        LargeFileUploadTask<DriveItem> largeFileUploadTask =
            new LargeFileUploadTask<DriveItem> (uploadSession, graphClient, fileStream, streamSize, DriveItem.class);
        
        // Do the upload
        // Max slice size must be a multiple of 320 KiB or 0 as default
        // int maxSliceSize = 320 * 1024 *2;
        LargeFileUploadResult<DriveItem> result = largeFileUploadTask.upload(0);
		return result;
	}


    public void deleteFileFromUPNFolder(String uploadFolder, String fileId){
        String path;
        getGraphServiceClient();

        if(!sendFolder.equals("NO_SUB") ){
            path = sendFolder + "/" + uploadFolder;
        }else{
            path = uploadFolder;
        }   

        //Fetch items in a folder with the requested UPN
        DriveItemCollectionPage children = graphClient
            .sites(siteId)
            .drives(driveId)
            .root()
            .itemWithPath(path)                       
            .children()                         
	        .buildRequest()
	        .get();

        //There should only be one UPN folder with the exact name. Sharepoint won't allow duplicate UPN named folders 
        DriveItemCollectionRequestBuilder nextPage;
        List<DriveItem> pages = children.getCurrentPage();    
        Iterator<DriveItem> items;
        DriveItem deletedFile = new DriveItem();
        
        
        while((children != null) && (pages != null)){
            items = pages.iterator();            
            deletedFile.id = "id";

            while((items.hasNext()) && (deletedFile.id != null)) {

                DriveItem page = items.next();
                // TODO: remove this output
                System.out.println("checking file: " + page.name + " with id: " + page.id);

                if(fileId.equals(page.id)){                     

                    try{
                        // When file is deleted, the values in deletedFile will be null.
                        deletedFile = graphClient
                            .sites(siteId)
                            .drives(driveId)
                            .items(fileId)          
                            .buildRequest()
                            .delete();  
                    }catch(ClientException ce){
                        logger.warn("Unable to delete file in folder: " + uploadFolder + " with id: " + fileId);
                    }             
                    
                } 
            }  
                       
            // A page represents contents of a unique folder which only contains a small number of files. 
            // This should be unnescessary, but worth logging if there actually is a nextPage
            pages = null;            
            nextPage = children.getNextPage();
            
            if(nextPage != null){
                children = nextPage.buildRequest().get();
                logger.error("Delete file request: Found additional pages when searching for folder: " + uploadFolder);               
                if(children != null) pages = children.getCurrentPage();                       
            }        

        }
        
    }    

    
    public GraphServiceClient<?> getGraphServiceClient(){
        if(graphClient == null){
            setUpConnectionForApp();
        }    
        return graphClient;
    }


    public void SetGraphServiceClient(GraphServiceClient<?> graphServiceClient){
        this.graphClient = graphServiceClient;
    }


    private String getContentTypeName(String fileName, Map<String, String> subTypes){
        //Match the first part of filename to a content type name. 
        String name = fileName.replace("-","/");
        
        for (String value : subTypes.values()) {
            if(name.startsWith(value)){
                return value;
            }
        }
        return "";
    }
  
}
