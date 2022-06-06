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
public class FamilySupportPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID25")
    private WebElement familySupportDD;

    @FindBy(id = "dropdown-PTID26")
    private WebElement familyInterventionDD;

    @Autowired
    private Assessment assessment;

    private static final String FAMILY_SUPPORT = "Family Support";
    private static final String FAMILY_INTERVENTION = "Family Intervention (pre-16)";

    public FamilySupportPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case FAMILY_SUPPORT:
                provisionText = selectByIndex(familySupportDD, indexStr);
                break;
            case FAMILY_INTERVENTION:
                provisionText = selectByIndex(familyInterventionDD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }
        setAssessmentProvision(provisionType, provisionText);
    }

}
