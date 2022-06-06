package uk.gov.bristol.send.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mock;

import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.SendUtilities;
import uk.gov.bristol.send.TestDataInitializer;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.ProvisionLookUp;
import uk.gov.bristol.send.repo.ProvisionsRepository;
import uk.gov.bristol.send.repo.ProvisionsLookUpRepository;


@MockBean(ProvisionsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProvisionServiceTest {
  
    private ProvisionService provisionService;

    private TestDataInitializer testDataInitializer;

    private final String VALID_PROVISION_ID =  "PGID1_PTID1_PS1,PGID1_PTID2_PS3,PGID2_PTID9_PS1";

    private final String VALID_PROVISION_GROUPS =  "PGID1,PGID2";

    private final String VALID_SUBAREA_ID = "expressiveCommunication";

    private final String INVALID_SUBAREA_ID = "invalidSubAreaId";


    @Mock
    Provision provision;    

    @Mock
    ProvisionLookUp provisionLookUp;

    @MockBean
    ProvisionsRepository provisionsRepository;

    @MockBean
    ProvisionsLookUpRepository provisionsLookUpRepository;

    @MockBean
    SendUtilities sendUtilities;

    @Mock
    ArrayList<Provision> arrayListProvisions;

    @Mock
    List<Provision> listProvisions;

    @Mock
    Iterable<Provision> iterProvisions; 

    @Mock
    List<ProvisionLookUp> listProvisionsLookUp; 

    @Mock
    Iterator<Provision> mockIterator;


    @BeforeAll
    public void setUp() {
        provisionService = new ProvisionService(provisionsRepository, provisionsLookUpRepository, sendUtilities);
        testDataInitializer = new TestDataInitializer();
    }

    @AfterAll
    public void tearDown() throws Exception {
        provisionService = null;
        testDataInitializer = null;
    }


    @Test
    public void whenGetAllProvisions_provisionsFound_returnsArrayList() throws Exception {
         when(provisionsRepository.findAll()).thenReturn(listProvisions);
         when(listProvisions.size()).thenReturn(1);
         assertSame(provisionService.getAllProvisions(), listProvisions);
    }
    
    @Test
    public void whenGetAllProvisions_provisionsFound_returnsSortedArrayList() throws Exception {
    	ArrayList<Provision> provisions = testDataInitializer.initProvisions();
    	Provision firstProvision = provisions.get(2);
    	
    	provisions.get(0).setId("3");
    	provisions.get(1).setId("2");
    	provisions.get(2).setId("1");
    	provisions.get(3).setId("4");
    	
        when(provisionsRepository.findAll()).thenReturn(provisions);        
        assertSame(provisionService.getAllProvisions(), provisions);
        
        assertSame(firstProvision, provisions.get(0));
       
    }


    @Test
    public void whenGetAllProvisions_noProvisionsFound_throwsException() throws Exception {
         when(provisionsRepository.findAll()).thenReturn(listProvisions);
        
         Exception thrown = assertThrows(Exception.class, () -> {
             provisionService.getAllProvisions();
         });
         assertTrue(thrown.getMessage().contains("No provisions found"));                
    }


    @Test
    public void whenGetProvisionsForSubArea_provisionLevelInvalid_throwsException() throws Exception {
        ArrayList<Provision> provisions = testDataInitializer.initProvisions();  
        ArrayList<ProvisionLookUp> provisionsLookUp = testDataInitializer.initProvisionsLookUp();              
        when(provisionsLookUpRepository.findBySubAreaId(VALID_SUBAREA_ID)).thenReturn(provisionsLookUp);
        when(provisionsRepository.findAll()).thenReturn(provisions);        
                
        Exception thrown = assertThrows(Exception.class, () -> {
            provisionService.getProvisionsForSubArea(VALID_SUBAREA_ID, "_!");            
        });
        assertTrue(thrown.getMessage().contains("Unable to fetch provisions. The need level: _! is not valid"));   
    }


    @Test
    public void whenGetProvisionsForSubArea_provisionInvalid_throwsException() throws Exception {
        ArrayList<Provision> provisions = testDataInitializer.initProvisions();  
        ArrayList<ProvisionLookUp> provisionsLookUp = testDataInitializer.initProvisionsLookUp();
        String provisionGroupIdString = provisionsLookUp.get(0).getProvisionGroups();              
        // Alter data to cause the exception
        provisions.get(0).setProvisionTypeId(null);
        when(provisionsLookUpRepository.findBySubAreaId(VALID_SUBAREA_ID)).thenReturn(provisionsLookUp); 
        when(provisionsRepository.findAll()).thenReturn(provisions);                
        
        Exception thrown = assertThrows(NullPointerException.class, () -> {
            provisionService.getProvisionsForSubArea(VALID_SUBAREA_ID, "A");            
        });
        assertTrue(thrown.getMessage().contains("Unable to fetch provisions for sub area id: " + VALID_SUBAREA_ID + ". This string of group ids may be corrupted: " + provisionGroupIdString));   
    }
    

    @Test
    public void whenGetProvisionsForSubArea_invalidSubArea_throwsException() throws Exception {
        ArrayList<Provision> provisions = testDataInitializer.initProvisions();                                       
        when(provisionsLookUpRepository.findBySubAreaId(INVALID_SUBAREA_ID)).thenReturn(listProvisionsLookUp);
        when(provisionsRepository.findAll()).thenReturn(provisions);   
        when(listProvisionsLookUp.get(0)).thenReturn(provisionLookUp);
        when(provisionLookUp.getProvisionGroups()).thenReturn(VALID_PROVISION_GROUPS);
                
        Exception thrown = assertThrows(Exception.class, () -> {
            provisionService.getProvisionsForSubArea(INVALID_SUBAREA_ID, "A");            
        });
        assertTrue(thrown.getMessage().contains("No provisions found for given sub area id: " + INVALID_SUBAREA_ID));   
    }


    @Test
    public void whenGetProvisionsForSubArea_provisionsFound_returnsOnlyForSubAreaAndLevel() throws Exception {
        ArrayList<Provision> provisions = testDataInitializer.initProvisions();  
        ArrayList<ProvisionLookUp> provisionsLookUp = testDataInitializer.initProvisionsLookUp();
      
        when(provisionsLookUpRepository.findBySubAreaId(VALID_SUBAREA_ID)).thenReturn(provisionsLookUp); 
        when(provisionsRepository.findAll()).thenReturn(provisions);  

        // There should be 2 provisions for VALID_SUBAREA_ID returned by testDataInitializer.
        List<Provision> returnedProvisions = provisionService.getProvisionsForSubArea(VALID_SUBAREA_ID, "C"); 
        assertSame(returnedProvisions.size(), 2);

        // Should be 2 provisions for VALID_SUBAREA_ID and level B
        returnedProvisions = provisionService.getProvisionsForSubArea(VALID_SUBAREA_ID, "B"); 
        assertSame(returnedProvisions.size(), 2);        

        // Should be 1 provisions for VALID_SUBAREA_ID and level A
        returnedProvisions = provisionService.getProvisionsForSubArea(VALID_SUBAREA_ID, "A"); 
        assertSame(returnedProvisions.size(), 1);
    }    
    
}
