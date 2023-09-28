package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.physicalsensory.hearing.*;
import uk.gov.bristol.send.pages.physicalsensory.sensory.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsSensorySteps extends StepUtils {

    @Autowired
    private AuditoryProcessingPage auditoryProcessingPage;

    @Autowired
    private GustatorySystemPage gustatorySystemPage;

    @Autowired
    private InteroceptiveSensePage interoceptiveSensePage;

    @Autowired
    private ProprioceptiveSensePage proprioceptiveSensePage;

    @Autowired
    private TactileAwarenessPage tactileAwarenessPage;

    @Autowired
    private VestibularSystemPage vestibularSystemPage;

    @Autowired
    private VisualProcessingPage visualProcessingPage;

    private static final String VESTIBULAR_SYSTEM = "Vestibular system";
    private static final String INTEROCEPTIVE_SENSE = "Interoceptive sense";
    private static final String PROPRIOCEPTIVE_SENSE = "Proprioceptive sense";
    private static final String TACTILE_AWARENESS = "Tactile Awareness";
    private static final String GUSTATORY_OLIFACTORY = "Gustatory and Olifactory";
    private static final String VISUAL_PROCESSING = "Visual processing";
    private static final String AUDITORY_PROCESSING = "Auditory processing";

    @When("user selects Sensory processing and integration needs - {string} - {string}")
    public void selectNeedLevel(String needPage, String level) {
        switch (needPage) {
            case VESTIBULAR_SYSTEM: vestibularSystemPage.selectNeedsLevel(level);
                break;
            case INTEROCEPTIVE_SENSE: interoceptiveSensePage.selectNeedsLevel(level);
                break;
            case PROPRIOCEPTIVE_SENSE: proprioceptiveSensePage.selectNeedsLevel(level);
                break;
            case TACTILE_AWARENESS: tactileAwarenessPage.selectNeedsLevel(level);
                break;
            case GUSTATORY_OLIFACTORY: gustatorySystemPage.selectNeedsLevel(level);
                break;
            case VISUAL_PROCESSING: visualProcessingPage.selectNeedsLevel(level);
                break;
            case AUDITORY_PROCESSING: auditoryProcessingPage.selectNeedsLevel(level);
                break;
            default : throw new SENDException("Needs sub-area was not found");
        }
    }

    @Then("the Sensory, processing and integration Level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, auditoryProcessingPage.getSelectedNeedLevel());
    }

    @Then("the Sensory, processing and integration level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(auditoryProcessingPage);
    }

    @When("all selected Sensory, processing and integration need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(auditoryProcessingPage, gustatorySystemPage, interoceptiveSensePage,
                proprioceptiveSensePage, tactileAwarenessPage, vestibularSystemPage, visualProcessingPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
