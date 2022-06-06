package uk.gov.bristol.send.stepdefs.elcneeds;

import org.junit.Assert;
import uk.gov.bristol.send.Utils;
import uk.gov.bristol.send.pages.elcneeds.StyleOfCommunicationPage;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StyleOfCommunicationSteps {

    @Autowired
    private StyleOfCommunicationPage styleOfCommunicationPage;

    @When("user clicks Style of communication accordion")
    public void click_soc_accordion() {
        styleOfCommunicationPage.clickAccordionBtn();
        Utils.driverWait(2000);
    }

    @When("user clicks Clear selection button")
    public void user_clicks_clear_selection() {
        styleOfCommunicationPage.clickClearSelection();
    }

}
