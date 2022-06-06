package uk.gov.bristol.send.pages.elcprovision;

import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.ELCProvisionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SpecialistProfessionalPage extends ELCProvisionPage {

    @FindBy(id = "dropdown-PTID1")
    private WebElement adviceSupportDD;

    @FindBy(id = "dropdown-PTID2")
    private WebElement overseeingDD;

    @FindBy(id = "dropdown-PTID3")
    private WebElement directSupportDD;

    @FindBy(id = "dropdown-PTID4")
    private WebElement multiLiaisonDD;

    private static final String ADVICE_SUPPORT = "Specialist professional advice and support 1";
    private static final String OVERSEEING_INTERVENTION = "Specialist professional supervision/overseeing of intervention";
    private static final String DIRECT_SUPPORT = "Specialist professional direct support";
    private static final String MULTI_LIAISON = "Multi-professional liaison";

    public SpecialistProfessionalPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        switch (provisionType) {
            case ADVICE_SUPPORT:
                selectByIndex(adviceSupportDD, indexStr, ADVICE_SUPPORT);
                break;
            case OVERSEEING_INTERVENTION:
                selectByIndex(overseeingDD, indexStr, OVERSEEING_INTERVENTION);
                break;
            case DIRECT_SUPPORT:
                selectByIndex(directSupportDD, indexStr, DIRECT_SUPPORT);
                break;
            case MULTI_LIAISON:
                selectByIndex(multiLiaisonDD, indexStr, MULTI_LIAISON);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
    }

}
