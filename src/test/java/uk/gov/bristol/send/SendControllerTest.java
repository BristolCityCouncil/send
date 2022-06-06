package uk.gov.bristol.send;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import uk.gov.bristol.send.exceptions.SendException;
import uk.gov.bristol.send.exceptions.SendPDFDownloadException;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.model.Owner;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.SelectedProvision;
import uk.gov.bristol.send.repo.AssessmentsRepository;
import uk.gov.bristol.send.service.AssessmentService;
import uk.gov.bristol.send.service.AuthenticationService;
import uk.gov.bristol.send.service.NeedService;
import uk.gov.bristol.send.service.PDFService;
import uk.gov.bristol.send.service.ProvisionService;


@MockBean(AssessmentsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SendControllerTest {
    
    private SendController sendController;

    private BindingAwareModelMap bindingAwareModelMap;

    private final String OWNER_EMAIL = "mauriceminor@leyland.co.uk";

    private final String OTHER_OWNER_EMAIL = "timmytestington@testersrus.com";

    private final String VALID_UPN = "A123456789012C";   

    private final String SCHOOL_NAME = "Bash Street";

    private final String VALID_ASSESSMENT_ID = "5942d909-36ca-4d63-92e1-292102a1740c";

    private final String SELECTED_NEED_STATEMENT_IDS = "1123,2345,";

    private final String VALID_AREA_ID = "CommunicationAndInteraction";

    private final String VALID_SUB_AREA_ID = "ReceptiveCommunication";

    private final String VALID_AREA_LABEL = "Communication and interaction";

    private final String VALID_SUB_AREA_LABEL = "Receptive communication"; 

    private final String VALID_NEED_LEVEL = "C";

    private final String BLANK_ERROR_STRING = "";

    private final String[] SELECTED_PROVISION_IDS = {"PGID3_PTID22_PS1", "PGID4_PTID25_PS3"};

    private final String PATH_PREFIX = "/top-up-assessment";

    @Mock
    Need need;    

    @MockBean
    AssessmentService assessmentService;

    @MockBean
    NeedService needService;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    ProvisionService provisionService;

    @MockBean
    PDFService pdfService;

    @MockBean
    SendUtilities sendUtilities;

    @Mock
    List<Need> listNeeds;

    @Mock 
    List<Need> filteredListNeeds;

    @Mock
    List<Assessment> emptyAssessmentList;

    @Mock
    List<Assessment> listAssessments;

    @Mock
    ArrayList<Provision> arrayListProvisions;

    @Mock
    List<Provision> listProvisions;

    @Mock
    List<NeedStatement> needStatements;

    @Mock 
    NeedStatement needStatement;

    @Mock
    List<String> selectedNeedStatementIds;

    @Mock
    List<String> selectedGroupIds;

    @Mock
    List<NeedStatement> selectedNeedStatements;

    @Mock
    List<SelectedProvision> listSelectedProvisions;

    @Mock
    HashMap<String, String> map;

    @Mock
    Assessment assessment;

    @Mock
    Owner owner;

    @Mock
    HttpServletRequest mockHttpServletRequest;

    @Mock
    Model model;

    @Mock
	private Logger log;

    @Spy
    BindingAwareModelMap spyBindingAwareModelMap;


    @BeforeAll
    public void setUp() {
        sendController = new SendController(authenticationService, assessmentService, needService, provisionService, pdfService);
        bindingAwareModelMap = new BindingAwareModelMap();
        spyBindingAwareModelMap = Mockito.spy(bindingAwareModelMap);
        sendController.setLogger(log);
    }

    @AfterAll
    public void tearDown() throws Exception {
        sendController = null;
        bindingAwareModelMap = null;
        spyBindingAwareModelMap = null;
    }

    @Test
    public void whenYourAssessments_findsAssessments_returnsHome() throws Exception{        
        setUpLogAndServices();
        assertSame(sendController.yourAssessments(mockHttpServletRequest, spyBindingAwareModelMap), "home"); 
        assertEquals(4, spyBindingAwareModelMap.size()); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("yourAssessments", listAssessments); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("currentUser", owner);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("upnError", BLANK_ERROR_STRING);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("pathPrefix", PATH_PREFIX); 
    }

    @Test
    public void whenNewAssessment_createsAssessment_returnsAssessment() throws Exception{        
        setUpLogAndServices();
        when(assessmentService.getAssessmentByIdForOwner(null, OWNER_EMAIL)).thenReturn(assessment);
        assertSame(sendController.newAssessment(mockHttpServletRequest, spyBindingAwareModelMap, VALID_UPN, SCHOOL_NAME), "summary");         
        assertEquals(7, spyBindingAwareModelMap.size());       
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("needAreas", listNeeds); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("needSubAreas", filteredListNeeds);        
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("assessment", assessment);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("currentUser", owner);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("selectedProvisions", listSelectedProvisions);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("pathPrefix", PATH_PREFIX);  
    }

    @Test
    public void whenNewAssessment_failsToCreateAssessment_logsError_returnsFailure() throws Exception{                
        setUpLogAndServices();
        when(assessmentService.createNewAssessment(any(Assessment.class))).thenThrow(new Exception());
        assertThrows(SendException.class, () -> {
        	assertSame(sendController.newAssessment(mockHttpServletRequest, spyBindingAwareModelMap, VALID_UPN, SCHOOL_NAME), "sendfailure");
        	verify(log).error("Unable to write new assessment to database: null");   
        });
        
    }

    @Test
    public void whenSingleAssessements_addsAssessment_returnsAssessment() throws Exception{
        setUpLogAndServices();
        assertSame(sendController.singleAssessment(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID), "summary"); 
        assertEquals(7, spyBindingAwareModelMap.size());
        assertThat(spyBindingAwareModelMap.get("needsMap"), instanceOf(Map.class));         
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("needAreas", listNeeds); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("needSubAreas", filteredListNeeds);        
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("assessment", assessment);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("currentUser", owner);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("selectedProvisions", listSelectedProvisions); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("pathPrefix", PATH_PREFIX);                 
    }

    @Test
    public void whenSingleAssessements_ownerNotMatching_returnsRedirect() throws Exception{
        setUpLogAndServices();             
        when(assessment.getOwner()).thenReturn(OTHER_OWNER_EMAIL);
        assertSame(sendController.singleAssessment(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID),"redirect:/");
    }

    @Test
    public void whenSingleAssessements_cannotFindAssessment_logsError_returnsFailure() throws Exception{
        sendController.setLogger(log);               
        when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenThrow(new Exception());        
        
        assertThrows(SendException.class, () -> {
        	assertSame(sendController.singleAssessment(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID), "sendfailure"); 
        	verify(log).error("Unable to get assessments from database: null");  
        });
        
    }

    @Test
    public void whenProvisions_returnsProvisions() throws Exception{
        setUpLogAndServices();
        assertSame(sendController.doProvisions(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, VALID_AREA_ID, VALID_SUB_AREA_ID, VALID_NEED_LEVEL), "provisions");
        assertEquals(10, spyBindingAwareModelMap.size()); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("provisions", arrayListProvisions);  
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("assessment", assessment);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("currentUser", owner);  
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("areaLabel", VALID_AREA_LABEL);               
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("subAreaLabel", VALID_SUB_AREA_LABEL);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("subAreaId", VALID_SUB_AREA_ID); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("needLevel", VALID_NEED_LEVEL);  
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("selectedProvisions", listSelectedProvisions);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("selectedProvisionIds", SELECTED_PROVISION_IDS); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("pathPrefix", PATH_PREFIX); 
    }

    @Test
    public void whenProvisions_cannotFindAssessment_logsError_returnsProvisions() throws Exception{
        setUpLogAndServices(); 
        when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenThrow(new Exception());        
        
        assertThrows(SendException.class, () -> {
        	assertSame(sendController.doProvisions(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, VALID_AREA_ID, VALID_SUB_AREA_ID, VALID_NEED_LEVEL), "sendfailure");        
            verify(log).error("Unable to get provisions from database: null");
       	   
       });  
    }


    @Test
    public void whenUpdateProvisions_cannotUpdateAssessment_logsError() throws Exception{
        setUpLogAndServices();          
        when(assessmentService.updateAssessmentWithSelectedProvisionStatements(any(), any(), any(), any(), any())).thenThrow(new Exception("Could not update assessment with selected provisions"));
       
        assertThrows(SendException.class, () -> {
        	 sendController.updateProvisions(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, SELECTED_PROVISION_IDS[0], VALID_SUB_AREA_ID, VALID_NEED_LEVEL);
        	 verify(log).error("Unable to update provisions in database: Could not update assessment with selected provisions");
        	   
        });       
              
    }    
    
    @Test
    public void whenUpdateProvisions_withRemovedProvisions_cannotUpdateAssessment_logsError() throws Exception{
    	setUpLogAndServices();     
        when(assessmentService.updateAssessmentWithRemovedProvisions(any(), any(), any())).thenThrow(new Exception("Could not update assessment with removed provisions"));
         
        assertThrows(SendException.class, () -> {
        	 sendController.deleteProvision(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, SELECTED_PROVISION_IDS[0], VALID_SUB_AREA_ID);       
             verify(log).error("Unable to update provisions in database: Could not update assessment with removed provisions");     
       });
    } 


    @Test
    public void whenDoNeeds_setsUpNeeds_returnsNeeds() throws Exception {
        setUpLogAndServices();               
        when(needService.findCurrentNeedLevel(selectedNeedStatements)).thenReturn("C");
        when(needStatement.getSubAreaLabel()).thenReturn("Receptive Communication");
        assertSame(sendController.doNeeds(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, VALID_SUB_AREA_ID), "needs"); 
        assertEquals(10, spyBindingAwareModelMap.size()); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("assessment", assessment); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("needStatements", needStatements);        
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("currentUser", owner); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("subAreaLabel", "Receptive Communication");
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("subAreaId", VALID_SUB_AREA_ID);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("selectedStatementIds", selectedNeedStatementIds);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("selectedGroupIds", selectedGroupIds);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("maximumNeedLevel", "C");
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("hasSelectedProvisions", Boolean.FALSE);
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("pathPrefix", PATH_PREFIX); 
    }

    @Test
    public void whenDoNeeds_cannotGetNeeds_logsError_returnsFailure() throws Exception {
        setUpLogAndServices(); 
        when(assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, SELECTED_NEED_STATEMENT_IDS, VALID_SUB_AREA_ID, Boolean.FALSE)).thenReturn(assessment);
        when(needService.getNeedStatementsForSubArea(VALID_SUB_AREA_ID, Boolean.FALSE)).thenThrow(new Exception());        
       
        assertThrows(SendException.class, () -> {
        	assertSame(sendController.doNeeds(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, VALID_SUB_AREA_ID), "sendfailure"); 
            verify(log).error("Unable to get needs from database: null");      
        });
    }

    @Test
    public void whenUpdateNeeds_cannotUpdateAssessment_logsError() throws Exception{
        setUpLogAndServices();          
        when(assessmentService.updateAssessmentWithSelectedNeedStatements(any(), any(), any(), any())).thenThrow(new Exception("Could not update assessment with selected needs"));           
        assertThrows(SendException.class, () -> {
        	sendController.updateNeeds(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, SELECTED_NEED_STATEMENT_IDS, VALID_SUB_AREA_ID, Boolean.FALSE);
            verify(log).error("Unable to update needs in database: Could not update assessment with selected needs");        
        });
    }    
    
    @Test
    public void whenDownloadPDF_setsUpAssessmentWithNeeds_returnsValidResponse() throws Exception {
        setUpLogAndServices();               
        MockHttpServletResponse response = new MockHttpServletResponse();
                
		Map<String, Object> pdfModel = new HashMap<>();
		pdfModel.put("ownerName", assessment.getOwner());
		pdfModel.put("needStatements", assessment.getSelectedNeedStatements());
		pdfModel.put("yourAssessment", assessment);
		pdfModel.put("provisions", assessment.getSelectedProvisions());
		
        sendController.downloadPDF(mockHttpServletRequest, response, VALID_ASSESSMENT_ID); 
        
        assertEquals(200, response.getStatus());
        assertEquals(0, spyBindingAwareModelMap.size()); 
      
    }
    
    @Test
    public void whenDownloadPDF_cannotDownloadPDF_logsError() throws Exception{
        setUpLogAndServices();   
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(pdfService.savePdfWithFooter(any(), any(), any(), any())).thenThrow(new Exception("Could not Save PDF"));
        assertThrows(SendPDFDownloadException.class, () -> {
        	 sendController.downloadPDF(mockHttpServletRequest, response, VALID_ASSESSMENT_ID); 
        	 verify(log).error("Unable to create pdf: Could not Save PDF");
        });
              
               
    }
    
    @Test
    public void whenDeleteAssessment_cannotDeleteAssessment_throwsSendException() throws Exception{
        setUpLogAndServices();   
        when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenThrow(new Exception());
        assertThrows(SendException.class, () -> {
        	 sendController.deleteAssessment(mockHttpServletRequest, bindingAwareModelMap, VALID_ASSESSMENT_ID) ;        	
        });   
    }
    
    
    @Test
    public void whenDeleteAssessment_DeletedAssessment_returnsHome() throws Exception{
        setUpLogAndServices();   
        assertSame(sendController.deleteAssessment(mockHttpServletRequest, bindingAwareModelMap, VALID_ASSESSMENT_ID), "home");      
    }
    
    @Test
    public void whenOverview_findsAssessments_returnsOverview() throws Exception{        
        setUpLogAndServices();        
        assertSame(sendController.overview(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, "two"), "overview"); 
        assertEquals(2, spyBindingAwareModelMap.size()); 
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("assessment", assessment);        
        Mockito.verify(spyBindingAwareModelMap, Mockito.atLeastOnce()).addAttribute("pathPrefix", PATH_PREFIX); 
    }
    
    @Test
    public void whenOverview_cannotFindAssessment_returnsFailure() throws Exception{
        sendController.setLogger(log);               
        when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenThrow(new Exception());        
        
        assertThrows(SendException.class, () -> {
        	assertSame(sendController.singleAssessment(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID), "sendfailure"); 
        	 
        });
        
    }
    
    @Test
    public void whenOverview_invalidPhase_returnsError() throws Exception{        
        setUpLogAndServices();        
        assertSame(sendController.overview(mockHttpServletRequest, spyBindingAwareModelMap, VALID_ASSESSMENT_ID, "one"), "error");        
    }

    public void setUpLogAndServices() throws Exception{
        // Stubbing for tests where the Ids are all valid. 
        // Use this stub for all tests then override where a different is required for invalid data.
        sendController.setLogger(log);
        spyBindingAwareModelMap.clear();
        when(assessment.getOwner()).thenReturn(OWNER_EMAIL);
        when(assessment.getSelectedProvisions()).thenReturn(listSelectedProvisions);
        when(authenticationService.getLoggedInUser(mockHttpServletRequest)).thenReturn(owner);
        when(owner.getOwnerEmail()).thenReturn(OWNER_EMAIL);
        when(assessmentService.getAssessmentByIdForOwner(VALID_ASSESSMENT_ID, OWNER_EMAIL)).thenReturn(assessment);
        when(assessmentService.getAssessmentsByOwner(OWNER_EMAIL)).thenReturn(listAssessments); 
        when(assessmentService.checkUpnPattern(VALID_UPN)).thenReturn(true);
        when(assessmentService.isUpnAvailable(VALID_UPN)).thenReturn(true);
        when(assessmentService.getSelectedNeedStatementsForSubArea(VALID_ASSESSMENT_ID , VALID_SUB_AREA_ID)).thenReturn(selectedNeedStatements);
        when(assessmentService.getSelectedNeedStatementIdsForSubArea(VALID_ASSESSMENT_ID , VALID_SUB_AREA_ID)).thenReturn(selectedNeedStatementIds);
        when(assessmentService.getSelectedGroupIdsForSubArea(VALID_ASSESSMENT_ID , VALID_SUB_AREA_ID)).thenReturn(selectedGroupIds);
        when(assessmentService.getSelectedProvisionsForSubArea(VALID_SUB_AREA_ID, assessment)).thenReturn(listSelectedProvisions);
        when(assessmentService.getSelectedProvisionIdsForSubArea(VALID_SUB_AREA_ID, assessment)).thenReturn(SELECTED_PROVISION_IDS);
        when(assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, SELECTED_NEED_STATEMENT_IDS, VALID_SUB_AREA_ID, Boolean.FALSE)).thenReturn(assessment);
        when(listNeeds.get(0)).thenReturn(need);
        when(need.getAreaLabel()).thenReturn(VALID_AREA_LABEL);
        when(need.getSubAreaLabel()).thenReturn(VALID_SUB_AREA_LABEL);
        when(needStatements.get(0)).thenReturn(needStatement);        
        when(needService.getNeedStatementsForSubArea(VALID_SUB_AREA_ID, Boolean.FALSE)).thenReturn(needStatements);  
        when(needService.getNeedsBySubAreaId(VALID_SUB_AREA_ID)).thenReturn(listNeeds);
        when(needService.getAllNeedAreas()).thenReturn(listNeeds);
        when(needService.getAllNeedSubAreas()).thenReturn(filteredListNeeds); 
        when(provisionService.getProvisionsForSubArea(VALID_SUB_AREA_ID, VALID_NEED_LEVEL)).thenReturn(arrayListProvisions);

    }

}
