package uk.gov.bristol.send.stepdefs.elcprovision;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.ELCProvisionPage;
import uk.gov.bristol.send.pages.elcprovision.AdditionalStaffPage;

public class AdditionalStaffSteps {

    @Autowired
    private AdditionalStaffPage additionalStaffPage;

    @Autowired
    private ELCProvisionPage elcProvisionPage;

    @Given("under Additional Staff Support selects {string} option {string}")
    public void under_additional_staff_support_selects_option(String provisionType, String indexStr) {
        additionalStaffPage.selectByIndex(provisionType, indexStr);
    }

}
