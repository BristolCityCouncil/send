package uk.gov.bristol.send.stepdefs.communication;

import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.SpecialistProfessionalAlePage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.*;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class CommunicationProvisionSteps {

    private static final String SPECIALIST_PROFESSIONAL = "Specialist professional involvement";
    private static final String GROUP_INTERVENTION = "Group & Individual Intervention";
    private static final String ADDITIONAL_STAFF = "Additional Staff support";
    private static final String FAMILY_SUPPORT = "Family Support";
    private static final String SUPPORT_UNSTRUCTURED = "Support in non-teaching/unstructured times";
    private static final String ADDITIONAL_DIFFERENT = "Additional and Different provision";

    @Autowired
    private SpecialistProfessionalPage specialistProfessionalPage;

    @Autowired
    private GroupInterventionPage groupInterventionPage;

    @Autowired
    private AdditionalStaffPage additionalStaffPage;

    @Autowired
    private FamilySupportPage familySupportPage;

    @Autowired
    private SupportUnstructuredPage supportUnstructuredPage;

    @Autowired
    private AdditionalDifferentPage additionalDifferentPage;

    @Autowired
    private SpecialistProfessionalAlePage specialistProfessionalAlePage;

    @Given("under Specialist Professional Provision selects {string} option {string}")
    public void select_provision_option(String provisionType, String indexStr) {
        specialistProfessionalPage.selectByIndex(provisionType, indexStr);
    }

    @Given("under Academic learning > Specialist Professional Provision - user selects {string} option {string}")
    public void under_ale_additional_staff_support_selects_option(String provisionType, String indexStr) {
        specialistProfessionalAlePage.selectByIndex(provisionType, indexStr);
    }

    @Given("under Expressive language > {string} - user selects {string} option {string}")
    public void underExpressiveLanguageUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        switch (pageSection) {
            case SPECIALIST_PROFESSIONAL:
                specialistProfessionalPage.selectByIndex(provisionType, indexStr);
                break;
            case GROUP_INTERVENTION:
                groupInterventionPage.selectByIndex(provisionType, indexStr);
                break;
            case ADDITIONAL_STAFF:
                additionalStaffPage.selectByIndex(provisionType, indexStr);
                break;
            case FAMILY_SUPPORT:
                familySupportPage.selectByIndex(provisionType, indexStr);
                break;
            case SUPPORT_UNSTRUCTURED:
                supportUnstructuredPage.selectByIndex(provisionType, indexStr);
                break;
            case ADDITIONAL_DIFFERENT:
                additionalDifferentPage.selectByIndex(provisionType, indexStr);
                break;
        }
    }

    @Given("under Receptive language > {string} - user selects {string} option {string}")
    public void underReceptiveLanguageUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        underExpressiveLanguageUserSelectsOption(pageSection, provisionType, indexStr);
    }
}
