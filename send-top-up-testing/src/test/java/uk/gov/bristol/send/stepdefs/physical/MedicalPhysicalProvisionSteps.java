package uk.gov.bristol.send.stepdefs.physical;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.SpecialistProfessionalAlePage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.AdditionalDifferentPage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SupportUnstructuredPage;
import uk.gov.bristol.send.pages.physicalsensory.provisionsmpmm.AdditionalDifferentMpmmPage;
import uk.gov.bristol.send.pages.physicalsensory.provisionsmpmm.AdditionalStaffMpmmPage;
import uk.gov.bristol.send.pages.physicalsensory.provisionsmpmm.HealthcareMpmmPage;
import uk.gov.bristol.send.pages.socialemotional.provisionssui.*;

public class MedicalPhysicalProvisionSteps {

    private static final String SPECIALIST_PROFESSIONAL = "Specialist professional involvement";
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
    private AdditionalStaffMpmmPage additionalStaffPage;

    @Autowired
    private FamilySupportSuiPage familySupportPage;

    @Autowired
    private SupportUnstructuredPage supportUnstructuredPage;

    @Autowired
    private AdditionalDifferentMpmmPage additionalDifferentPage;

    @Autowired
    private SpecialistProfessionalAlePage specialistProfessionalAlePage;

    @Autowired
    private SafetySuiPage safetySuiPage;

    @Autowired
    private HealthcareMpmmPage healthcarePage;

    @Given("under Medical, physical, motor-skills and mobility > {string} - user selects {string} option {string}")
    public void underMedicalUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        switch (pageSection) {
            case SPECIALIST_PROFESSIONAL:
                specialistProfessionalPage.selectByIndex(provisionType, indexStr);
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
                healthcarePage.selectByIndex(provisionType, indexStr);
                break;
            case ADDITIONAL_DIFFERENT:
                additionalDifferentPage.selectByIndex(provisionType, indexStr);
                break;
        }
    }

    @Given("under Vision > {string} - user selects {string} option {string}")
    public void underVisionUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        underMedicalUserSelectsOption(pageSection, provisionType, indexStr);
    }

    @Given("under Hearing > {string} - user selects {string} option {string}")
    public void underHearingUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        underMedicalUserSelectsOption(pageSection, provisionType, indexStr);
    }

    @Given("under Sensory processing and integration > {string} - user selects {string} option {string}")
    public void underSensoryLanguageUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        underMedicalUserSelectsOption(pageSection, provisionType, indexStr);
    }
}
