package uk.gov.bristol.send.stepdefs.summary;

import org.junit.Assert;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.NeedsPage;
import uk.gov.bristol.send.pages.ProvisionPage;
import uk.gov.bristol.send.pages.summary.CommunicationInteractionPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class CommunicationInteractionSteps {

    @Autowired
    private CommunicationInteractionPage communicationInteractionPage;

    @Autowired
    private Assessment assessment;

    private static final String DISABLED = "disabled";

    @Given("user clicks link for Expressive language and communication need")
    public void click_link_elc_need() {
        communicationInteractionPage.clickElcNeedsLink();
        Utils.driverWait(2000);
    }

    @Given("user clicks link for Receptive language and communication need")
    public void click_link_rlc_need() {
        communicationInteractionPage.clickRlcNeedsLink();
        Utils.driverWait(1000);
    }

    @Then("Communication and interaction Provisions links are disabled")
    public void communication_interaction_provision_links_disabled() {
        String isElcEnabled = communicationInteractionPage.getElcProvisionsLink().getAttribute(DISABLED);
        String isRlcEnabled = communicationInteractionPage.getRlcProvisionsLink().getAttribute(DISABLED);

        Assert.assertEquals("true", isElcEnabled);
        Assert.assertEquals("true", isRlcEnabled);
    }

    @When("I click the Remove link for Expressive language and communication provision item {string}")
    public void click_remove_link_for_elc_item(String itemNumber) {
        communicationInteractionPage.clickRemoveElcProvisionLink(itemNumber);
    }

    @Then("the Expressive language and communication provision is removed")
    public void the_elc_provision_is_removed() {
        Utils.driverWait(2000);
        // Possible more than one Provision so Exception and title check needed.
        try {
            String provisionTitle = communicationInteractionPage.getElcProvisionSelectedType();
            Assert.assertNotEquals(provisionTitle, assessment.getAllProvisions(NeedsPage.ELC).get(provisionTitle));
        } catch (NoSuchElementException ex) {
            Assert.assertTrue(true);
        }
    }

    @Given("user clicks link for Expressive language and communication provision")
    public void click_link_elc_provision() {
        communicationInteractionPage.clickElcProvisionsLink();
        Assert.assertTrue("Page title not correct", communicationInteractionPage.getPageTitle().contains("Expressive language and communication"));
        assessment.setNeedsPage(NeedsPage.ELC);
    }

    @Given("user clicks link for Receptive language and communication provision")
    public void click_link_rlc_provision() {
        communicationInteractionPage.clickRlcProvisionsLink();
        Assert.assertTrue("Page title not correct", communicationInteractionPage.getPageTitle().contains("Receptive language and communication"));
        assessment.setNeedsPage(NeedsPage.RLC);
    }

    @Then("all saved Expressive language and communication Provisions displayed on Summary")
    public void all_elc_provisions_displayed() {
        Map<String, String> elcSummaryProvisions = communicationInteractionPage.getElcSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.ELC).keySet().size(), elcSummaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                elcSummaryProvisions, assessment.getAllProvisions(NeedsPage.ELC));

    }

    @Then("all saved Receptive language and communication Provisions displayed on Summary")
    public void all_rlc_provisions_displayed() {
        Map<String, String> elcSummaryProvisions = communicationInteractionPage.getRlcSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.RLC).keySet().size(), elcSummaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                elcSummaryProvisions, assessment.getAllProvisions(NeedsPage.RLC));

    }

    @Then("provision is displayed under Communication and interaction on the Summary containing {string}")
    public void provision_displayed_on_summary(String expectedProvision) {

        String provisionType = communicationInteractionPage.getElcProvisionSelectedType();
        Assert.assertTrue(provisionType.contains(expectedProvision));

        String provisionText = communicationInteractionPage.getElcProvisionSelectedText();
        Assert.assertEquals(provisionText, assessment.getAllProvisions(NeedsPage.ELC).get(provisionType));

    }

    @Then("selected need level under Communication and interaction on the Summary is {string}")
    public void selected_need_level_displayed_on_summary(String expectedNeed) {
        Assert.assertEquals(communicationInteractionPage.getELCNeedsLevel(), expectedNeed);
    }

    @Then("selected need level under Communication and interaction on the Summary has been cleared")
    public void selected_need_level_under_communication_and_interaction_on_the_summary_has_been_cleared() {
        Assert.assertThrows(NoSuchElementException.class, () -> communicationInteractionPage.getELCNeedsLevel());

    }
}
