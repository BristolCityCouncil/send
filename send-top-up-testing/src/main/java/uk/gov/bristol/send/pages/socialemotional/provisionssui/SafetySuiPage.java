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
public class SafetySuiPage extends ProvisionPage {

    @FindBy(id = "dropdown-PTID31")
    private WebElement safetyOnSiteDD;

    @FindBy(id = "dropdown-PTID32")
    private WebElement physicalInterventionDD;

    @FindBy(id = "dropdown-PTID33")
    private WebElement safetyPre16;

    @FindBy(id = "dropdown-PTID34")
    private WebElement safetyPost16;

    @Autowired
    private Assessment assessment;

    private static final String SAFETY_ONSITE = "Safety On-site";
    private static final String PHYSICAL_INTERVENTION = "Physical Intervention";
    private static final String SAFETY_PRE16 = "Safety off site pre-16";
    private static final String SAFETY_POST16 = "Safety off site post-16";

    public SafetySuiPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case SAFETY_ONSITE:
                provisionText = selectByIndex(safetyOnSiteDD, indexStr);
                break;
            case PHYSICAL_INTERVENTION:
                provisionText = selectByIndex(physicalInterventionDD, indexStr);
                break;
            case SAFETY_PRE16:
                provisionText = selectByIndex(safetyPre16, indexStr);
                break;
            case SAFETY_POST16:
                provisionText = selectByIndex(safetyPost16, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        setAssessmentProvision(provisionType, provisionText);
    }

    protected void setAssessmentProvision(String provisionType, String provisionText) {
        assessment.setSuiProvisions(provisionType, provisionText);
    }
}
