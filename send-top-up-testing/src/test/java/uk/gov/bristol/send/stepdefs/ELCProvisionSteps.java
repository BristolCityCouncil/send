package uk.gov.bristol.send.stepdefs;

import io.cucumber.java.en.Given;
import uk.gov.bristol.send.pages.ELCProvisionPage;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.elcprovision.AdditionalStaffPage;

/**
 * All Provisions have the same type sections so likely this class will become the overall parent
 */
// TODO Check common types across all areas. This class may be redundant.
public class ELCProvisionSteps {

    @Autowired
    private ELCProvisionPage elcProvisionPage;

}
