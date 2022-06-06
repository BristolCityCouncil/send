package uk.gov.bristol.send.stepdefs.social;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
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
