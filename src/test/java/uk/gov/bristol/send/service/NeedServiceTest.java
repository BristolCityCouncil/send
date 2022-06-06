package uk.gov.bristol.send.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.repo.NeedsRepository;


@MockBean(NeedsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NeedServiceTest {
    
    private NeedService needService;

    private TestDataInitializer testDataInitializer;

    @Mock
    Need need;    

    @MockBean
    NeedsRepository needRepository;

    @MockBean
    SendUtilities sendUtilities;

    @Mock
    List<Need> listNeeds;

    @Mock
    List<NeedStatement> listNeedStatements;

    @Mock
    Iterable<NeedStatement> iterNeedStatements;

    @Mock
    Iterable<Need> iterNeeds;
    
    @Mock
    Iterator<Need> mockIterator;

    @Mock
    Optional<Need> optionNeeds;

    @Mock
    ArrayList<Need> arrayList;

    @BeforeAll
    public void setUp() {
        needService = new NeedService(needRepository, sendUtilities);
        testDataInitializer = new TestDataInitializer();
    }

    @AfterAll
    public void tearDown() throws Exception {
        needService = null;
        testDataInitializer = null;
    }


    @Test
    public void whenGetAllNeeds_needsFound_returnsArrayList() throws Exception {
        when(needRepository.findAll()).thenReturn(iterNeeds);
        when(iterNeeds.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableNeedsToList(iterNeeds)).thenReturn(listNeeds);
        when(listNeeds.size()).thenReturn(1);
        assertSame(needService.getAllNeeds(), listNeeds);
    }


    @Test
    public void whenGetAllNeeds_noNeedsFound_throwsException() throws Exception {
        when(needRepository.findAll()).thenReturn(iterNeeds);
        when(iterNeeds.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableNeedsToList(iterNeeds)).thenReturn(null);
        Exception thrown = assertThrows(Exception.class, () -> {
            needService.getAllNeeds();
        });
        assertTrue(thrown.getMessage().contains("No needs found"));                
    }


    @Test
    public void whenGetAllNeedSubAreas_subAreasFound_returnsArrayList() throws Exception {
        ArrayList<Need> needs = testDataInitializer.initNeeds();
        List<Need> needsFiltered;
        when(needRepository.findAll()).thenReturn(iterNeeds);
        when(iterNeeds.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableNeedsToList(iterNeeds)).thenReturn(needs);   
        needsFiltered = needService.getAllNeedSubAreas();
        assertSame(needsFiltered.size(), 3);  
        assertSame("firstSubAreaId", needsFiltered.get(0).getSubAreaId());
        assertSame("secondSubAreaId", needsFiltered.get(1).getSubAreaId());
        assertSame("thirdSubAreaId", needsFiltered.get(2).getSubAreaId());
    }


    @Test
    public void whenGetAllSubAreas_noSubAreasFound_throwsException() throws Exception {
        when(needRepository.findAll()).thenReturn(iterNeeds);
        when(iterNeeds.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableNeedsToList(iterNeeds)).thenReturn(null);   
        
        Exception thrown = assertThrows(Exception.class, () -> {
            needService.getAllNeedSubAreas();
        });
        assertTrue(thrown.getMessage().contains("No need sub areas found!"));                
    }


    @Test
    public void whenGetAllNeedAreas_areasFound_returnsArrayList() throws Exception {
        ArrayList<Need> needs = testDataInitializer.initNeeds();
        List<Need> needsFiltered;
        when(needRepository.findAll()).thenReturn(iterNeeds);
        when(iterNeeds.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableNeedsToList(iterNeeds)).thenReturn(needs);
        needsFiltered = needService.getAllNeedAreas();
        assertSame(needsFiltered.size(), 2);
        assertSame("firstAreaId", needsFiltered.get(0).getAreaId());
        assertSame("secondAreaId", needsFiltered.get(1).getAreaId());
    }


    @Test
    public void whenGetAllAreas_noAreasFound_throwsException() throws Exception {
        ArrayList<Need> emptyNeeds = new ArrayList<Need>();
        when(needRepository.findAll()).thenReturn(iterNeeds);
        when(iterNeeds.iterator()).thenReturn(mockIterator);
        when(sendUtilities.iterableNeedsToList(iterNeeds)).thenReturn(emptyNeeds);
        Exception thrown = assertThrows(Exception.class, () -> {
            needService.getAllNeedAreas();
        });
        assertTrue(thrown.getMessage().contains("No need areas found!"));                
    }

    @Test
    public void whenGetNeedStatementsForSubArea_statementsFound_returnsArrayList() throws Exception {
        List<NeedStatement> needStatements = testDataInitializer.initNeedStatements();        
        //Take out the need statements not of the first sub area
        needStatements.remove(1);
        needStatements.remove(2);
        when(needRepository.findStatementsForSubArea("firstSubAreaId")).thenReturn(listNeedStatements);
        when(sendUtilities.iterableStatementsToList(listNeedStatements)).thenReturn(needStatements);
        needStatements = needService.getNeedStatementsForSubArea("firstSubAreaId", Boolean.FALSE);
        assertSame(needStatements.size(), 3);
        assertSame("A1SA1SG1STA1", needStatements.get(0).getStatementNumber());
        assertSame("A1SA1SG1STA2", needStatements.get(1).getStatementNumber()); 
        assertSame("A1SA1SG1STC1", needStatements.get(2).getStatementNumber()); 
    }


    @Test
    public void whenGetNeedStatementsForSubArea_noStatementsFound_throwsException() throws Exception {
        ArrayList<NeedStatement> emptyNeedStatements = new ArrayList<NeedStatement>();
        when(needRepository.findStatementsForSubArea("subAreaId")).thenReturn(emptyNeedStatements);
        when(sendUtilities.iterableStatementsToList(iterNeedStatements)).thenReturn(emptyNeedStatements);
        Exception thrown = assertThrows(Exception.class, () -> {
            needService.getNeedStatementsForSubArea("subAreaId", Boolean.FALSE);
        });
        assertTrue(thrown.getMessage().contains("Test query failed!"));   
    }

    @Test
    public void whenFindCurrentNeedLevel_findsCorrectLevel() {
        ArrayList<NeedStatement> needStatements = testDataInitializer.initNeedStatements();        
        
        // leave the highest level as C
        assertSame(needService.findCurrentNeedLevel(needStatements), "C");       

        // set the highest level as B
        NeedStatement ns = needStatements.get(4);        
        ns.setStatementLevel("a");
        needStatements.set(4, ns);        
        assertSame(needService.findCurrentNeedLevel(needStatements), "B");       

        // set the highest level as A
        ns = needStatements.get(2);
        ns.setStatementLevel("a");
        needStatements.set(2, ns); 
        ns = needStatements.get(3);
        ns.setStatementLevel("a");
        needStatements.set(3, ns); 
        assertSame(needService.findCurrentNeedLevel(needStatements), "A");      
    }

    @Test
    public void whenFindNeedLevelForSubAreas_findsCorrectLevel() {
        ArrayList<NeedStatement> needStatements = testDataInitializer.initNeedStatements();  
        Map<String, String> needLevels = needService.findNeedLevelForSubAreas(needStatements);
        assertSame(needLevels.size(), 2);
        assertSame(needLevels.get("firstSubAreaId"), "C");        
        assertSame(needLevels.get("secondSubAreaId"), "B");
    }  
    
    @Test
    public void whenGetSelectedNeedStatementsGroupByAreaId_returnsCorrectGroupedList() {
		List<NeedStatement> needStatements = new ArrayList<NeedStatement>();
		NeedStatement ns1 = new NeedStatement("areaIdOne", "areaLabelOne", "firstSubAreaId", "first sub area label", "firstCategoryId", "first category label","A1SA1SG1", "A1SA1SG1STA1", "first statement label", "a");
		NeedStatement ns2 = new NeedStatement("areaIdOne", "areaLabelOne", "secondSubAreaId", "second sub area label", "firstCategoryId", "first category label","A1SA1SG1", "A1SA1SG1STA2", "third statement label", "b");
	    NeedStatement ns3 = new NeedStatement("areaIdTwo", "areaLabelTwo", "thirdSubAreaId", "third sub area label", "fourthCategoryId", "fourth category label","A1SA2SG1", "A1SA2SG1STB3", "second statement label", "a"); 
	    NeedStatement ns4 = new NeedStatement("areaIdOne", "areaLabelOne", "fourthSubAreaId", "fourth sub area label", "fourthCategoryId", "fourth category label","A1SA2SG1", "A1SA2SG1STB3", "second statement label", "a");
	     
	    needStatements.add(ns1);
	    needStatements.add(ns3);
	    needStatements.add(ns2);
	    needStatements.add(ns4);
	    
        needStatements = needService.getSelectedNeedStatementsGroupByAreaId(needStatements);
        
        assertSame("areaLabelOne", needStatements.get(2).getareaLabel());
        assertSame("areaLabelOne", needStatements.get(3).getareaLabel());
     
    }    
 
}