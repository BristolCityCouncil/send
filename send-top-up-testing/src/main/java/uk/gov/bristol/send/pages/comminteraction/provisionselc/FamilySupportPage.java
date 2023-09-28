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
public class FamilySupportPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID4-1")
    private WebElement dropDown1;

    @FindBy(id = "dropdown-PTID4-2")
    private WebElement dropDown2;

    @Autowired
    private Assessment assessment;

    private static final String OPTION_1 = "Family Support";
    private static final String OPTION_2 = "Family Intervention (pre-16)";

    public FamilySupportPage(WebDriver webDriver) {
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
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }
        setAssessmentProvision(provisionType, provisionText);
    }

}
