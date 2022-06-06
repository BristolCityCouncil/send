package uk.gov.bristol.send.stepdefs.provisionelc;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.ProvisionPage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.AdditionalStaffPage;

public class AdditionalStaffSteps {

    @Autowired
    private AdditionalStaffPage additionalStaffPage;

    @Autowired
    private ProvisionPage provisionPage;

    @Given("under Expressive language > Additional Staff Support - user selects {string} option {string}")
    public void under_elc_additional_staff_support_selects_option(String provisionType, String indexStr) {
        additionalStaffPage.selectByIndex(provisionType, indexStr);
    }

}
