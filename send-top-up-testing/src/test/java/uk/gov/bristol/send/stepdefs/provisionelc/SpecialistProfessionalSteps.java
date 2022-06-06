package uk.gov.bristol.send.stepdefs.provisionelc;

import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.SpecialistProfessionalAlePage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecialistProfessionalSteps  {

    @Autowired
    private SpecialistProfessionalPage specialistProfessionalPage;

    @Autowired
    private SpecialistProfessionalAlePage specialistProfessionalAlePage;

    @Given("under Specialist Professional Provision selects {string} option {string}")
    public void under_additional_staff_support_selects_option(String provisionType, String indexStr) {
        specialistProfessionalPage.selectByIndex(provisionType, indexStr);
    }

    @Given("under Academic learning > Specialist Professional Provision - user selects {string} option {string}")
    public void under_ale_additional_staff_support_selects_option(String provisionType, String indexStr) {
        specialistProfessionalAlePage.selectByIndex(provisionType, indexStr);
    }
}
