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
import uk.gov.bristol.send.pages.summary.SocialEmotionalMentalPage;

import java.util.Map;

public class SocialEmotionalSteps {

    @Autowired
    private SocialEmotionalMentalPage socialEmotionalMentalPage;

    @Autowired
    private ProvisionPage provisionPage;

    @Autowired
    private Assessment assessment;

    @Given("user clicks link for Social understanding and interaction")
    public void clicks_link_for_social_understanding_and_interaction() {
        socialEmotionalMentalPage.clickSuiNeedsLink();
        Utils.driverWait(1000);
    }

    @When("user clicks link for Behaviour, emotional and mental health needs")
    public void clicks_link_for_behaviour_emotional_and_mental_health_needs() {
        socialEmotionalMentalPage.clickBemNeedsLink();
        Utils.driverWait(1000);
    }



}
