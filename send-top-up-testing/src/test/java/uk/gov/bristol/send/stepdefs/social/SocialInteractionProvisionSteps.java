package uk.gov.bristol.send.stepdefs.social;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.SpecialistProfessionalAlePage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.*;
import uk.gov.bristol.send.pages.socialemotional.provisionssui.*;

public class SocialInteractionProvisionSteps {

    private static final String SPECIALIST_PROFESSIONAL = "Specialist professional involvement";
    private static final String GROUP_INTERVENTION = "Group & Individual Intervention";
    private static final String ADDITIONAL_STAFF = "Additional Staff support";
    private static final String FAMILY_SUPPORT = "family Support";
    private static final String SUPPORT_UNSTRUCTURED = "Support in non-teaching/unstructured times";
    private static final String SAFETY = "Safety";
    private static final String HEALTH_CARE = "Health care";
    private static final String ADDITIONAL_DIFFERENT = "Additional and Different provision";

    @Autowired
    private SpecialistProfessionalPage specialistProfessionalPage;

    @Autowired
    private GroupInterventionSuiPage groupInterventionPage;

    @Autowired
    private AdditionalStaffSuiPage additionalStaffPage;

    @Autowired
    private FamilySupportSuiPage familySupportPage;

    @Autowired
    private SupportUnstructuredPage supportUnstructuredPage;

    @Autowired
    private AdditionalDifferentPage additionalDifferentPage;

    @Autowired
    private SpecialistProfessionalAlePage specialistProfessionalAlePage;

    @Autowired
    private SafetySuiPage safetySuiPage;

    @Autowired
    private HealthcareSuiPage healthcareSuiPage;

    @Given("under Social understanding and interaction > {string} - user selects {string} option {string}")
    public void underSuiUserSelectsOption(String pageSection, String provisionType, String indexStr) {
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
            case SAFETY:
                safetySuiPage.selectByIndex(provisionType, indexStr);
                break;
            case HEALTH_CARE:
                healthcareSuiPage.selectByIndex(provisionType, indexStr);
                break;
            case ADDITIONAL_DIFFERENT:
                additionalDifferentPage.selectByIndex(provisionType, indexStr);
                break;
        }
    }

    @Given("under Behaviour emotional > {string} - user selects {string} option {string}")
    public void underReceptiveLanguageUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        underSuiUserSelectsOption(pageSection, provisionType, indexStr);
    }
}
