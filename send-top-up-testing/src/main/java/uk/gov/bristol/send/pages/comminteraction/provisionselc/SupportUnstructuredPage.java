package uk.gov.bristol.send.pages.comminteraction.provisionselc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.SENDException;

@Component
public class SupportUnstructuredPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID5-1")
    private WebElement dropDown1;

    @FindBy(id = "dropdown-PTID5-2")
    private WebElement dropDown2;

    @FindBy(id = "dropdown-PTID5-3")
    private WebElement dropDown3;

    @FindBy(id = "dropdown-PTID5-4")
    private WebElement dropDown4;

    @Autowired
    private Assessment assessment;

    private static final String OPTION_1 = "lunchtime support";
    private static final String OPTION_2 = "break time support";
    private static final String OPTION_3 = "support over transitions in current setting (pre-16)";
    private static final String OPTION_4 = "Support over transitions post-16";


    public SupportUnstructuredPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case OPTION_1:
                provisionText = selectByIndex(dropDown1, indexStr);
                break;
            case OPTION_2:
                provisionText = selectByIndex(dropDown2, indexStr);
                break;
            case OPTION_3:
                provisionText = selectByIndex(dropDown3, indexStr);
                break;
            case OPTION_4:
                provisionText = selectByIndex(dropDown4, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }

        setAssessmentProvision(provisionType, provisionText);
    }

}
