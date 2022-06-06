package uk.gov.bristol.send.stepdefs.communication;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.comminteraction.needselc.*;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsElcSteps extends StepUtils {

    @Autowired
    private StyleOfCommunicationPage styleOfCommunicationPage;

    @Autowired
    private BrainInjuryPage brainInjuryPage;

    @Autowired
    private ConversationalPage conversationalPage;

    @Autowired
    private FunctionalPage functionalPage;

    @Autowired
    private ImpactOfAnxietyPage impactOfAnxietyPage;

    @Autowired
    private NonVerbalPage nonVerbalPage;

    @Autowired
    private SocialExpressivePage socialExpressivePage;

    @Autowired
    private SpeechPage speechPage;

    @Autowired
    private VocabSentencePage vocabSentencePage;

    @Autowired
    private WordFindingPage wordFindingPage;

    @Then("the Expressive Communication Level of need is identified as {string}")
    public void the_expressive_communication_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, styleOfCommunicationPage.getSelectedNeedLevel());
    }

    @Then("the Expressive Communication level of need has been cleared")
    public void the_expressive_communication_level_of_need_has_been_cleared() {
        assertSelectionCleared(styleOfCommunicationPage);
    }

    @When("all selected Expressive language and communication need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(brainInjuryPage, conversationalPage, functionalPage,
                impactOfAnxietyPage, nonVerbalPage, socialExpressivePage, vocabSentencePage, wordFindingPage, styleOfCommunicationPage, speechPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }

}
