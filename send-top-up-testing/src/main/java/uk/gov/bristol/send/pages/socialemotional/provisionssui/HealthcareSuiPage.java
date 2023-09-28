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

import java.util.HashMap;
import java.util.Map;

@Component
public class HealthcareSuiPage extends ProvisionPage {

    @FindBy(id = "dropdown-PTID7-2")
    private WebElement supportMealtimesDD;

    @Autowired
    private Assessment assessment;

    private static final String SUPPORT_MEALTIMES = "Support at meal times";

    public HealthcareSuiPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case SUPPORT_MEALTIMES:
                provisionText = selectByIndex(supportMealtimesDD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        setAssessmentProvision(provisionType, provisionText);
    }

    protected void setAssessmentProvision(String provisionType, String provisionText) {
        Map<String, String> provisionMap = new HashMap<>();
        provisionMap.put(provisionType, provisionText);
        assessment.setAllProvisions(provisionMap);
    }
}
