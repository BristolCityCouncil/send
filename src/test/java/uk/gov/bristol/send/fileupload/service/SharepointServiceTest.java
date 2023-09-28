package uk.gov.bristol.send.fileupload.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.google.common.collect.Maps;

import uk.gov.bristol.send.fileupload.service.SharepointService;
import uk.gov.bristol.send.service.ConfigService;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SharepointServiceTest {

    private SharepointService sharepointService;

    @MockBean
    ConfigService mockConfigService;

    @Mock
    MultipartFile mockMultipartFile;

    final String CLIENT_ID = "123qwe";

    final String SITE_ID = "789iop";
    
              
    @BeforeAll  
    public void setUp() {
        sharepointService = new SharepointService(mockConfigService); 
       
        Map<String, String> subtypes = Maps.newLinkedHashMap();
		subtypes.put("100", "Support plan (mandatory)");
		subtypes.put("101", "Whole school provision map (mandatory)");
		subtypes.put("102", "Application form (mandatory)");
        subtypes.put("10", "Application form (mandatory)");
        subtypes.put("104","Speech and language therapy report");
        subtypes.put("105","Occupational therapy report");
        subtypes.put("106","Paediatrician\u2019s report");
        subtypes.put("107","CAMHS report/involvement");
        subtypes.put("108","GP report/involvement");
        subtypes.put("109","Other");

		when(mockConfigService.getSubtypes()).thenReturn(subtypes);
        when(mockConfigService.getSharepointClientId()).thenReturn(CLIENT_ID);
        when(mockConfigService.getSharepointSiteId()).thenReturn(SITE_ID);
    }
    
    @AfterAll
    public void tearDown() throws Exception {
		sharepointService = null;
	}

    @Test
    public void getGraphServiceClient_connectsToSharepoint() throws Exception{
        //GraphServiceClient<?> graphClient = sharepointService.getGraphServiceClient();
        //assertNotNull(graphClient);
    }

    @Test
    public void setUpConnection_whenGetFileList_returnsValidFileList() throws Exception{
        //sharepointService.getFileListForUPN("A1234567890"); 
        // assert something here.
    }

    @Test
    public void addFileToUPNFolder_whenUPNNotInUse_createsNewFileInFolder() throws Exception{         
        // when(mockMultipartFile.getSize()).thenReturn(123456L); 
        // when(mockMultipartFile.getName()).thenReturn(""); 
        // when(mockMultipartFile.getName()).thenReturn(""); 
        // when(mockMultipartFile.getName()).thenReturn("");    

        // HashMap<String, String> result = sharepointService.addFileToUPNFolder("V1234567890", mockMultipartFile); 

        // assertNotNull(result.get("id"));
        // assertNotNull(result.get("name"));
    }

    @Test
    public void deleteFileFromUPNFolder_whenFileFound_FileIsDeleted() throws Exception{
        // sharepointService.deleteFileFromUPNFolder("A1234567890", "01RVMSYPURQ7KDRDDZZFHLM56R2KDMNH6Z");
        // sharepointService.getFileListForUPN("A1234567890"); 
        // assert something here.
    }

    @Test
    public void deleteFileFromUPNFolder_whenAdditionalPages_AdditionalPagesLogged() throws Exception{
        //sharepointService.deleteFileFromUPNFolder("A1234567890", "someId");
        //sharepointService.getFileListForUPN("A1234567890"); 
        //assert something here.
    }

    
}
