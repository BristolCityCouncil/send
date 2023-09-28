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
import uk.gov.bristol.send.pages.summary.PhysicalSensoryPage;
import uk.gov.bristol.send.pages.summary.SocialEmotionalMentalPage;

import java.util.Map;

public class PhysicalSensorySteps {

    @Autowired
    private PhysicalSensoryPage physicalSensoryPage;

    @Autowired
    private Assessment assessment;

    @When("user clicks link for Medical, physical, motor-skills and mobility needs")
    public void user_clicks_link_for_medical_physical_motor_skills_and_mobility() {
        physicalSensoryPage.clickPhysicalNeedsLink();
        Utils.driverWait(1000);
    }

    @When("user clicks link for Vision needs")
    public void user_clicks_link_for_vision() {
        physicalSensoryPage.clickVisionNeedsLink();
    }

    @When("user clicks link for Hearing needs")
    public void user_clicks_link_for_hearing() {
        physicalSensoryPage.clickHearingNeedsLink();
    }

    @When("user clicks link for Sensory processing and integration needs")
    public void user_clicks_link_for_sensory_processing_and_integration_needs() {
        physicalSensoryPage.clickSensoryNeedsLink();
    }

    @Given("user clicks link for Medical, physical, motor-skills and mobility provisions")
    public void click_link_mpmm_provision() {
        physicalSensoryPage.clickPhysicalProvisionsLink();
        Utils.driverWait(1000);
        Assert.assertTrue("Page title not correct", physicalSensoryPage.getPageTitle().contains("Medical, physical, motor-skills and mobility"));
        assessment.setNeedsPage(NeedsPage.MPMM);
    }

    @Given("user clicks link for Vision provisions")
    public void click_link_vision_provision() {
        physicalSensoryPage.clickVisionProvisionsLink();
        Utils.driverWait(1000);
        Assert.assertTrue("Page title not correct", physicalSensoryPage.getPageTitle().contains("Vision"));
        assessment.setNeedsPage(NeedsPage.VISION);
    }

    @Given("user clicks link for Hearing provisions")
    public void click_link_hearing_provision() {
        physicalSensoryPage.clickHearingProvisionsLink();
        Utils.driverWait(1000);
        Assert.assertTrue("Page title not correct", physicalSensoryPage.getPageTitle().contains("Hearing"));
        assessment.setNeedsPage(NeedsPage.HEARING);
    }

    @Given("user clicks link for Sensory, processing and integration provisions")
    public void click_link_spi_provision() {
        physicalSensoryPage.clickSensoryProvisionsLink();
        Utils.driverWait(1000);
        Assert.assertTrue("Page title not correct", physicalSensoryPage.getPageTitle().contains("Sensory processing and integration"));
        assessment.setNeedsPage(NeedsPage.SENSORY);
    }

    @Then("all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary")
    public void all_mpmm_provisions_displayed() {
        Map<String, String> summaryProvisions = physicalSensoryPage.getPhysicalSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.MPMM).keySet().size(), summaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                summaryProvisions, assessment.getAllProvisions(NeedsPage.MPMM));

    }

    @Then("all saved Vision Provisions displayed on Summary")
    public void all_vision_provisions_displayed() {
        Map<String, String> summaryProvisions = physicalSensoryPage.getVisionSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.VISION).keySet().size(), summaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                summaryProvisions, assessment.getAllProvisions(NeedsPage.VISION));

    }

    @Then("all saved Hearing Provisions displayed on Summary")
    public void all_hearing_provisions_displayed() {
        Map<String, String> summaryProvisions = physicalSensoryPage.getHearingSummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.HEARING).keySet().size(), summaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                summaryProvisions, assessment.getAllProvisions(NeedsPage.HEARING));

    }

    @Then("all saved Sensory processing and integration Provisions displayed on Summary")
    public void all_sensory_provisions_displayed() {
        Map<String, String> summaryProvisions = physicalSensoryPage.getSensorySummaryProvisions();

        Assert.assertEquals("Saved Provisions and Summary Provisions are different sizes",
                assessment.getAllProvisions(NeedsPage.SENSORY).keySet().size(), summaryProvisions.keySet().size());

        Assert.assertEquals("Saved and Summary Provisions have different values",
                summaryProvisions, assessment.getAllProvisions(NeedsPage.SENSORY));

    }
}
