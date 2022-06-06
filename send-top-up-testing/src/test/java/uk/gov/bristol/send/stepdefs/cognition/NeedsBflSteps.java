package uk.gov.bristol.send.stepdefs.cognition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.cognitionlearning.needsbfl.*;
import uk.gov.bristol.send.pages.comminteraction.needsrlc.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsBflSteps extends StepUtils {

    @Autowired
    private AcceptingChallengePage acceptingChallengePage;

    @Autowired
    private AttentionConcentrationPage attentionConcentrationPage;

    @Autowired
    private FlexibleThinkingPage flexibleThinkingPage;

    @Autowired
    private FollowingAdultPage followingAdultPage;

    @Autowired
    private IndependenceLearnerPage independenceLearnerPage;

    @Autowired
    private MotivationAspirationPage motivationAspirationPage;

    @Autowired
    private OrganisationForLearningPage organisationForLearningPage;

    @Then("the Behaviours for learning level of need is identified as {string}")
    public void the_behaviour_learning_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, acceptingChallengePage.getSelectedNeedLevel());
    }

    @Then("the Behaviours for learning level of need has been cleared")
    public void the_e_behaviour_learning_level_of_need_has_been_cleared() {
        assertSelectionCleared(acceptingChallengePage);
    }

    @When("all selected Behaviours for learning need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(acceptingChallengePage, attentionConcentrationPage, flexibleThinkingPage,
                followingAdultPage, independenceLearnerPage, motivationAspirationPage, organisationForLearningPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
