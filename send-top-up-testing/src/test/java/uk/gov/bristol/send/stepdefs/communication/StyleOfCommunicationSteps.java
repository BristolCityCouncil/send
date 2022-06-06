package uk.gov.bristol.send.stepdefs.communication;

import uk.gov.bristol.send.pages.comminteraction.needselc.StyleOfCommunicationPage;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StyleOfCommunicationSteps {

    @Autowired
    private StyleOfCommunicationPage styleOfCommunicationPage;

    @When("user clicks Clear selection button")
    public void user_clicks_clear_selection() {
        styleOfCommunicationPage.clickClearSelection();
    }


}
