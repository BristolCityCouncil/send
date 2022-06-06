package uk.gov.bristol.send.pages.comminteraction.provisionselc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.ProvisionPage;

@Component
public class GroupInterventionPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID5")
    private WebElement generalisationDD;

    @FindBy(id = "dropdown-PTID6")
    private WebElement interventionDD;

    @FindBy(id = "dropdown-PTID7")
    private WebElement smallGroupDD;

    @FindBy(id = "dropdown-PTID8")
    private WebElement socialEmotionalDD;

    @FindBy(id = "dropdown-PTID9")
    private WebElement post16TutoringDD;

    @FindBy(id = "dropdown-PTID10")
    private WebElement post16SupportDD;

    @Autowired
    private Assessment assessment;

    private static final String GENERALISATION = "Generalisation and reinforcement";
    private static final String INTERVENTION = "1:1 Intervention";
    private static final String SMALL_GROUP = "Small group Intervention";
    private static final String SOCIAL_EMOTIONAL = "Intervention for social and emotional learning";
    private static final String POST_16_TUTORING = "Post-16 tutoring";
    private static final String POST_16_SUPPORT = "Post-16 exam support";


    public GroupInterventionPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case GENERALISATION:
                provisionText = selectByIndex(generalisationDD, indexStr);
                break;
            case INTERVENTION:
                provisionText = selectByIndex(interventionDD, indexStr);
                break;
            case SMALL_GROUP:
                provisionText = selectByIndex(smallGroupDD, indexStr);
                break;
            case SOCIAL_EMOTIONAL:
                provisionText = selectByIndex(socialEmotionalDD, indexStr);
                break;
            case POST_16_TUTORING:
                provisionText = selectByIndex(post16TutoringDD, indexStr);
                break;
            case POST_16_SUPPORT:
                provisionText = selectByIndex(post16SupportDD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }
        setAssessmentProvision(provisionType, provisionText);
    }

}
