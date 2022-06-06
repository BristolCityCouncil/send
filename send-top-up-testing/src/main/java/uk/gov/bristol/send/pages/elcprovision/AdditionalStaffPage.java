package uk.gov.bristol.send.pages.elcprovision;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.ELCProvisionPage;

public class AdditionalStaffPage extends ELCProvisionPage {

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
        switch (provisionType) {
            case GENERAL_CLASS:
                selectByIndex(generalInClassDD, indexStr, GENERAL_CLASS);
                break;
            case GENERAL_LESSON:
                selectByIndex(generalInLessonDD, indexStr, GENERAL_LESSON);
                break;
            case POST16_SUPPORT:
                selectByIndex(post16SupportDD, indexStr, POST16_SUPPORT);
                break;
            case SUPPORT_PRE16:
                selectByIndex(supportPre16DD, indexStr, SUPPORT_PRE16);
                break;
            case SUPPORT_POST16:
                selectByIndex(supportPost16DD, indexStr, SUPPORT_POST16);
                break;
            case LIFE_SKILLS:
                selectByIndex(lifeSkillsDD, indexStr, LIFE_SKILLS);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
    }
}
