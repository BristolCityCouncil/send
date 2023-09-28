package uk.gov.bristol.send.pages.comminteraction.provisionselc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.ProvisionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

@Component
public class SpecialistProfessionalPage extends ProvisionPage {

    @FindBy(id = "dropdown-PTID1-1")
    private WebElement dropDown1;


    @Autowired
    private Assessment assessment;

    private static final String OPTION_1 = "Specialist professional advice, supervision or support";

    public SpecialistProfessionalPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case OPTION_1:
                provisionText = selectByIndex(dropDown1, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }
        setAssessmentProvision(provisionType, provisionText);
    }

    protected void setAssessmentProvision(String provisionType, String provisionText) {
        Map<String, String> provisionMap = new HashMap<>();
        provisionMap.put(provisionType, provisionText);
        assessment.setAllProvisions(provisionMap);
    }

}
