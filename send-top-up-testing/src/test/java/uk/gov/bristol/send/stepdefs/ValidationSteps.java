package uk.gov.bristol.send.stepdefs;

import uk.gov.bristol.send.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Assert;

public class ValidationSteps {

    @Autowired
    private HomePage homePage;

    @Given("user enters UPN {string}")
    public void user_enters_existing_upn(String upnVal) {
        homePage.typeUPN(upnVal);
    }

    @Given("user enters school name of {string}")
    public void user_enters_school_name_of(String schoolName) {
        homePage.typeSchoolName(schoolName);
    }

    @Then("the following UPN error is displayed {string}")
    public void upn_error_is_displayed(String errorMsg) {
        Assert.assertEquals(homePage.getUpnExistsMsg(), errorMsg);
    }

    @Then("the following invalid UPN error is displayed {string}")
    public void invalid_upn_error_is_displayed(String errorMsg) {
        Assert.assertEquals(homePage.getUpnInvalidMsg(), errorMsg);
    }

    @Then("the following school name error is displayed {string}")
    public void the_following_school_name_error_is_displayed(String errorMsg) {
        Assert.assertEquals(homePage.getSchoolErrorMsg(), errorMsg);
    }
}
