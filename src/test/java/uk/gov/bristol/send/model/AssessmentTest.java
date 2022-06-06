package uk.gov.bristol.send.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import uk.gov.bristol.send.model.Assessment;

public class AssessmentTest {

    private static Assessment assessment;

    private final String EXPECTED_DATE_VALID = "27/08/2021";
    private final String EXPECTED_DATE_INVALID = "";

    @BeforeAll
    public static void setUp() {
    }

    @AfterAll
    public static void tearDown() throws Exception {
        assessment = null;
    }

    @Test
    public void whenGetModifiedDate_TimestampIsValid_returnsDateString() {
        assessment = new Assessment(1, "2222-345-678", "morrisminor@leyland.co.uk", "new", "James Woods High", List.of(), List.of());
        assessment.setModifiedDate("1630063005");
        assertEquals(EXPECTED_DATE_VALID, assessment.getModifiedDate());
    }

    @Test
    public void whenGetModifiedDate_TimestampIsNotValid_returnsEmptyString() {
        assessment = new Assessment(1, "2222-345-678", "morrisminor@leyland.co.uk", "new", "James Woods High", List.of(), List.of());
        assessment.setModifiedDate("a63006BBB5");
        assertEquals(assessment.getModifiedDate(), EXPECTED_DATE_INVALID);
    }

}
