package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.SENDException;
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

    private static final String PHYSICAL_HEALTH = "Physical health/ Medical needs";
    private static final String PAIN = "Pain";
    private static final String HEALTH_ANXIETY = "Health anxiety";
    private static final String TRAUMATIC_INJURY = "Traumatic brain injury";
    private static final String FINE_MOTOR = "Fine motor skills (and personal care)";
    private static final String MOBILITY = "Mobility, gross motor skills and coordination";
    private static final String CONTINENCE = "Continence";
    private static final String SLEEP = "Sleep";

    @When("user selects Medical, physical, motor-skills and mobility needs - {string} - {string}")
    public void selectNeedLevel(String needPage, String level) {
        switch (needPage) {
            case PHYSICAL_HEALTH: physicalHealthPage.selectNeedsLevel(level);
                break;
            case PAIN: painPage.selectNeedsLevel(level);
                break;
            case HEALTH_ANXIETY: healthAnxietyPage.selectNeedsLevel(level);
                break;
            case TRAUMATIC_INJURY: traumaticInjuryPage.selectNeedsLevel(level);
                break;
            case FINE_MOTOR: fineMotorSkillsPage.selectNeedsLevel(level);
                break;
            case MOBILITY: mobilityPage.selectNeedsLevel(level);
                break;
            case CONTINENCE: continencePage.selectNeedsLevel(level);
                break;
            case SLEEP: sleepPage.selectNeedsLevel(level);
                break;
            default : throw new SENDException("Needs sub-area was not found");
        }

    }

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
