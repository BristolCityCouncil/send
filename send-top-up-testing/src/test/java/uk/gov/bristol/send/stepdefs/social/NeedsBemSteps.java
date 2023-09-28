package uk.gov.bristol.send.stepdefs.social;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.AnxietyPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.EmotionalDevelopmentPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.EmotionalRegulationPage;
import uk.gov.bristol.send.pages.socialemotional.needsbem.IncidentsPage;
import uk.gov.bristol.send.pages.socialemotional.needssui.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsBemSteps extends StepUtils {

    @Autowired
    private AnxietyPage anxietyPage;

    @Autowired
    private EmotionalDevelopmentPage emotionalDevelopmentPage;

    @Autowired
    private EmotionalRegulationPage emotionalRegulationPage;

    @Autowired
    private IncidentsPage incidentsPage;

    private static final String INCIDENTS = "Incidents of behaviour that challenges";
    private static final String EMOTIONAL_DEVELOPMENT = "Emotional development and emotional literacy";
    private static final String EMOTIONAL_REGULATION = "Emotional regulation and impulsivity";
    private static final String ANXIETY = "Anxiety";

    @When("user selects Behaviour, emotional and mental health needs - {string} - {string}")
    public void selectNeedLevel(String needPage, String level) {
        switch (needPage) {
            case INCIDENTS: incidentsPage.selectNeedsLevel(level);
                break;
            case EMOTIONAL_DEVELOPMENT: emotionalDevelopmentPage.selectNeedsLevel(level);
                break;
            case EMOTIONAL_REGULATION: emotionalRegulationPage.selectNeedsLevel(level);
                break;
            case ANXIETY: anxietyPage.selectNeedsLevel(level);
                break;
            default : throw new SENDException("Needs sub-area was not found");
        }

    }

    @Then("the Behaviour, emotional and mental health Level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, anxietyPage.getSelectedNeedLevel());
    }

    @Then("the Behaviour, emotional and mental health level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(anxietyPage);
    }

    @When("all selected Behaviour, emotional and mental health need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(anxietyPage, emotionalDevelopmentPage,
                emotionalRegulationPage, incidentsPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
