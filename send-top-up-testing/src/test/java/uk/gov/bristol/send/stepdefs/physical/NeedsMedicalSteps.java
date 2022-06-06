package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.physicalsensory.hearing.*;
import uk.gov.bristol.send.pages.physicalsensory.needsmpmm.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsMedicalSteps extends StepUtils {

    @Autowired
    private ContinencePage continencePage;

    @Autowired
    private FineMotorSkillsPage fineMotorSkillsPage;

    @Autowired
    private HealthAnxietyPage healthAnxietyPage;

    @Autowired
    private MobilityPage mobilityPage;

    @Autowired
    private PainPage painPage;

    @Autowired
    private PhysicalHealthPage physicalHealthPage;

    @Autowired
    private SleepPage sleepPage;

    @Autowired
    private TraumaticInjuryPage traumaticInjuryPage;

    @Then("the Medical, physical, motor-skills and mobility Level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, continencePage.getSelectedNeedLevel());
    }

    @Then("the Medical, physical, motor-skills and mobility level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(continencePage);
    }

    @When("all selected Medical, physical, motor-skills and mobility need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(continencePage, fineMotorSkillsPage, healthAnxietyPage,mobilityPage,
                painPage, physicalHealthPage, sleepPage, traumaticInjuryPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
