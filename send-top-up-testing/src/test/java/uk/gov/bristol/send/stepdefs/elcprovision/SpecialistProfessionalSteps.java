package uk.gov.bristol.send.stepdefs.elcprovision;

import uk.gov.bristol.send.pages.elcprovision.SpecialistProfessionalPage;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecialistProfessionalSteps  {

    @Autowired
    private SpecialistProfessionalPage specialistProfessionalPage;

    @Given("under Specialist Professional Provision selects {string} option {string}")
    public void under_additional_staff_support_selects_option(String provisionType, String indexStr) {
        specialistProfessionalPage.selectByIndex(provisionType, indexStr);
    }
}
