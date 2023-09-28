package uk.gov.bristol.send.stepdefs.summary;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.NeedsPage;
import uk.gov.bristol.send.pages.ProvisionPage;
import uk.gov.bristol.send.pages.summary.CognitionLearningPage;
import uk.gov.bristol.send.pages.summary.SocialEmotionalMentalPage;

import java.util.Map;

public class SocialEmotionalSteps {

    @Autowired
    private SocialEmotionalMentalPage socialEmotionalMentalPage;

    @Autowired
    private Assessment assessment;

    @Given("user clicks link for Social understanding and interaction needs")
    public void clicks_link_for_social_understanding_and_interaction() {
        socialEmotionalMentalPage.clickSuiNeedsLink();
        Utils.driverWait(1000);
    }

    @When("user clicks link for Behaviour, emotional and mental health needs")
    public void clicks_link_for_behaviour_emotional_and_mental_health_needs() {
        socialEmotionalMentalPage.clickBemNeedsLink();
        Utils.driverWait(1000);
    }

    @Given("user clicks link for Social understanding and interaction provision")
    public void click_link_sui_provision() {
        socialEmotionalMentalPage.clickSuiProvisionsLink();
        Utils.driverWait(1000);
        Assert.assertTrue("Page title not correct", socialEmotionalMentalPage.getPageTitle().contains("Social understanding and interaction"));
        assessment.setNeedsPage(NeedsPage.SUI);
    }

    @Given("user clicks link for Behaviour, emotional and mental health provision")
    public void click_link_bem_provision() {
        socialEmotionalMentalPage.clickBemProvisionsLink();
        Utils.driverWait(1000);
        Assert.assertTrue("Page title not correct", socialEmotionalMentalPage.getPageTitle().contains("Behaviour, emotional and mental health"));
        assessment.setNeedsPage(NeedsPage.BEM);
    }

    @Then("all saved Social understanding and interaction Provisions displayed on Summary")
    public void all_sui_provisions_displayed() {
        Map<String, String> summaryProvisions = socialEmotionalMentalPage.getSuiSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.SUI).keySet().size(), summaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                summaryProvisions, assessment.getAllProvisions(NeedsPage.SUI));

    }

    @Then("all saved Behaviour, emotional and mental health Provisions displayed on Summary")
    public void all_bem_provisions_displayed() {
        Map<String, String> summaryProvisions = socialEmotionalMentalPage.getBemSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.BEM).keySet().size(), summaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                summaryProvisions, assessment.getAllProvisions(NeedsPage.BEM));

    }

}
