package uk.gov.bristol.send.stepdefs.cognition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import uk.gov.bristol.send.pages.AccordionPage;
import uk.gov.bristol.send.pages.cognitionlearning.needsale.*;
import uk.gov.bristol.send.pages.comminteraction.needsrlc.*;
import uk.gov.bristol.send.pages.comminteraction.needsrlc.MemoryPage;
import uk.gov.bristol.send.stepdefs.StepUtils;

import java.util.ArrayList;
import java.util.List;

public class NeedsAleSteps extends StepUtils {

    @Autowired
    private LiteracyPage literacyPage;

    @Autowired
    private MemoryAlePage memoryAlePage;

    @Autowired
    private NumeracyPage numeracyPage;

    @Autowired
    private ProblemSolvingPage problemSolvingPage;

    @Autowired
    private ProcessingPage processingPage;

    @Autowired
    private QualificationsEmployabilityPage qualificationsEmployabilityPage;

    @Autowired
    private TheoryOfMindPage theoryOfMindPage;

    @Then("the Academic learning and employability level of need is identified as {string}")
    public void the_academic_learning_level_of_need_is_identified_as(String string) {
        Assert.assertEquals(string, literacyPage.getSelectedNeedLevel());
    }

    @When("all selected Academic learning and employability need levels are displayed at the bottom of page")
    public void selected_saved_need_level_displayed() {
        List<AccordionPage> pages = new ArrayList<>(List.of(literacyPage, memoryAlePage, numeracyPage, problemSolvingPage,
                processingPage, qualificationsEmployabilityPage, theoryOfMindPage));
        for (AccordionPage page : pages) {
            selectAllAccordionLevels(page);
        }
    }
}
