package uk.gov.bristol.send.pages.socialemotional.provisionssui;

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
public class AdditionalStaffSuiPage extends ProvisionPage {

    @FindBy(id = "dropdown-PTID13")
    private WebElement inClassSocialDD;

    @FindBy(id = "dropdown-PTID14")
    private WebElement inClassEmotional;

    @FindBy(id = "dropdown-PTID19")
    private WebElement checkInSupportDD;

    @FindBy(id = "dropdown-PTID20")
    private WebElement staffedWithdrawalDD;

    @FindBy(id = "dropdown-PTID21")
    private WebElement post16SupportDD;

    @FindBy(id = "dropdown-PTID22")
    private WebElement supportTransitionPre16DD;

    @FindBy(id = "dropdown-PTID23")
    private WebElement supportTransitionPost16DD;

    @FindBy(id = "dropdown-PTID24")
    private WebElement lifeSkillsDD;

    @Autowired
    private Assessment assessment;

    private static final String CLASS_SOCIAL = "In-Class staff support for social interaction";
    private static final String CLASS_EMOTIONAL = "In-Class staff support for emotional development";
    private static final String CHECKIN_SUPPORT = "Check-in support";
    private static final String STAFFED_WITHDRAWAL = "Staffed Withdrawal/calm space";
    private static final String POST16_SUPPORT = "Post-16 support";
    private static final String SUPPORT_TRANSITION_PRE = "Support over transition between education settings (pre-16)";
    private static final String SUPPORT_TRANSITION_POST = "Support over transition between education settings (post-16)";
    private static final String LIFE_SKILLS = "Life skills/functional skills provision";

    public AdditionalStaffSuiPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case CLASS_SOCIAL:
                provisionText = selectByIndex(inClassSocialDD, indexStr);
                break;
            case CLASS_EMOTIONAL:
                provisionText = selectByIndex(inClassEmotional, indexStr);
                break;
            case CHECKIN_SUPPORT:
                provisionText = selectByIndex(checkInSupportDD, indexStr);
                break;
            case STAFFED_WITHDRAWAL:
                provisionText = selectByIndex(staffedWithdrawalDD, indexStr);
                break;
            case POST16_SUPPORT:
                provisionText = selectByIndex(post16SupportDD, indexStr);
                break;
            case SUPPORT_TRANSITION_PRE:
                provisionText = selectByIndex(supportTransitionPre16DD, indexStr);
                break;
            case SUPPORT_TRANSITION_POST:
                provisionText = selectByIndex(supportTransitionPost16DD, indexStr);
                break;
            case LIFE_SKILLS:
                provisionText = selectByIndex(lifeSkillsDD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        setAssessmentProvision(provisionType, provisionText);
    }

    protected void setAssessmentProvision(String provisionType, String provisionText) {
        assessment.setSuiProvisions(provisionType, provisionText);
    }
}
