package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.physicalsensory.hearing.*;
import uk.gov.bristol.send.pages.socialemotional.needssui.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsHearingSteps extends StepUtils {

    @Autowired
    private CurriculumAccessHearingPage curriculumAccessHearingPage;

    @Autowired
    private PhysicalSafetyHearingPage physicalSafetyHearingPage;

    @Autowired
    private SocialInclusionHearingPage socialInclusionHearingPage;

    @Autowired
    private UnderstandingNeedsHearingPage understandingNeedsHearingPage;

    @Autowired
    private WellbeingIdentityHearingPage wellbeingIdentityHearingPage;


    @Then("the Hearing Level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, curriculumAccessHearingPage.getSelectedNeedLevel());
    }

    @Then("the Hearing level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(curriculumAccessHearingPage);
    }

    @When("all selected Hearing need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(curriculumAccessHearingPage, physicalSafetyHearingPage,
                socialInclusionHearingPage, understandingNeedsHearingPage, wellbeingIdentityHearingPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
