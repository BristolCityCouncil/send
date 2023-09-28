package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.SENDException;
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

    private static final String UNDERSTANDING_NEEDS = "Understanding needs";
    private static final String CURRICULUM_ACCESS = "Curriculum access";
    private static final String SOCIAL_INCLUSION = "Social inclusion";
    private static final String PHYSICAL_SAFETY = "Physical safety";
    private static final String WELLBEING = "Wellbing and identity";

    @When("user selects Hearing needs - {string} - {string}")
    public void selectNeedLevel(String needPage, String level) {
        switch (needPage) {
            case UNDERSTANDING_NEEDS: understandingNeedsHearingPage.selectNeedsLevel(level);
                break;
            case CURRICULUM_ACCESS: curriculumAccessHearingPage.selectNeedsLevel(level);
                break;
            case SOCIAL_INCLUSION: socialInclusionHearingPage.selectNeedsLevel(level);
                break;
            case PHYSICAL_SAFETY: physicalSafetyHearingPage.selectNeedsLevel(level);
                break;
            case WELLBEING: wellbeingIdentityHearingPage.selectNeedsLevel(level);
                break;
            default : throw new SENDException("Needs sub-area was not found");
        }
    }

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
