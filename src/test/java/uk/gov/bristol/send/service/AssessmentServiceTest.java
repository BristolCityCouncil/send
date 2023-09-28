package uk.gov.bristol.send.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.SendUtilities;
import uk.gov.bristol.send.TestDataInitializer;
import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUploadResponse;
import uk.gov.bristol.send.fileupload.controller.UploadTestDataInitializer;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.ProvisionCodesLookUp;
import uk.gov.bristol.send.model.SelectedProvision;
import uk.gov.bristol.send.repo.AssessmentsRepository;

@MockBean(AssessmentsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssessmentServiceTest {

    private AssessmentService assessmentService;

    private TestDataInitializer testDataInitializer;
    
    private UploadTestDataInitializer uploadTestDataInitializer;

    private final String VALID_UPN = "A123456789012B";

    private final String OWNER_EMAIL = "tinatestington@bristol.gov.uk";

    private final String ASSESSMENT_ID = "4d671fb1-88fe-4c88-85f8-9d6a4ed224b4";

    private final String SUB_AREA_ID_TWO = "secondSubAreaId";
    private final String SUB_AREA_ID_ONE = "firstSubAreaId";
    private final String PROVISION_STATEMENT_ID = "PGID1_PTID1_PS1";

    private final String WRONG_SUB_AREA_ID = "wrongSubAreaId";

    @Mock
    Assessment assessment;

    @MockBean
    AssessmentsRepository assessmentsRepository;

    @MockBean
    NeedService needService;
    
    @MockBean
    ProvisionService provisionService;

    @MockBean
    SendUtilities sendUtilities;

    @Mock
    NeedStatement mockNeedStatement;

    @Mock
    ArrayList<Assessment> arrayList;

    @Mock
    ArrayList<Assessment> filteredArrayList;

    @Mock
    Iterable<Assessment> iterAssessments;

    @Mock
    List<Assessment> listAssessments;

    @Mock
    List<Assessment> listAssessments2;

    @Mock
    Iterable<NeedStatement> iterNeedStatements;

    @Mock
    List<NeedStatement> listNeedStatements;

    @Mock
    List<Assessment> emptyList;

    @Mock
    Iterator<Assessment> mockIterator;


    @BeforeAll
    public void setUp() {
        assessmentService = new AssessmentService(assessmentsRepository, sendUtilities, needService, provisionService);
        testDataInitializer = new TestDataInitializer();
        uploadTestDataInitializer = new UploadTestDataInitializer();
        when(emptyList.size()).thenReturn(0);
    }

    @AfterAll
    public void tearDown() throws Exception {
        assessmentService = null;
        testDataInitializer = null;
        sendUtilities = null;
        uploadTestDataInitializer = null;
    }

    @Test
    public void whenGetAllAssessments_assessmentsFound_returnsArrayList() throws Exception {
        when(assessmentsRepository.findAll()).thenReturn(iterAssessments);
        when(iterAssessments.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableAssessmentsToList(iterAssessments)).thenReturn(arrayList);
        when(arrayList.size()).thenReturn(1);
        assertSame(assessmentService.getAllAssessments(), arrayList);
    }

    @Test
    public void whenGetAllAssessments_noAssessmentsFound_throwsException() throws Exception {
        when(assessmentsRepository.findAll()).thenReturn(iterAssessments);        
        when(iterAssessments.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableAssessmentsToList(iterAssessments)).thenReturn(arrayList);
        Exception thrown = assertThrows(Exception.class, () -> {
            assessmentService.getAllAssessments();
        });
        assertTrue(thrown.getMessage().contains("No assessments found"));    
    }


    @Test
    public void whenGetAssessmentsByOwner_assessmentsFound_returnsArrayList() throws Exception {
        List<Assessment> assessments = testDataInitializer.initAssessments();
        assessments.remove(2);
        when(assessmentsRepository.findByOwnerOrdered(OWNER_EMAIL)).thenReturn(assessments);                
        when(sendUtilities.iterableAssessmentsToList(assessments)).thenReturn(assessments);
        List<Assessment> filteredList = assessmentService.getAssessmentsByOwner(OWNER_EMAIL);
        assertSame(filteredList.size(), 2);
        assertSame(OWNER_EMAIL, filteredList.get(0).getOwner()); 
        assertSame(OWNER_EMAIL, filteredList.get(1).getOwner()); 
    }


    @Test
    public void whenGetAssessmentsByOwner_noAssessmentsFound_returnsEmptyList() {
        when(assessmentsRepository.findByOwnerOrdered(OWNER_EMAIL)).thenReturn(listAssessments);
                
        // Test a null list
        when(sendUtilities.iterableAssessmentsToList(listAssessments)).thenReturn(null);
        assert (assessmentService.getAssessmentsByOwner(OWNER_EMAIL)).isEmpty();

        // Test a list with zero elements
        when(sendUtilities.iterableAssessmentsToList(listAssessments)).thenReturn(emptyList);
        assert (assessmentService.getAssessmentsByOwner(OWNER_EMAIL)).isEmpty();
    }


    @Test
    public void whenGetAssessmentsByUPN_assessmentsFound_returnsArrayList() throws Exception {
        List<Assessment> assessments = testDataInitializer.initAssessments();
        assessments.remove(1);
        assessments.remove(1);
        when(assessmentsRepository.findByUpn(VALID_UPN)).thenReturn(assessments);    
        when(sendUtilities.iterableAssessmentsToList(assessments)).thenReturn(assessments);      
        List<Assessment> filteredList = assessmentService.getAssessmentsByUPN(VALID_UPN);
        assertSame(filteredList.size(), 1);
        assertSame(VALID_UPN, filteredList.get(0).getUpn());        
    }
    

    @Test
    public void whenGetAssessmentsByUPN_assessmentsNotFound_throwsException() throws Exception {
        when(assessmentsRepository.findByUpn(VALID_UPN)).thenReturn(emptyList);
        when(sendUtilities.iterableAssessmentsToList(emptyList)).thenReturn(emptyList);              
        Exception thrown = assertThrows(Exception.class, () -> {
            assessmentService.getAssessmentsByUPN(VALID_UPN);
        });
        assertTrue(thrown.getMessage().contains("No assessments found for given upn: " + VALID_UPN));    
    }


    @Test
    public void whenIsUpnAvailable_FindsIfUpnInUse() throws Exception {
        when(assessmentsRepository.findByUpn(VALID_UPN)).thenReturn(listAssessments);
        when(listAssessments.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(false);        
        assertSame(assessmentService.isUpnAvailable(VALID_UPN), true);
        // now if Upn is already taken
        Mockito.when(mockIterator.hasNext()).thenReturn(true,false);        
        assertSame(assessmentService.isUpnAvailable(VALID_UPN), false);
    }
   

    @Test
    public void whenGetAssessmentByIdForOwner_assessmentFound_returnsAssessment() throws Exception {
        String id = "2";
        Assessment assessment = testDataInitializer.initAssessments().get(1);
        when(assessmentsRepository.getByIdForOwner(id, OWNER_EMAIL)).thenReturn(listAssessments);
        when(sendUtilities.iterableAssessmentsToList(listAssessments)).thenReturn(listAssessments2);     
        when(listAssessments2.size()).thenReturn(1);
        when(listAssessments2.get(0)).thenReturn(assessment);
        assertSame(assessmentService.getAssessmentByIdForOwner(id, OWNER_EMAIL), assessment);
    }

    @Test
    public void whenGetAssessmentByIdForOwner_assessmentNotFound_throwsException() throws Exception {
        String id = "6060842";
        when(assessmentsRepository.getByIdForOwner(id, OWNER_EMAIL)).thenReturn(listAssessments);
        when(sendUtilities.iterableAssessmentsToList(listAssessments)).thenReturn(listAssessments2);     
        when(listAssessments2.size()).thenReturn(0);        
        Exception thrown = assertThrows(Exception.class, () -> {
            assessmentService.getAssessmentByIdForOwner(id, OWNER_EMAIL);
        });
        assertTrue(thrown.getMessage().contains("No single assessment exists for given id: " + id + " and owner: " + OWNER_EMAIL));    
    }

    @Test
    public void whenCreateNewAssessment_AssessmentCreated_returnsSameAssessment() throws Exception {
        when(assessmentsRepository.save(assessment)).thenReturn(assessment);
        assertSame(assessmentService.createNewAssessment(assessment), assessment);
    }

    @Test
    public void whenCreateNewAssessment_AssessmentNotCreated_throwsException() throws Exception {
        when(assessmentsRepository.save(assessment)).thenReturn(null);        
        Exception thrown = assertThrows(Exception.class, () -> {
            assessmentService.createNewAssessment(assessment);
        });
        assertTrue(thrown.getMessage().contains("Could not create new assessment"));
    }


    @Test
    public void whenGetSelectedNeedStatements_needStatementsNotFound_returnsEmptyList(){
        when(assessmentsRepository.getSelectedNeedStatements(ASSESSMENT_ID)).thenReturn(listNeedStatements);
        when(sendUtilities.iterableStatementsToList(listNeedStatements)).thenReturn(listNeedStatements);
        assert(assessmentService.getSelectedNeedStatements(ASSESSMENT_ID).size()) == 0;          
    } 

    @Test
    public void whenGetSelectedNeedStatementsForSubArea_needStatementsFound_returnsList(){
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();
        String subAreaId = selectedNeedStatements.get(0).getSubAreaId(); 
        NeedStatement selectedStatement = selectedNeedStatements.get(0);               
        when(assessmentsRepository.getSelectedNeedStatements(ASSESSMENT_ID)).thenReturn(selectedNeedStatements);
        when(sendUtilities.iterableStatementsToList(selectedNeedStatements)).thenReturn(selectedNeedStatements);        
        assert(assessmentService.getSelectedNeedStatementsForSubArea(ASSESSMENT_ID, subAreaId)).contains(selectedStatement);
    }

    @Test
    public void whenGetSelectedNeedStatementsForSubArea_needStatementsNotFound_returnsEmptyList(){
        when(assessmentsRepository.getSelectedNeedStatements(ASSESSMENT_ID)).thenReturn(listNeedStatements);
        when(sendUtilities.iterableStatementsToList(listNeedStatements)).thenReturn(listNeedStatements);
        assert(assessmentService.getSelectedNeedStatementsForSubArea(ASSESSMENT_ID, WRONG_SUB_AREA_ID).size()) == 0;          
    } 


    @Test
    public void whenGetSelectedNeedStatements_needStatementsFound_returnsList(){
        when(assessmentsRepository.getSelectedNeedStatements(ASSESSMENT_ID)).thenReturn(listNeedStatements);
        when(sendUtilities.iterableStatementsToList(listNeedStatements)).thenReturn(listNeedStatements);
        when(listNeedStatements.size()).thenReturn(2);
        assert(assessmentService.getSelectedNeedStatements(ASSESSMENT_ID)).equals(listNeedStatements);
    }
    
    @Test
    public void whenGetSelectedGroupIdsForSubArea_selectedStatementsFound_returnsGroupIdList(){
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();
        String expectedGroupNumber = selectedNeedStatements.get(1).getStatementGroupNumber();
        when(assessmentsRepository.getSelectedNeedStatements(ASSESSMENT_ID)).thenReturn(listNeedStatements);
        when(sendUtilities.iterableStatementsToList(listNeedStatements)).thenReturn(selectedNeedStatements);      
        assert(assessmentService.getSelectedGroupIdsForSubArea(ASSESSMENT_ID, SUB_AREA_ID_TWO)).contains(expectedGroupNumber);
    }

    @Test
    public void whenGetSelectedGroupIdsForSubArea_noSelectedStatementsFound_returnsGroupIdList(){
        when(assessmentsRepository.getSelectedNeedStatements(ASSESSMENT_ID)).thenReturn(Collections.emptyList());
        when(sendUtilities.iterableStatementsToList(listNeedStatements)).thenReturn(Collections.emptyList());      
        assert(assessmentService.getSelectedGroupIdsForSubArea(ASSESSMENT_ID, WRONG_SUB_AREA_ID)).isEmpty();
    }


    @Test
    public void whenUpdateAssessmentWithSelectedNeedStatements_updatesAssessment_withoutDuplicates() throws Exception{
        //setup an assessment with one selected need statement
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();    
           
        List<NeedStatement> ns1 = Arrays.asList(selectedNeedStatements.get(0));
        List<NeedStatement> ns2 = Arrays.asList(selectedNeedStatements.get(1));
        List<NeedStatement> ns3 = Arrays.asList(selectedNeedStatements.get(2));
        List<NeedStatement> ns4 = Arrays.asList(selectedNeedStatements.get(3));
        List<NeedStatement> ns5 = Arrays.asList(selectedNeedStatements.get(4));

        when(needService.getNeedStatementById(ns1.get(0).getStatementNumber())).thenReturn(ns1);
        when(needService.getNeedStatementById(ns2.get(0).getStatementNumber())).thenReturn(ns2);
        when(needService.getNeedStatementById(ns3.get(0).getStatementNumber())).thenReturn(ns3); 
        when(needService.getNeedStatementById(ns4.get(0).getStatementNumber())).thenReturn(ns4); 
        when(needService.getNeedStatementById(ns5.get(0).getStatementNumber())).thenReturn(ns5);        

        //Create a new selected ids list. Note, selectedIds come from a needs page, so are only for one subarea, in this case the secondSubAreaId
        String selectedIds = selectedNeedStatements.get(1).getStatementNumber() + "," + selectedNeedStatements.get(3).getStatementNumber() + ",";

        // remove statements ns1 and ns2 before setting the remaining three as existing selected statements in the assessment
        selectedNeedStatements.remove(0);
        selectedNeedStatements.remove(0);
        assessments.get(0).setSelectedNeedStatements(selectedNeedStatements);

        // setup the id that might wrongly be repeated
        String repeatedId = selectedNeedStatements.get(1).getStatementNumber();
        String subAreaId = selectedNeedStatements.get(1).getSubAreaId(); 

        Assessment assessment = assessments.get(0); 
        when(assessmentsRepository.save(assessment)).thenReturn(assessment);    
        
        // Now update the assessment, adding one statement to the existing list of 3x statements 
        Assessment updatedAssessment = assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, selectedIds, subAreaId, Boolean.FALSE);
        assert(updatedAssessment.getSelectedNeedStatements()).size() == 4;

        // check the assessment now contains one each of four need statements
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns2.get(0));
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns3.get(0));
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns4.get(0));
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns5.get(0));
       
        // check the assessment does not contain the previously existing id more than once
        List<NeedStatement> newNeedStatements = updatedAssessment.getSelectedNeedStatements();
        int count = 0;
        for(int i=0; i<newNeedStatements.size(); i++){
            if(newNeedStatements.get(i).getStatementNumber().equals(repeatedId)){
                count = count+1;
            }    
        }
        assert(count) == 1;
          
    }


    @Test
    public void whenUpdateAssessmentWithSelectedNeedStatements_needsDeselected_updatesAssessment() throws Exception{
        //setup an assessment with one selected need statement
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();        
           
        List<NeedStatement> ns1 = Arrays.asList(selectedNeedStatements.get(0));
        List<NeedStatement> ns2 = Arrays.asList(selectedNeedStatements.get(1));
        List<NeedStatement> ns3 = Arrays.asList(selectedNeedStatements.get(2));
        List<NeedStatement> ns4 = Arrays.asList(selectedNeedStatements.get(3));
        List<NeedStatement> ns5 = Arrays.asList(selectedNeedStatements.get(4));

        when(needService.getNeedStatementById(ns1.get(0).getStatementNumber())).thenReturn(ns1);
        when(needService.getNeedStatementById(ns2.get(0).getStatementNumber())).thenReturn(ns2);
        when(needService.getNeedStatementById(ns3.get(0).getStatementNumber())).thenReturn(ns3); 
        when(needService.getNeedStatementById(ns4.get(0).getStatementNumber())).thenReturn(ns4); 
        when(needService.getNeedStatementById(ns5.get(0).getStatementNumber())).thenReturn(ns5);         

        //Create an empty selected ids list
        String selectedIds = "-1";
        assessments.get(0).setSelectedNeedStatements(selectedNeedStatements);        
        String subAreaId = selectedNeedStatements.get(1).getSubAreaId();  // use the subAreaId of ns1 and ns3, secondSubAreaId     
        Assessment assessment = assessments.get(0); 
        when(assessmentsRepository.save(assessment)).thenReturn(assessment);       
        
        Assessment updatedAssessment = assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, selectedIds, subAreaId, Boolean.FALSE);
        // check the assessment now contains no need statements with secondSubAreaId, but the other three remain
        assert(updatedAssessment.getSelectedNeedStatements()).size() == 3;        
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns1.get(0));
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns3.get(0));
        assert(updatedAssessment.getSelectedNeedStatements()).contains(ns5.get(0));
        assertFalse((updatedAssessment.getSelectedNeedStatements()).contains(ns2.get(0)));
        assertFalse((updatedAssessment.getSelectedNeedStatements()).contains(ns4.get(0)));         
    }    
    
    @Test
    public void whenUpdateAssessmentWithSelectedNeedStatements_deleteProvision_provisionDeleted() throws Exception{
        //setup an assessment with one selected need statement
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();  
        ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
           
        List<NeedStatement> ns1 = Arrays.asList(selectedNeedStatements.get(0));
       

        when(needService.getNeedStatementById(ns1.get(0).getStatementNumber())).thenReturn(ns1);                
        
        Provision provision1 =  provisions.get(0);        
        Assessment assessment = assessments.get(0);
        
        List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();       
        selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
       		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));       
        assessment.setSelectedProvisions(selectedProvisions);
             
        //Create an empty selected ids list
        String selectedIds = "-1";
        assessments.get(0).setSelectedNeedStatements(selectedNeedStatements);        
       
        when(assessmentsRepository.save(assessment)).thenReturn(assessment);       
        
        Assessment updatedAssessment = assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, selectedIds, SUB_AREA_ID_ONE, Boolean.TRUE);
        assert(updatedAssessment.getSelectedProvisions().size() == 0);
       
    }    
    
    
    @Test
    public void whenUpdateAssessmentWithSelectedNeedStatements_keepProvision_provisionNotDeleted() throws Exception{
        //setup an assessment with one selected need statement
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();  
        ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
           
        List<NeedStatement> ns1 = Arrays.asList(selectedNeedStatements.get(0));
       

        when(needService.getNeedStatementById(ns1.get(0).getStatementNumber())).thenReturn(ns1);                
        
        Provision provision1 =  provisions.get(0);        
        Assessment assessment = assessments.get(0);
        
        List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();       
        selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
       		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));       
        assessment.setSelectedProvisions(selectedProvisions);
             
        //Create an empty selected ids list
        String selectedIds = "-1";
        assessments.get(0).setSelectedNeedStatements(selectedNeedStatements);        
       
        when(assessmentsRepository.save(assessment)).thenReturn(assessment);       
        
        Assessment updatedAssessment = assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, selectedIds, SUB_AREA_ID_ONE, Boolean.FALSE);
        assert(updatedAssessment.getSelectedProvisions().size() == 1);
       
    }    

    
    @Test
    public void whenUpdateAssessmentWithSelectedNeedStatements_statementNumberInvalid_throwsException() throws Exception{
        //setup an assessment with one selected need statement
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<NeedStatement> selectedNeedStatements = testDataInitializer.initNeedStatements();        
        String subAreaId = selectedNeedStatements.get(0).getSubAreaId();
        Assessment assessment = assessments.get(0);    
        NeedStatement needStatement = selectedNeedStatements.get(0);

        // add some need statements and check they are all there, without duplicating the existing one
        selectedNeedStatements = testDataInitializer.initNeedStatements(); 
        when(needService.getNeedStatementById(needStatement.getStatementNumber())).thenReturn(listNeedStatements);

        Exception thrown = assertThrows(Exception.class, () -> {
            assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, "A1SA1SG3STC1, A1SA1SG3STC2", subAreaId, Boolean.FALSE);
        });
        assertTrue(thrown.getMessage().contains("Need Statement with id: " + "A1SA1SG3STC1" + " either does not exist or has more than one entry in the database"));
    }
    
   
    @Test
    public void whenAreProvisionsSelectedForSubArea_selectedProvision_returnsTrue(){     
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
        
        Provision provision1 =  provisions.get(0);        
        Assessment assessment = assessments.get(0);
        
        List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();       
        selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
       		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));       
        assessment.setSelectedProvisions(selectedProvisions);
        
        boolean hasSelectedProvisions = assessmentService.areProvisionsSelectedForSubArea(SUB_AREA_ID_ONE, assessment);
        assertTrue(hasSelectedProvisions);       
    }
    
    @Test
    public void whenAreProvisionsSelectedForSubArea_NoSelectedProvision_returnsFalse(){     
        List<Assessment> assessments = testDataInitializer.initAssessments();
        ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
        
        Provision provision1 =  provisions.get(0);        
        Assessment assessment = assessments.get(0);
        
        List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();       
        selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
       		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));       
        assessment.setSelectedProvisions(selectedProvisions);
        
        boolean hasSelectedProvisions = assessmentService.areProvisionsSelectedForSubArea(SUB_AREA_ID_TWO, assessment);
        assertFalse(hasSelectedProvisions);
    }
    
    @Test
    public void whenUpdateAssessmentWithRemovedProvision_DifferentSubAreaAndStatementSelected_updatesAssessment() throws Exception{
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(1);
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision2.getLevel(),
        		 provision2.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
    					 
         assessment.setSelectedProvisions(selectedProvisions);
                  
         when(assessmentsRepository.save(assessment)).thenReturn(assessment);     
         
         Assessment updatedAssessment = assessmentService.updateAssessmentWithRemovedProvisions(assessment,SUB_AREA_ID_TWO,PROVISION_STATEMENT_ID);
         
         // Check the assessment, as this now contains only one selected Provision as the other selected provision with matching SubArea and statement is now removed
         assert(updatedAssessment.getSelectedProvisions()).size() == 1;        
      
    }
    
    @Test
    public void whenUpdateAssessmentWithRemovedProvision_SameSubAreaButDifferentStatementSelected_updatesAssessment() throws Exception{
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(1);
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision2.getLevel(),
        		 provision2.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
    					 
         assessment.setSelectedProvisions(selectedProvisions);
                  
         when(assessmentsRepository.save(assessment)).thenReturn(assessment);     
         
         Assessment updatedAssessment = assessmentService.updateAssessmentWithRemovedProvisions(assessment,SUB_AREA_ID_ONE,PROVISION_STATEMENT_ID);
         
         // Check the assessment, making sure Only one selected provision removed as other selected Provision though belong to the same subArea but different statement remain selected.
         assert(updatedAssessment.getSelectedProvisions()).size() == 1;        
      
    }   

    @Test
    public void whenUpdateAssessmentWithRemovedProvision_FailedUpdate_throwsException() throws Exception{    	             
         when(assessmentsRepository.save(assessment)).thenReturn(null);       
         Exception thrown = assertThrows(Exception.class, () -> {
             assessmentService.updateAssessmentWithRemovedProvisions(assessment,SUB_AREA_ID_ONE,PROVISION_STATEMENT_ID);
         });       
         
         assertTrue(thrown.getMessage().contains("Could not update assessment with removed provisions"));     
    }
    
    @Test
    public void whenGetSelectedProvisionsGroupByProvisionStatementId_returnsCorrectGroupedList() {
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(1);
         Provision provision3 =  provisions.get(2);
                 
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision3.getLevel(),
        		 provision3.getProvisionGroup(), provision3.getProvisionStatementId(),provision3.getSpecificProvision(),provision3.getProvisionTypeLabel()));        
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision2.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
        		 
         assessment.setSelectedProvisions(selectedProvisions);
         
         selectedProvisions = assessmentService.getSelectedProvisionsGroupByProvisionStatementId(assessment.getSelectedProvisions());               
		 //making sure though added in a different order, the selected provision are grouped together based on  the Provision Group
         assertSame("Supervision or coaching of staff by professionals.", selectedProvisions.get(1).getSpecificProvision());
         assertSame(SUB_AREA_ID_TWO, selectedProvisions.get(1).getSubAreaId());
            
    } 
    
    
    @Test
    public void whenGetSelectedProvisionsRequestingWithCheckedProvisions_returnsListRequestingForFunding() {
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(1);
         Provision provision3 =  provisions.get(2);
                 
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision3.getLevel(),
        		 provision3.getProvisionGroup(), provision3.getProvisionStatementId(),provision3.getSpecificProvision(),provision3.getProvisionTypeLabel()));        
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision2.getLevel(),
        		 provision1.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
         
         selectedProvisions.get(2).setRequestingFunding(true);
        		 
         assessment.setSelectedProvisions(selectedProvisions);
         
         selectedProvisions = assessmentService.getSelectedProvisionsRequestingForFunding(assessment.getSelectedProvisions());               		 
         assert(selectedProvisions).size() == 1;            
            
    } 
    
    @Test
    public void whenupdateAssessmentWithRequestingFundingProvisions__updatesAssessment() throws Exception{
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(1);
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision2.getLevel(),
        		 provision2.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
                  
         assessment.setSelectedProvisions(selectedProvisions);				 
        
                  
         when(assessmentsRepository.save(assessment)).thenReturn(assessment);   
         
         
         Assessment updatedAssessment = assessmentService.updateAssessmentWithRequestingFundingProvisions(assessment,assessment.getSelectedProvisions(),SUB_AREA_ID_ONE, PROVISION_STATEMENT_ID);
         
         // Check the updated assessment with matching SubArea and statement for Requesting Funding flag. Should be marked as  true for  matching and false for non matching
         assertTrue(updatedAssessment.getSelectedProvisions().get(0).isRequestingFunding());  
         assertFalse(updatedAssessment.getSelectedProvisions().get(1).isRequestingFunding());  
      
    }
    
    @Test
    public void whenCalculateTotalProvisionsAnnualCost_updatedTotalAnnualCost() throws Exception{
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
         ArrayList<ProvisionCodesLookUp> provisionCodesLookUp = testDataInitializer.initProvisionCodesLookUp();
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(1);        
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision2.getLevel(),
        		 provision2.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
                  
         assessment.setSelectedProvisions(selectedProvisions);		
         
         when(provisionService.getProvisionForStatement(provision1.getProvisionStatementId())).thenReturn(provision1); 
         when(provisionService.getProvisionForStatement(provision2.getProvisionStatementId())).thenReturn(provision2); 
         when(provisionService.getProvisionCodesLookUp(provision1.getCode())).thenReturn(provisionCodesLookUp.get(0));
         when(provisionService.getProvisionCodesLookUp(provision2.getCode())).thenReturn(provisionCodesLookUp.get(1));
         
         assessmentService.calculateTotalProvisionsAnnualCost(assessment,selectedProvisions);
                 
         assertEquals(1708.07, Double.parseDouble(assessment.getTotalAnnualCost()));  
              
    }
    
    @Test
    public void whenCalculateTotalProvisionsAnnualCost_TotalWeeklyHourCapExceeded_failedToUpdateTotalAnnualCost() throws Exception{
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
         ArrayList<ProvisionCodesLookUp> provisionCodesLookUp = testDataInitializer.initProvisionCodesLookUp();
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(2);        
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision2.getLevel(),
        		 provision2.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
                  
         assessment.setSelectedProvisions(selectedProvisions);		
         
         when(provisionService.getProvisionForStatement(provision1.getProvisionStatementId())).thenReturn(provision1); 
         when(provisionService.getProvisionForStatement(provision2.getProvisionStatementId())).thenReturn(provision2); 
         when(provisionService.getProvisionCodesLookUp(provision1.getCode())).thenReturn(provisionCodesLookUp.get(0));
         when(provisionService.getProvisionCodesLookUp(provision2.getCode())).thenReturn(provisionCodesLookUp.get(1));
         
         assessmentService.calculateTotalProvisionsAnnualCost(assessment,selectedProvisions);
         assertTrue(assessment.isTotalHourlyCapExceeded());
                 
         assertEquals(null, assessment.getTotalAnnualCost());  
              
    }
    @Test
    public void whenCalculateTotalWeeklyHourPerCodeForSelectedProvisions_WeeklyHourCapExceededForType_UpdateFlag() throws Exception{
    	 List<Assessment> assessments = testDataInitializer.initAssessments();
         ArrayList<Provision> provisions = testDataInitializer.initProvisions(); 
         ArrayList<ProvisionCodesLookUp> provisionCodesLookUp = testDataInitializer.initProvisionCodesLookUp();
                  
         List<SelectedProvision> selectedProvisions = new ArrayList<SelectedProvision>();  
         Assessment assessment = assessments.get(0);
         
         Provision provision1 =  provisions.get(0);
         Provision provision2 =  provisions.get(3);        
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_ONE, provision1.getLevel(),
        		 provision1.getProvisionGroup(), provision1.getProvisionStatementId(),provision1.getSpecificProvision(),provision1.getProvisionTypeLabel()));
         
         selectedProvisions.add(new SelectedProvision(SUB_AREA_ID_TWO, provision2.getLevel(),
        		 provision2.getProvisionGroup(), provision2.getProvisionStatementId(),provision2.getSpecificProvision(),provision2.getProvisionTypeLabel()));
                  
         assessment.setSelectedProvisions(selectedProvisions);		
         
         when(provisionService.getProvisionForStatement(provision1.getProvisionStatementId())).thenReturn(provision1); 
         when(provisionService.getProvisionForStatement(provision2.getProvisionStatementId())).thenReturn(provision2); 
         when(provisionService.getProvisionCodesLookUp(provision1.getCode())).thenReturn(provisionCodesLookUp.get(0));
         when(provisionService.getProvisionCodesLookUp(provision2.getCode())).thenReturn(provisionCodesLookUp.get(1));
         
         assessmentService.calculateTotalWeeklyHourPerCodeForSelectedProvisions(assessment,selectedProvisions);
                 
         assertTrue(assessment.isTypeHourlyCapExceeded());
              
    }
    
    @Test
	public void whenSaveUploadedFileInfo_validData_saveUploadedFileInfoToAssessment() throws Exception {
		List<Assessment> assessments = testDataInitializer.initAssessments();

		FileUpload fileUpload = uploadTestDataInitializer.initFileUpload();
		FileUploadResponse fileUploadResponse = uploadTestDataInitializer.initFileUploadSuccessResponse();

		Assessment assessment = assessments.get(0);
		assessmentService.saveUploadedFileInfo(assessment, fileUploadResponse, fileUpload);
		
		verify(assessmentsRepository, times(1)).save(assessment);
		assertTrue(assessment.getUploadedFilesInfo().size() == 1);
		
	}
    
    @Test
 	public void whenDeleteUploadedFileInfo_validData_deleteUploadedFileInfoFromAssessment() throws Exception {
 		List<Assessment> assessments = testDataInitializer.initAssessments();

 		FileUpload fileUpload = uploadTestDataInitializer.initFileUpload();
 		FileUploadResponse fileUploadResponse = uploadTestDataInitializer.initFileUploadSuccessResponse();
 		
		Assessment assessment = assessments.get(0);
		assessmentService.saveUploadedFileInfo(assessment, fileUploadResponse, fileUpload);
		
		UploadedFileInfo uploadedFileInfo1 = uploadTestDataInitializer.initUploadedFileInfo("101", "Whole school provision map (mandatory)", "Whole school provision map (mandatory)");
		UploadedFileInfo uploadedFileInfo2 = uploadTestDataInitializer.initUploadedFileInfo("102", "Application form (mandatory)", "Application form (mandatory).pdf");

		List<UploadedFileInfo> uploadedFiles = new ArrayList<UploadedFileInfo>();
		uploadedFiles.add(uploadedFileInfo1);
		uploadedFiles.add(uploadedFileInfo2);	
 		
 		assessmentService.deleteUploadedFileInfo(assessment, uploadedFiles, "101");
 		 		
 		verify(assessmentsRepository, times(2)).save(assessment);
 		assertTrue(assessment.getUploadedFilesInfo().size() == 1);
 		
 	}

    @Test
    public void whenCheckUpnPattern_Valid_returnsTrue() throws Exception {
        List<String> upns = new ArrayList<String>();
        upns.add("A111111111111A");
        upns.add("A111111111111");
        upns.add("Z111111111111Z");
        for (String upn : upns) {
            assertTrue(assessmentService.checkUpnPattern(upn));
        }
    }

    @Test
    public void whenCheckUpnPattern_Invalid_returnsFalse() throws Exception {
        List<String> upns = new ArrayList<String>();
        upns.add(null);
        upns.add("A");
        upns.add("A1111 11111111");
        upns.add("11111111111111");
        upns.add("AA111111111111");
        for (String upn : upns) {
            assertFalse(assessmentService.checkUpnPattern(upn));
        }
    }
    
    @Test
    public void whenDeleteAssessment_ValidAssessment_CallsDeleteOnRepo() throws Exception{    	                      
         assessmentService.deleteAssessment(assessment);
         verify(assessmentsRepository, times(1)).delete(assessment);
    }
    
    @Test
    public void whenSaveAssessment_ValidAssessment_CallsSaveOnRepo() throws Exception{    	                      
         assessmentService.saveAssessment(assessment);
         verify(assessmentsRepository, times(1)).save(assessment);
    }

}