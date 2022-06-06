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
public class AdditionalStaffPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID11")
    private WebElement generalInClassDD;

    @FindBy(id = "dropdown-PTID12")
    private WebElement generalInLessonDD;

    @FindBy(id = "dropdown-PTID21")
    private WebElement post16SupportDD;

    @FindBy(id = "dropdown-PTID22")
    private WebElement supportPre16DD;

    @FindBy(id = "dropdown-PTID23")
    private WebElement supportPost16DD;

    @FindBy(id = "dropdown-PTID24")
    private WebElement lifeSkillsDD;

    @Autowired
    private Assessment assessment;

    private static final String GENERAL_CLASS = "General in class learning support pre-16";
    private static final String GENERAL_LESSON = "General In lesson learning support post-16";
    private static final String POST16_SUPPORT = "Pre-16 support";
    private static final String SUPPORT_PRE16 = "Support over transition between educational setting (pre-16)";
    private static final String SUPPORT_POST16 = "Support over transition between educational settings (post-16)";
    private static final String LIFE_SKILLS = "Life skills/functional skills provision";

    public AdditionalStaffPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case GENERAL_CLASS:
                provisionText = selectByIndex(generalInClassDD, indexStr);
                break;
            case GENERAL_LESSON:
                provisionText = selectByIndex(generalInLessonDD, indexStr);
                break;
            case POST16_SUPPORT:
                provisionText = selectByIndex(post16SupportDD, indexStr);
                break;
            case SUPPORT_PRE16:
                provisionText = selectByIndex(supportPre16DD, indexStr);
                break;
            case SUPPORT_POST16:
                provisionText = selectByIndex(supportPost16DD, indexStr);
                break;
            case LIFE_SKILLS:
                provisionText = selectByIndex(lifeSkillsDD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }

        setAssessmentProvision(provisionType, provisionText);
    }

}
