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
public class FamilySupportSuiPage extends ProvisionPage {

    @FindBy(id = "dropdown-PTID25")
    private WebElement familySupportDD;

    @FindBy(id = "dropdown-PTID28")
    private WebElement breakTimeDD;

    @FindBy(id = "dropdown-PTID29")
    private WebElement supportPre16DD;

    @FindBy(id = "dropdown-PTID30")
    private WebElement supportPost16DD;

    @Autowired
    private Assessment assessment;

    private static final String FAMILY_SUPPORT = "Family Support";
    private static final String BREAKTIME_SUPPORT = "break time support";
    private static final String SUPPORT_PRE16 = "support over transitions in current setting (pre-16)";
    private static final String SUPPORT_POST16 = "Support over transitions post-16";

    public FamilySupportSuiPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case FAMILY_SUPPORT:
                provisionText = selectByIndex(familySupportDD, indexStr);
                break;
            case BREAKTIME_SUPPORT:
                provisionText = selectByIndex(breakTimeDD, indexStr);
                break;
            case SUPPORT_PRE16:
                provisionText = selectByIndex(supportPre16DD, indexStr);
                break;
            case SUPPORT_POST16:
                provisionText = selectByIndex(supportPost16DD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        setAssessmentProvision(provisionType, provisionText);
    }

    protected void setAssessmentProvision(String provisionType, String provisionText) {
        assessment.setSuiProvisions(provisionType, provisionText);
    }
}
