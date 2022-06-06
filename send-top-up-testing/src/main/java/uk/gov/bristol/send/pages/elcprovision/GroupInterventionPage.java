package uk.gov.bristol.send.pages.elcprovision;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.ELCProvisionPage;

public class GroupInterventionPage extends ELCProvisionPage {

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
        switch (provisionType) {
            case GENERALISATION:
                selectByIndex(generalisationDD, indexStr, GENERALISATION);
                break;
            case INTERVENTION:
                selectByIndex(interventionDD, indexStr, INTERVENTION);
                break;
            case SMALL_GROUP:
                selectByIndex(smallGroupDD, indexStr, SMALL_GROUP);
                break;
            case SOCIAL_EMOTIONAL:
                selectByIndex(socialEmotionalDD, indexStr, SOCIAL_EMOTIONAL);
                break;
            case POST_16_TUTORING:
                selectByIndex(post16TutoringDD, indexStr, POST_16_TUTORING);
                break;
            case POST_16_SUPPORT:
                selectByIndex(post16SupportDD, indexStr, POST_16_SUPPORT);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
    }
}
