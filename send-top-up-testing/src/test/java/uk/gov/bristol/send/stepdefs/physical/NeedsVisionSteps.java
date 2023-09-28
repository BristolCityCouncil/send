package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.physicalsensory.hearing.*;
import uk.gov.bristol.send.pages.physicalsensory.vision.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsVisionSteps extends StepUtils {

    @Autowired
    private CurriculumAccessPage curriculumAccessPage;

    @Autowired
    private PhysicalSafetyPage physicalSafetyPage;

    @Autowired
    private SocialInclusionPage socialInclusionPage;

    @Autowired
    private UnderstandingNeedsPage understandingNeedsPage;

    @Autowired
    private WellbeingIdentityPage wellbeingIdentityPage;

    private static final String UNDERSTANDING_NEEDS = "Understanding needs";
    private static final String CURRICULUM_ACCESS = "Curriculum access";
    private static final String SOCIAL_INCLUSION = "Social inclusion";
    private static final String PHYSICAL_SAFETY = "Physical safety";
    private static final String WELLBEING = "Wellbing and identity";

    @When("user selects Vision needs - {string} - {string}")
    public void selectNeedLevel(String needPage, String level) {
        switch (needPage) {
            case UNDERSTANDING_NEEDS: understandingNeedsPage.selectNeedsLevel(level);
                break;
            case CURRICULUM_ACCESS: curriculumAccessPage.selectNeedsLevel(level);
                break;
            case SOCIAL_INCLUSION: socialInclusionPage.selectNeedsLevel(level);
                break;
            case PHYSICAL_SAFETY: physicalSafetyPage.selectNeedsLevel(level);
                break;
            case WELLBEING: wellbeingIdentityPage.selectNeedsLevel(level);
                break;
            default : throw new SENDException("Needs sub-area was not found");
        }
    }

    @Then("the Vision Level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, curriculumAccessPage.getSelectedNeedLevel());
    }

    @Then("the Vision level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(curriculumAccessPage);
    }

    @When("all selected Vision need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(curriculumAccessPage, physicalSafetyPage, socialInclusionPage,
                understandingNeedsPage, wellbeingIdentityPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
