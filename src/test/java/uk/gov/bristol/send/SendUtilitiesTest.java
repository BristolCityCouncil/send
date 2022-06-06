package uk.gov.bristol.send;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SendUtilitiesTest {

    private SendUtilities sendUtilities = new SendUtilities();

    
    @Test
    public void whenIterableNeedsToList_returnsOrderedList()  {
        Need need1 = new Need(1, 1, "firstAreaId", "first area label", 1, "firstSubAreaId", "first sub area label");
        Need need2 = new Need(1, 1, "firstAreaId", "first area label", 1, "secondSubAreaId", "second sub area label");
        need1.setId("2");
        need2.setId("1");
        Iterable<Need> needsIterable = Stream.of(need1, need2).collect(Collectors.toList());
        List<Need> needsList = sendUtilities.iterableNeedsToList(needsIterable);
        assertSame(need2, needsList.get(0));
        assertSame(need1, needsList.get(1));               
    }

    @Test
    public void whenIterableNeedStatementsToList_returnsList()  {
        NeedStatement ns1 = new NeedStatement("areaId", "areaLabel", "firstSubAreaId", "first sub area label", "firstCategoryId", "first category label","11", "1111", "first statement label", "a"); 
        NeedStatement ns2 = new NeedStatement("areaId", "areaLabel", "secondSubAreaId", "second sub area label", "fourthCategoryId", "fourth category label","23", "2311", "second statement label", "b"); 
        Iterable<NeedStatement> needStatementsIterable = Stream.of(ns1, ns2).collect(Collectors.toList());
        List<NeedStatement> needStatementsList = sendUtilities.iterableStatementsToList(needStatementsIterable); 
        assertSame(ns1, needStatementsList.get(0));       
        assertSame(ns2, needStatementsList.get(1));
    }
   
    @Test
    public void whenIterableAssessmentsToList_returnsList()  {        
        Assessment assessment1 = new Assessment();
        Assessment assessment2 = new Assessment();
        Iterable<Assessment> assessmentsIterable = Stream.of(assessment1, assessment2).collect(Collectors.toList());
        List<Assessment> assessmentsList = sendUtilities.iterableAssessmentsToList(assessmentsIterable); 
        assertSame(assessment1, assessmentsList.get(0));       
        assertSame(assessment2, assessmentsList.get(1));
    }
}
