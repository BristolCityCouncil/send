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

@Component
public class SpecialistProfessionalPage extends ProvisionPage {

    @FindBy(id = "dropdown-PTID1")
    private WebElement adviceSupportDD;

    @FindBy(id = "dropdown-PTID2")
    private WebElement overseeingDD;

    @FindBy(id = "dropdown-PTID3")
    private WebElement directSupportDD;

    @FindBy(id = "dropdown-PTID4")
    private WebElement multiLiaisonDD;

    @Autowired
    private Assessment assessment;

    private static final String ADVICE_SUPPORT = "Specialist professional advice and support 1";
    private static final String OVERSEEING_INTERVENTION = "Specialist professional supervision/overseeing of intervention";
    private static final String DIRECT_SUPPORT = "Specialist professional direct support";
    private static final String MULTI_LIAISON = "Multi-professional liaison";

    public SpecialistProfessionalPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case ADVICE_SUPPORT:
                provisionText = selectByIndex(adviceSupportDD, indexStr);
                break;
            case OVERSEEING_INTERVENTION:
                provisionText = selectByIndex(overseeingDD, indexStr);
                break;
            case DIRECT_SUPPORT:
                provisionText = selectByIndex(directSupportDD, indexStr);
                break;
            case MULTI_LIAISON:
                provisionText = selectByIndex(multiLiaisonDD, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }
        setAssessmentProvision(provisionType, provisionText);
    }

    protected void setAssessmentProvision(String provisionType, String provisionText) {
        assessment.setElcProvisions(provisionType, provisionText);
    }

}
