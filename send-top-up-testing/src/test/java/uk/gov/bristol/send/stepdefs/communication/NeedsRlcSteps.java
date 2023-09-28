package uk.gov.bristol.send.stepdefs.communication;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.comminteraction.needsrlc.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsRlcSteps extends StepUtils {

    @Autowired
    private AttentionListeningPage attentionListeningPage;

    @Autowired
    private FunctionalUnderstandingPage functionalUnderstandingPage;

    @Autowired
    private MemoryPage memoryPage;

    @Autowired
    private NonVerbalRlcPage nonVerbalPage;

    @Autowired
    private UnderstandSocialLanguagePage understandSocialLanguagePage;

    @When("user selects Attention and listening {string}")
    public void selectAttentionNeedLevel(String level) {
        attentionListeningPage.selectNeedsLevel(level);
    }

    @When("user selects Memory {string}")
    public void selectMemoryNeedLevel(String level) {
        memoryPage.selectNeedsLevel(level);
    }

    @When("user selects Functional understanding {string}")
    public void selectFunctionalNeedLevel(String level) {
        functionalUnderstandingPage.selectNeedsLevel(level);
    }

    @When("user selects Understanding social language {string}")
    public void selectUnderstandingNeedLevel(String level) {
        understandSocialLanguagePage.selectNeedsLevel(level);
    }

    @When("user selects Non-verbal, alternative augmented communication {string}")
    public void selectNonVerbalNeedLevel(String level) {
        nonVerbalPage.selectNeedsLevel(level);
    }


    @Then("the Receptive language and communication level of need is identified as {string}")
    public void the_social_understanding_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, attentionListeningPage.getSelectedNeedLevel());
    }

    @Then("the Receptive language and communication level of need has been cleared")
    public void the_social_understanding_level_of_need_has_been_cleared() {
        assertSelectionCleared(attentionListeningPage);
    }

    @When("all selected Receptive language and communication need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(attentionListeningPage, functionalUnderstandingPage,
            memoryPage, nonVerbalPage, understandSocialLanguagePage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
