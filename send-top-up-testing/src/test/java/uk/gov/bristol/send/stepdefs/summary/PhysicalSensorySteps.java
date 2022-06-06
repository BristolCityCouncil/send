package uk.gov.bristol.send.stepdefs.summary;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.ProvisionPage;
import uk.gov.bristol.send.pages.summary.PhysicalSensoryPage;
import uk.gov.bristol.send.pages.summary.SocialEmotionalMentalPage;

public class PhysicalSensorySteps {

    @Autowired
    private PhysicalSensoryPage physicalSensoryPage;

    @Autowired
    private ProvisionPage provisionPage;

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

}
