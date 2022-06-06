package uk.gov.bristol.send.stepdefs.summary;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.ProvisionPage;
import uk.gov.bristol.send.pages.summary.CognitionLearningPage;

import java.util.Map;


public class CognitionLearningSteps {

    @Autowired
    private CognitionLearningPage cognitionLearningPage;

    @Autowired
    private ProvisionPage provisionPage;

    @Autowired
    private Assessment assessment;

    @Then("the Academic learning and employability Level of need is identified as {string}")
    public void level_of_need_identified_as(String expectedNeedLevel) {
        Assert.assertEquals(cognitionLearningPage.getMaxNeedLevel(), expectedNeedLevel);
    }

    @Then("the Academic learning and employability level of need has been cleared")
    public void level_of_need_empty() {
        Assert.assertTrue("Element expected to be hidden", cognitionLearningPage.getMaxNeedNotSelected().isDisplayed());
    }

    @Given("user clicks link for Academic learning and employability need")
    public void click_link_elc_need() {
        cognitionLearningPage.clickAleNeedsLink();
        Utils.driverWait(1000);
    }

    @When("user clicks link for Behaviours for learning")
    public void user_clicks_link_for_behaviours_for_learning() {
        cognitionLearningPage.clickBflNeedsLink();
        Utils.driverWait(1000);
    }

    @Given("user clicks link for Academic learning and employability provision")
    public void click_link_ale_provision() {
        cognitionLearningPage.clickAleProvisionsLink();
        Utils.driverWait(1000);
    }

    @Then("Academic learning and employability Provisions links are disabled")
    public void communication_interaction_provision_links_disabled() {
        String isAleEnabled = cognitionLearningPage.getAleProvisionsLink().getAttribute("disabled");
        Assert.assertEquals("true", isAleEnabled);
    }


    @Then("all saved Academic learning and employability Provisions displayed on Summary")
    public void all_ale_provisions_displayed() {
        Map<String, String> aleSummaryProvisions = cognitionLearningPage.getAleSummaryProvisions();
        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAleProvisions().keySet().size(), aleSummaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                aleSummaryProvisions, assessment.getAleProvisions());

    }
}
