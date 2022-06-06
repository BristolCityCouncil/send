package uk.gov.bristol.send.stepdefs;

import uk.gov.bristol.send.pages.ELCNeedsPage;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;

public class ELCNeedsSteps {

    @Autowired
    private ELCNeedsPage elcNeedsPage;

    @Then("the Expressive Communication Level of need is identified as {string}")
    public void level_of_need_identified_as(String expectedNeedLevel) {
        Assert.assertEquals(elcNeedsPage.getMaxNeedLevel(), expectedNeedLevel);
    }

    @Then("the Expressive Communication level of need has been cleared")
    public void level_of_need_empty() {
        Assert.assertTrue("Element expected to be hidden", elcNeedsPage.getMaxNeedNotSelected().isDisplayed());
    }
}
