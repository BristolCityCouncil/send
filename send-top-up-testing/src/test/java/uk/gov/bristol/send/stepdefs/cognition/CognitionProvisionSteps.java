package uk.gov.bristol.send.stepdefs.cognition;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.GroupInterventionAlePage;
import uk.gov.bristol.send.pages.cognitionlearning.provisionsale.SpecialistProfessionalAlePage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.*;

public class CognitionProvisionSteps {

    private static final String SPECIALIST_PROFESSIONAL = "Specialist professional involvement";
    private static final String GROUP_INTERVENTION = "Group & Individual Intervention";
    private static final String ADDITIONAL_STAFF = "Additional Staff support";
    private static final String FAMILY_SUPPORT = "Family Support";
    private static final String SUPPORT_UNSTRUCTURED = "Support in non-teaching/unstructured times";
    private static final String ADDITIONAL_DIFFERENT = "Additional and Different provision";

    @Autowired
    private SpecialistProfessionalPage specialistProfessionalPage;

    @Autowired
    private GroupInterventionAlePage groupInterventionPage;

    @Autowired
    private AdditionalStaffPage additionalStaffPage;

    @Autowired
    private FamilySupportPage familySupportPage;

    @Autowired
    private SupportUnstructuredPage supportUnstructuredPage;

    @Autowired
    private AdditionalDifferentPage additionalDifferentPage;

    @Given("under Academic learning > {string} - user selects {string} option {string}")
    public void underAcademicLearningUserSelectsOption(String pageSection, String provisionType, String indexStr) {
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

    @Given("under Behaviours for learning > {string} - user selects {string} option {string}")
    public void underBehavioursUserSelectsOption(String pageSection, String provisionType, String indexStr) {
        underAcademicLearningUserSelectsOption(pageSection, provisionType, indexStr);
    }
}
